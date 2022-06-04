package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.model.CreateOrder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ru.yandex.praktikum.CourierClient.createOrders;
import static ru.yandex.praktikum.model.CreateOrder.getRandomOrder;


@RunWith(Parameterized.class)
public class CreatingOrdersTest {

    private final String black;
    private final String grey;

    public CreatingOrdersTest(String black, String grey) {
        this.black = black;
        this.grey = grey;
    }


    @Parameterized.Parameters
    public static Object[] getColor() {
        return new Object[][]{
                {"BLACK",""},
                {"GREY",""},
                {"BLACK","GREY"},
                {"",""}

        };
    }


    @Test
    @DisplayName("Создание заказа") // имя теста
    @Description("Возможность создать заказ с указанием черного, серого цветов и без указания цвета")
    public void  testParametrizedCourier(){

        CreateOrder createOrder = new CreateOrder(getRandomOrder().getFirstName(),getRandomOrder().getLastName(),getRandomOrder().getAddress()
                ,getRandomOrder().getMetroStation(),getRandomOrder().getPhone(),getRandomOrder().getRentTime()
                , getRandomOrder().getDeliveryDate(), getRandomOrder().getComment(),getRandomOrder().getColor());
        List<String>colors = Stream.of(black,grey).filter(x->!x.isBlank()).collect(Collectors.toList());
        createOrder.setColor(colors);
        Response responseOrders = createOrders(createOrder);
       assertEquals(SC_CREATED,responseOrders.statusCode());
        MatcherAssert.assertThat(responseOrders.body().jsonPath().getInt( "track"), notNullValue());


    }

}
