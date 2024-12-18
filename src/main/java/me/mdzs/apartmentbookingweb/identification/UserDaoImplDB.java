package me.mdzs.apartmentbookingweb.identification;


import me.mdzs.apartmentbookingweb.domain.User;
import me.mdzs.apartmentbookingweb.utils.DBUtils;

import java.util.List;
import java.util.Objects;


public class UserDaoImplDB implements UserDao<User>{
    private static final String URL = "jdbc:sqlite:users.db";


    @Override
    public User getUser(String userName) {
        return DBUtils.getUser(userName, URL);
    }

    private Boolean check(String userName, String password) {
        User user = new User(userName, password);
        return DBUtils.checkIfUserExist(user, URL);
    }


    @Override
    public List<User> getAll() {
        return DBUtils.getAllUsers(URL);
    }

    @Override
    public void delete(User user) {
        DBUtils.removeUser(user, URL);
    }

    @Override
    public void update(User userNew) {
        List<User> userList = getAll();
        for (User user : userList) {
            if (Objects.equals(user.getUserName(), userNew.getUserName())) {
                User userUpdated = new User(user.getUserName(), userNew.getPassword());
                //удалить старого юзера
                delete(user);
                //добавить нового юзера
                save(userUpdated);
                break;
            }
        }
//        JsonUtils.writeListToJson(userList, PATH);
    }

    @Override
    public void save(User user) {
        boolean flag = true;
        List<User> userList = getAll();
        for (User user_old : userList) {
            if (Objects.equals(user_old.getUserName(), user.getUserName())) {
                flag = false;
                break;
            }
        }
        //////////////////REMAKE
        if(flag){
            userList.add(user);
            DBUtils.addUser(user, URL);
        }
    }
}
