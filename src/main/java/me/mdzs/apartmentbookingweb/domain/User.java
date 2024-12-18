package me.mdzs.apartmentbookingweb.domain;

import java.util.Objects;

public class User {
    private String userName;

    private String password;

    private Boolean isAdmin;

    public User(String userName, String userPassword, boolean isAdmin) {
        this.userName = userName;
        this.password = userPassword;
        this.isAdmin = isAdmin;
    }
    public User(String userName, String userPassword) {
        this.userName = userName;
        this.password = userPassword;
        this.isAdmin = Boolean.FALSE;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                "password='" + password + '\'' +
                "isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User userAsUser = (User) o;
        return (Objects.equals(this.userName, userAsUser.userName) && Objects.equals(this.password, userAsUser.password));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }

    public User makeAdmin(User user){
        user.isAdmin = true;
        return user;
    }
}
