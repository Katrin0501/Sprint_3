package ru.yandex.praktikum.model;

import java.util.Date;
import java.util.List;

public class OrderList {
    public String id;
    public Integer courierId;
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public Integer rentTime;
    public Date deliveryDate;
    public Integer track;
    public List<String> color;
    public String comment;
    public Date createdAt;
    public Date updatedAt;
    public Integer status;

    @Override
    public String toString() {
        return "OrderList{" +
                "id='" + id + '\'' +
                ", courierId=" + courierId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation + '\'' +
                ", phone='" + phone + '\'' +
                ", rentTime=" + rentTime +
                ", deliveryDate=" + deliveryDate +
                ", track=" + track +
                ", color=" + color +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                '}';
    }
}
