package me.mdzs.apartmentbookingweb.utils;


import me.mdzs.apartmentbookingweb.domain.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {


    // Инициализация базы данных и создание таблицы, если её нет
    public static void initializeDatabase(String URL) {
        // Проверка наличия файла базы данных
        File dbFile = new File("users.db");
        if (!dbFile.exists()) {
            try {
                // Создаем новый файл базы данных
                dbFile.createNewFile();
                System.out.println("Файл базы данных создан.");
            } catch (Exception e) {
                System.out.println("Ошибка создания файла базы данных: " + e.getMessage());
            }
        }

        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "userName TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "isAdmin INTEGER NOT NULL)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка инициализации базы данных: " + e.getMessage());
        }
    }

    // Метод для добавления пользователя в базу данных
    public static void addUser(User user, String URL) {
        String sql = "INSERT INTO users(userName, password, isAdmin) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getIsAdmin() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя: " + e.getMessage());
        }
    }

    // Метод для получения пользователя из базы данных по имени пользователя
    // Метод для получения пользователя из базы данных по имени пользователя и паролю
    public static User getUser(String userName, String URL) {
        String sql = "SELECT * FROM users WHERE userName = ?";
        User user = null;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String password = rs.getString("password");
                boolean isAdmin = rs.getInt("isAdmin") == 1;
                user = new User(userName, password, isAdmin);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения пользователя: " + e.getMessage());
        }
        return user;
    }

    public static Boolean checkIfUserExist(User userOld, String URL) {
        String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";
        User user = null;
        boolean flag = false;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userOld.getUserName());
            pstmt.setString(2, userOld.getPassword()); // Устанавливаем значение для пароля
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
//                boolean isAdmin = rs.getInt("isAdmin") == 1;
//                user = new User( userOld.getUserName(), userOld.getPassword(), isAdmin);
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения пользователя: " + e.getMessage());
        }
        return flag;
    }


    public static List<User> getAllUsers(String URL) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                boolean isAdmin = rs.getInt("isAdmin") == 1;
                users.add(new User(userName, password, isAdmin));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения пользователей: " + e.getMessage());
        }
        return users;
    }

    public static void removeUser(User user, String URL) {
        String sql = "DELETE FROM users WHERE userName = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Пользователь успешно удален.");
            } else {
                System.out.println("Пользователь не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя: " + e.getMessage());
        }
    }

}