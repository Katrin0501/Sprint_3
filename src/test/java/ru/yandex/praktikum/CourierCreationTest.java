package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierCredentials;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.praktikum.CourierClient.*;
import static ru.yandex.praktikum.model.Courier.getRandomCourier;


public class CourierTest {
    Courier courier;
    int courierId = 0;

    @Before
    public void init() {
        courier = getRandomCourier();
    }


    @Test
    @DisplayName("Создание курьера") // имя теста
    @Description("Базовый тест - успешноу создание курьера")
    public void CourierTest() {

        //Действия
        Response responseCreate = createCourier(courier);
        //Проверка
        assertEquals(SC_CREATED, responseCreate.statusCode());
        assertTrue(responseCreate.body().jsonPath().getBoolean("ok"));
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = loginCourier(courierCredentials);
        assertEquals(SC_OK,responseLogin.statusCode());
        courierId = responseLogin.body().jsonPath().getInt("id");

    }

    @Test
    @DisplayName("Нет логина") // имя теста
    @Description("Недостаточно данных для создания учетной записи")
    public void CourierNotLoginTest() {

        Courier courier = new Courier("", getRandomCourier().getPassword(), getRandomCourier().getFirstName());
        Response responseCreate = createCourier(courier);
        responseCreate.then().log().all().extract();
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals(responseCreate.body().jsonPath().getString("message"),"Недостаточно данных для создания учетной записи");



    }

    @Test
    @DisplayName("Нет пароля") // имя теста
    @Description("Недостаточно данных для создания учетной записи")
    public void CourierNotPasswordTest() {
        Courier courier = new Courier(getRandomCourier().getLogin(), "", getRandomCourier().getFirstName());
        Response responseCreate = createCourier(courier);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals(responseCreate.body().jsonPath().getString("message"),"Недостаточно данных для создания учетной записи");



    }


    @Test
    @DisplayName("Одинаковый логин") // имя теста
    @Description("Логин уже используется")

    public void CourierSameLoginTest() {
        String login = "Самый лучший курьер";

        Courier courier = new Courier(login, getRandomCourier().getPassword(), getRandomCourier().getFirstName());
        createCourier(courier);
        Courier courierTwo = new Courier(login, getRandomCourier().getPassword(), getRandomCourier().getFirstName());
        Response responseCreateTwo = createCourier(courierTwo);
        Assert.assertEquals(SC_CONFLICT, responseCreateTwo.statusCode());
        assertEquals(responseCreateTwo.body().jsonPath().getString("message"),"Этот логин уже используется");


    }

    @Test
    @DisplayName("Создание 2х одинаковых курьеров") // имя теста
    @Description("Этот логин уже используется")
    public void CourierOneToOneTest() {
        Courier courier = new Courier("Kva4", "La4", "Da4");
        Response responseCreate = createCourier(courier);
        Courier courierTwo = new Courier("Kva4", "La4", "Da4");
        Response responseCreateTwo = createCourier(courierTwo);
        responseCreateTwo.then().log().all().extract();
        Assert.assertEquals(SC_CONFLICT, responseCreateTwo.statusCode());
        assertEquals(responseCreateTwo.body().jsonPath().getString("message"),"Этот логин уже используется");

    }


    @After
    public void clear() {
        if (courierId != 0) {

            deleteCourier(courierId);
            courierId = 0;
        }

    }


}
