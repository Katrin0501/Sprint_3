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


public class CourierCreationTest {
    Courier courier;
    int courierId;

    @Before
    public void init() {
        courier = getRandomCourier();
    }


    @Test
    @DisplayName("Создание курьера") // имя теста
    @Description("Базовый тест - успешного создания курьера и проверки его id")
    public void successfulCreationTest() {

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
    public void courierNotLoginTest() {

        Courier courier = new Courier("", getRandomCourier().getPassword(), getRandomCourier().getFirstName());
        Response responseCreate = createCourier(courier);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи",responseCreate.body().jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Нет пароля") // имя теста
    @Description("Недостаточно данных для создания учетной записи")
    public void courierNotPasswordTest() {
        Courier courier = new Courier(getRandomCourier().getLogin(), "", getRandomCourier().getFirstName());
        Response responseCreate = createCourier(courier);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи",responseCreate.body().jsonPath().getString("message"));

    }


    @Test
    @DisplayName("Одинаковый логин") // имя теста
    @Description("Логин уже используется")

    public void courierSameLoginTest() {
        String login = "Самый лучший курьер";

        Courier courier = new Courier(login, getRandomCourier().getPassword(), getRandomCourier().getFirstName());
        createCourier(courier);
        Courier courierTwo = new Courier(login, courier.getPassword(), courier.getFirstName());
        Response responseCreate = createCourier(courierTwo);
        Assert.assertEquals(SC_CONFLICT, responseCreate.statusCode());
        assertEquals("Этот логин уже используется",responseCreate.body().jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание 2х одинаковых курьеров") // имя теста
    @Description("Этот логин уже используется")
    public void courierOneToOneTest() {
        Courier courier = new Courier(getRandomCourier().getLogin(), getRandomCourier().getPassword(), getRandomCourier().getFirstName());
        createCourier(courier);
        Courier courierTwo = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        Response responseCreate = createCourier(courierTwo);
        Assert.assertEquals(SC_CONFLICT, responseCreate.statusCode());
        assertEquals("Этот логин уже используется",responseCreate.body().jsonPath().getString("message"));

    }


    @After
    public void clear() {
        if (courierId != 0) {

            deleteCourier(courierId);
            courierId = 0;
        }

    }


}
