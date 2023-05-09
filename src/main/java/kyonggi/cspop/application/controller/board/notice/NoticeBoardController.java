package kyonggi.cspop.application.controller.board.notice;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.board.notice.dto.NoticeBoardRequestDto;
import kyonggi.cspop.application.controller.board.notice.dto.NoticeNumberJsonRequest;
import kyonggi.cspop.application.controller.board.notice.dto.NoticeViewDto;
import kyonggi.cspop.application.util.FileStore;
import kyonggi.cspop.application.util.PageStore;
import kyonggi.cspop.domain.admins.Admins;
import kyonggi.cspop.domain.admins.repository.AdminsRepository;
import kyonggi.cspop.domain.board.notice.NoticeBoard;
import kyonggi.cspop.domain.board.notice.dto.NoticeBoardResponseDto;
import kyonggi.cspop.domain.board.notice.service.NoticeBoardService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.uploadfile.NoticeBoardUploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final AdminsRepository adminsRepository;
    private final FileStore fileStore;
    private final PageStore pageStore;

    @GetMapping("api/graduation/form")
    public String noticeForm() {return "graduation/notice/noticeForm";}

    @GetMapping("api/graduation/detail")
    public String noticeDetail() {return "graduation/notice/noticeDetail";}

    @GetMapping("/notice/find")
    public String findAllNoticeBoard(Pageable pageable, Model model) {
        Page<NoticeBoardResponseDto> allNoticeBoard = noticeBoardService.findAllNoticeBoard(pageable);

        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(allNoticeBoard.getPageable().getPageNumber(), allNoticeBoard.getTotalPages());
        model.addAttribute("startBlockPage", startAndEndBlockPage[0]);
        model.addAttribute("endBlockPage", startAndEndBlockPage[1]);
        model.addAttribute("allNoticeBoard", allNoticeBoard);
        return "graduation/notice/notice";
    }

    @GetMapping("api/notice/search")
    public String searchNotice(@RequestParam String word, Pageable pageable, Model model) {
        Page<NoticeBoardResponseDto> searchNotice = noticeBoardService.findSearchNotice(pageable, word);
        if (searchNotice.isEmpty()) {
            model.addAttribute("errorMessage", true);
            return "graduation/notice/notice";
        }

        int[] startAndEndBlockPage = pageStore.getStartAndEndBlockPage(searchNotice.getPageable().getPageNumber(), searchNotice.getTotalPages());
        model.addAttribute("startBlockPage", startAndEndBlockPage[0]);
        model.addAttribute("endBlockPage", startAndEndBlockPage[1]);
        model.addAttribute("allNoticeBoard", searchNotice);

        return "graduation/notice/notice";
    }

    @PostMapping("api/graduation/form")
    public String saveNoticeBoard(HttpServletRequest request, @Validated @ModelAttribute NoticeBoardRequestDto noticeBoardRequestDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("data", noticeBoardRequestDto.getText() != null ? noticeBoardRequestDto.getText() : "");
            return "graduation/notice/noticeForm";
        }

        UserSessionDto adminSession = (UserSessionDto) request.getSession().getAttribute(SessionFactory.CSPOP_SESSION_KEY);
        Admins findAdmin = adminsRepository.findByAdminId(adminSession.getStudentId()).get();

        List<NoticeBoardUploadFile> storeFiles = fileStore.storeFiles(noticeBoardRequestDto.getFiles());

        //데이터베이스에 저장
        NoticeBoard noticeBoard = NoticeBoard.createNoticeBoard(noticeBoardRequestDto.getTitle(), noticeBoardRequestDto.getText(), false, 0, findAdmin, storeFiles);
        noticeBoardService.saveNoticeBoard(noticeBoard, storeFiles);

        return "redirect:/notice/find?page=0&size=10";
    }

    @GetMapping("/attach/{noticeBoardId}/{uploadFileName}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long noticeBoardId, @PathVariable String uploadFileName) throws MalformedURLException {
        NoticeBoard noticeBoard = noticeBoardService.findNoticeBoard(noticeBoardId);

        ResponseEntity<Resource> CONTENT_DISPOSITION = noticeBoardFileDownload(uploadFileName, noticeBoard);
        if (CONTENT_DISPOSITION != null)
            return CONTENT_DISPOSITION;

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notice/view/detail/{noticeBoardId}")
    public String viewDetail(@PathVariable Long noticeBoardId, Model model) {
        //자세히 보기 누르면 view + 1 돼야함.
        NoticeBoard detailNoticeBoard = noticeBoardService.findDetailNoticeBoard(noticeBoardId);
        model.addAttribute("detailView", new NoticeViewDto(detailNoticeBoard));
        return "graduation/notice/noticeDetail";
    }

    @GetMapping("api/graduation/modifyForm/{noticeBoardId}")
    public String noticeModifyForm(@PathVariable Long noticeBoardId, Model model) {
        NoticeBoard findNoticeBoard = noticeBoardService.findNoticeBoard(noticeBoardId);
        model.addAttribute("detailView", new NoticeViewDto(findNoticeBoard));
        return "graduation/notice/noticeModifyForm";
    }

    @PostMapping("api/graduation/modifyForm/{noticeBoardId}")
    public String noticeModify(@PathVariable Long noticeBoardId, @Validated @ModelAttribute NoticeBoardRequestDto noticeBoardRequestDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "graduation/notice/noticeModifyForm";
        }
        List<NoticeBoardUploadFile> storeFiles = fileStore.storeFiles(noticeBoardRequestDto.getFiles());
        noticeBoardService.updateNoticeBoard(noticeBoardId, noticeBoardRequestDto, storeFiles);
        return "redirect:/notice/find?page=0&size=10";
    }

    @PostMapping("api/notice/fix")
    public ResponseEntity<Void> fixNotice(@RequestBody NoticeNumberJsonRequest noticeNumberJsonRequest) {
        noticeBoardService.fixAndClearNoticeBoard(noticeNumberJsonRequest.getNoticeBoardId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("api/notice/delete")
    public ResponseEntity<Void> deleteNotice(@RequestBody NoticeNumberJsonRequest noticeNumberJsonRequest) {
        noticeBoardService.deleteNoticeBoard(noticeNumberJsonRequest.getNoticeBoardId());
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<Resource> noticeBoardFileDownload(String uploadFileName, NoticeBoard noticeBoard) throws MalformedURLException {
        List<NoticeBoardUploadFile> uploadFiles = noticeBoard.getUploadFiles();
        for (NoticeBoardUploadFile uploadFile : uploadFiles) {
            if (uploadFile.getUploadFileName().equals(uploadFileName)) {
                String storeFileName = uploadFile.getStoreFileName();
                String dbUploadFileName = uploadFile.getUploadFileName();

                UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
                String encodedUploadFileName = UriUtils.encode(dbUploadFileName, StandardCharsets.UTF_8);
                String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
            }
        }
        return null;
    }
}
