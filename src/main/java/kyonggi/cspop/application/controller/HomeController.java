package kyonggi.cspop.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("api/home")
    public String home(@RequestParam(required = false) String param, Model model) {

        if (param != null){
            if (param.equals("login")) {
                model.addAttribute("message", "로그인 후 이용해주세요!");
            }
            else if (param.equals("user")) {
                model.addAttribute("message", "권한이 없는 사용자입니다!");
            }
        }
        return "index";
    }

    @GetMapping("api/chatBotPage")
    public String chatBot(){
        return "chatBot/chatBot";
    }
}
