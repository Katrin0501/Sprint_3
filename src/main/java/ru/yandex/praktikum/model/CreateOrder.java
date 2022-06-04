package ru.yandex.praktikum.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;

public class CreateOrder {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Number rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;



    public CreateOrder(String firstName, String lastName, String address, String metroStation, String phone, Number rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public Number getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }


    public static CreateOrder getRandomOrder() {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(20);
        String metroStation = RandomStringUtils.randomAlphabetic(12);
        String phone = RandomStringUtils.randomAlphabetic(10);
        Integer rentTime = (int) (Math.random() * 10);
        String deliveryDate = "2021-05-05";//RandomStringUtils.randomAlphabetic(10);
        String comment = RandomStringUtils.randomAlphabetic(20);
        List<String> color = Collections.singletonList(RandomStringUtils.randomAlphabetic(10));


        return new CreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

    }

    @Override
    public String toString() {
        return String.format("CreateOrder{firstName='%s', lastName='%s', address='%s', metroStation='%s', phone='%s', rentTime=%s', deliveryDate='%s', comment='%s', color=%s'}"
                ,firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,color);
    }

}
