package me.mdzs.apartmentbookingweb;

import me.mdzs.apartmentbookingweb.domain.User;
import me.mdzs.apartmentbookingweb.identification.UserDaoImplJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthenticationController {
    private final UserDaoImplJson userDao = new UserDaoImplJson();

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
                               Model model) throws IOException {
        // Простая логика проверки пользователя (замените на реальную)
        Boolean flag = checkIfUserExist(username, password);
        if (userDao.getUser(username) != null & flag) {
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
                                      Model model) throws IOException {


        // Проверяем, существует ли уже пользователь с таким именем
        if (userDao.getUser(username) != null) {
            model.addAttribute("error", "Username already exists. Please choose another one.");
            return "registration"; // если пользователь существует, возвращаем форму с ошибкой
        }

        // Создаем нового пользователя и сохраняем его в базе данных
        User newUser = new User(username, password);
        userDao.save(newUser); // сохраняем пользователя через UserDaoImplDB

        // Перенаправляем пользователя на страницу логина после успешной регистрации
        return "redirect:/login"; // перенаправление на страницу логина
    }
    private Boolean checkIfUserExist(String userName, String password) throws IOException {
        List<User> userList = userDao.getAll();
        for (User user : userList) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return true; // Возвращаем пользователя, если имя и пароль совпадают
            }
        }
        return false; // Если пользователь не найден или пароль неверный
    }
}
