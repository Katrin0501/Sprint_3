package ru.yandex.praktikum.model;

public class CourierCredentials {
    public String login;
    public String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public  CourierCredentials(){}

    @Override
    public String toString() {
        return String.format("CourierCredentials{login='%s', password='%s'}", login, password);
    }
}
