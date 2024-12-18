package me.mdzs.apartmentbookingweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/test")
    public String test() {
        return "test"; // возвращаем имя шаблона, т.е. "test.html"
    }
}