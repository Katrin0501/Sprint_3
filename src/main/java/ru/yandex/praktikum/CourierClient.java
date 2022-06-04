package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierCredentials;
import ru.yandex.praktikum.model.CreateOrder;
import ru.yandex.praktikum.model.OrderList;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class CourierClient extends BaseApiClient {

@Step("Создать курьера")
    public static Response createCourier(Courier courier) {

        return (Response) given()
                .spec(getSeqSpec())
                .body(courier)
                .when()
                .post(BASE_URL + "/api/v1/courier").then().log().all().extract();

    }

    @Step("Логин курьера")
    public static Response loginCourier(CourierCredentials courierCredentials) {

        return (Response) given()
                .spec(getSeqSpec())
                .body(courierCredentials)
                .when()
                .post(BASE_URL + "/api/v1/courier/login")
                .then().log().all().extract();
    }

    @Step("Удалить курьера")
    public static Boolean deleteCourier(Integer courier) {

        return given()
                .spec(getSeqSpec())
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + courier)
                .then()
                .assertThat().log().all()
                .statusCode(200)
                .extract()
                .path("ok");

    }

    @Step("Создать заказ")
    public static Response createOrders(CreateOrder createOrder) {

        return (Response) given()
                .spec(getSeqSpec())
                .body(createOrder)
                .when()
                .post(BASE_URL + "/api/v1/orders")
                .then()
                .log().all()
               .extract();


    }

}
