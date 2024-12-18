package me.mdzs.apartmentbookingweb;

import me.mdzs.apartmentbookingweb.identification.UserDaoImplDB;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class AuthenticationController {
    private final UserDaoImplDB userDao = new UserDaoImplDB();

    // Отображение страницы логина
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // возвращаем имя шаблона "login.html"
    }

    // Обработка отправки формы логина
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String role,
                               Model model) {
        // Простая логика проверки пользователя (замените на реальную)
        if (username.equals("1") && password.equals("1") && role.equals("admin")) {
            model.addAttribute("message", "Login successful!");
            return "welcome"; // возвращаем имя шаблона для успешного входа
        } else {
            model.addAttribute("error", "Invalid credentials or role.");
            return "login"; // возвращаемся на страницу логина с ошибкой
        }
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration"; // возвращаем имя шаблона "registration.html"
    }

    // Обработка отправки формы регистрации
    @PostMapping("/registration")
    public String processRegistration(@RequestParam String username,
                                      @RequestParam String password,
                                      Model model) {
        // Логика регистрации пользователя (замените на реальную логику)
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Username and password are required.");
            return "registration"; // возвращаем форму регистрации с ошибкой
        }

        // Пример успешной регистрации (замените на реальную логику)
        model.addAttribute("message", "Registration successful! You can now log in.");
        return "login"; // после успешной регистрации перенаправляем на страницу логина
    }
}
