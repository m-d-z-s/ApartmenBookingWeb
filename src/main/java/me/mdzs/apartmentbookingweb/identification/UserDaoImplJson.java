package me.mdzs.apartmentbookingweb.identification;


import me.mdzs.apartmentbookingweb.domain.User;
import me.mdzs.apartmentbookingweb.utils.JsonUtilsForUsers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UserDaoImplJson implements UserDao<User> {

    private final String PATH = "usersData.json";


    public UserDaoImplJson() {
    }
    //    /**
//     * Получает пользователя по имени и паролю.
//     * Используется для авторизации.
//     *
//     * @param user     имя пользователя
//     * @param password пароль пользователя
//     * @return объект User, если пользователь найден и пароль совпадает, иначе null
//     */
    @Override
    public User getUser(String userName) throws IOException {
        List<User> userList = getAll();
        for (User user : userList) {
            if (user.getUserName().equals(userName)) {
                return user; // Возвращаем пользователя, если имя и пароль совпадают
            }
        }
        return null; // Если пользователь не найден или пароль неверный
    }




    @Override
    public List<User> getAll() throws IOException {
        return JsonUtilsForUsers.readJsonToList(PATH);
    }

    @Override
    public void delete(User user) throws IOException {
        List<User> userList = getAll();
        userList.removeIf(user1 -> user1.equals(user));

        JsonUtilsForUsers.writeListToJson(userList, PATH);
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param userNew объект User с обновленной информацией
     */
    @Override
    public void update(User userNew) throws IOException {
        List<User> userList = getAll();
        for (User user : userList) {
            if (Objects.equals(user.getUserName(), userNew.getUserName())) {
                user.setPassword(userNew.getPassword());
                break;
            }
        }
        JsonUtilsForUsers.writeListToJson(userList, PATH);
    }

    /**
     * Сохраняет нового пользователя.
     * Используется для регистрации нового пользователя.
     *
     * @param user объект User, который нужно сохранить
     * @throws IOException если не удалось сохранить пользователя в файл
     */
    @Override
    public void save(User user) throws IOException {
        boolean flag = true;
        List<User> userList = getAll();
        for (User user_old : userList) {
            if (Objects.equals(user_old.getUserName(), user.getUserName())) {
                flag = false;
                break;
            }
        }
        if(flag){
            userList.add(user);
            JsonUtilsForUsers.writeListToJson(userList, PATH);
        }

    }
}
