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
        return String.format("OrderList{id='%s', courierId='%s', firstName='%s', lastName='%s', address='%s'" +
                ", metroStation='%s', phone='%s', rentTime=%s', deliveryDate=%s', track=%s', color=%s', comment='%s', createdAt=%s', updatedAt=%s', status=%s'}",
                id,courierId,firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,track,color,comment,createdAt,updatedAt,status);
    }
}
