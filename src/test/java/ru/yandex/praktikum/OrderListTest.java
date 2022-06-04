package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import ru.yandex.praktikum.model.OrderList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class OrderListTest {


    @Test
    @DisplayName("Получение списка заказа") // имя теста
    @Description("Получить весь список заказов, проверить что он не пустой")
    public  void  getOrderListTest() {


        List<OrderList> orderLists = given()
                .when()
                .contentType(ContentType.JSON)
                .get(BaseApiClient.BASE_URL + "/api/v1/orders")
                .then().log().all()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList("orders",OrderList.class);
        MatcherAssert.assertThat(orderLists, notNullValue());

    }

}
