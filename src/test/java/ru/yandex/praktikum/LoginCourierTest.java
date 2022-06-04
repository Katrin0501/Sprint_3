package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierCredentials;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.CourierClient.*;
import static ru.yandex.praktikum.model.Courier.getRandomCourier;

public class LoginCourierTest {
    int courierId;
    Courier courier;

    @Before
    public void init() {
        courier = getRandomCourier();
    }

    @Test
    @DisplayName("Авторизация курьера") // имя теста
    @Description("Успешная авторизация ")

    public void loginCourierTest() {
        //Создание курьера
        createCourier(courier);
        //Ввод логина
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = loginCourier(courierCredentials);
        //Ответ об успешной авторизации
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = responseLogin.body().jsonPath().getInt("id");
        assertEquals(courierId, responseLogin.body().jsonPath().getInt("id"));
    }

    @Test
    @DisplayName("Неудачная авторизация курьера") // имя теста
    @Description("Не верно указан логин и пароль ")
    public void loginIncorrectCourierTest() {
        //Создание курьера
        createCourier(courier);
        //Ввод несуществующего логина
        CourierCredentials courierCredentials = new CourierCredentials(" ", " ");
        //Провека Логирование курьера
        Response responseLogin = loginCourier(courierCredentials);
        //Ответ об ошибке авторизации
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        assertEquals("Учетная запись не найдена", responseLogin.body().jsonPath().getString("message") );


    }

    @Test
    @DisplayName("Не указан логин при авторизации курьера") // имя теста
    @Description("Недостаточно данных для входа/Логин=пусто")
    public void noLoginCourierTest() {

        //Ввод пароля, логин отсутствует
        CourierCredentials courierCredentials = new CourierCredentials("", courier.getPassword());
        //Провека Логирование курьера
        Response responseLogin = loginCourier(courierCredentials);
        //Ответ об ошибке авторизации
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        assertEquals( "Недостаточно данных для входа",responseLogin.body().jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Не указан пароль при авторизации курьера") // имя теста
    @Description("Недостаточно данных для входа/Пароль=пусто")
    public void noPasswordCourierTest() {
        //Ввод логина, пароль отсутствует
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), "");
        //Провека Логирование курьера
        Response responseLogin = loginCourier(courierCredentials);
        //Ответ об ошибке авторизации
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        assertEquals("Недостаточно данных для входа", responseLogin.body().jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Авторизация под несуществующем пользователем") // имя теста
    @Description("Учетная запись не найдена")
    public void courierIsNotFoundTest() {
        //Ввод рандомного логина
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        //Провека Логирование курьера
        Response responseLogin = loginCourier(courierCredentials);
        //Ответ ошибке авторизации
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        assertEquals("Учетная запись не найдена", responseLogin.body().jsonPath().getString("message"));

    }
    @Test
    @DisplayName("Авторизация c незаполненными Логин и Пароль ") // имя теста
    @Description("Недостаточно данных для входа(Логин и пароль не заполнены),")
    public void emptyLoginPasswordTest () {
        //Ввод рандомного логина
        CourierCredentials courierCredentials = new CourierCredentials();
        //Провека Логирование курьера
        Response responseLogin = loginCourier(courierCredentials);
        //Ответ ошибке авторизации
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        assertEquals("Недостаточно данных для входа", responseLogin.body().jsonPath().getString("message"));

    }



    @After
    public void clear() {
        if (courierId != 0) {

            deleteCourier(courierId);
            courierId = 0;
        }

    }
}
