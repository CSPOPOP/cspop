package kyonggi.cspop.application.controller.chatbot;

import kyonggi.cspop.application.SessionFactory;
import kyonggi.cspop.application.controller.chatbot.dto.ChatBotRequestDto;
import kyonggi.cspop.application.controller.chatbot.dto.ChatBotResponseDto;
import kyonggi.cspop.domain.board.certification.service.CertificationBoardService;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatBotController {

    private final CertificationBoardService certificationBoardService;

    @GetMapping("api/chatBot")
    public ChatBotResponseDto professionalEducation(@RequestBody ChatBotRequestDto chatBotRequestDto,
                                                    @SessionAttribute(name = SessionFactory.CSPOP_SESSION_KEY, required = false) UserSessionDto userSessionDto) {
        ChatBotResponseDto.ChatBotResponseDtoBuilder builder = ChatBotResponseDto.builder();
        builder.answer(certificationBoardService.findCertificationByStudentId(chatBotRequestDto.getSentence(), userSessionDto));
        return builder.build();
    }
}
