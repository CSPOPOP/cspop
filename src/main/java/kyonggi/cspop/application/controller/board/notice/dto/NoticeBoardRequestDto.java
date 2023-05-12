package kyonggi.cspop.application.controller.board.notice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NoticeBoardRequestDto {

    @NotBlank(message = "제목을 작성해 주세요.")
    private String title;
    @NotBlank(message = "글을 작성해 주세요.")
    private String text;
    private List<MultipartFile> files;
}
