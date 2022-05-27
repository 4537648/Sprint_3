package ru.yandex.praktikum;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.orders.Order;
import ru.yandex.praktikum.orders.OrdersClient;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@Feature("Параметризованные тесты создания заказа")
@DisplayName("Параметризованные тесты создания заказа")
@RunWith(Parameterized.class)
public class OrdersColorParamTest extends Config {

  private OrdersClient ordersClient;
  private Order order;

  private final List<String> color;

  public OrdersColorParamTest(List<String> color) {
    this.color = color;
  }

  @Parameterized.Parameters
  public static Object[] orderTestParams() {
    return new Object[]{
            List.of("BLACK"),
            List.of("GREY"),
            List.of("BLACK", "GREY"),
            List.of()};
  }

  @Before
  public void setUp() {
    if(LOG_STATUS) { turnOnLogging();}
    ordersClient = new OrdersClient();
  }

  @Test
  @DisplayName("Проверка создания заказа с различным набором цвета")
  public void orderCreateHappyPassTest() {
    order = Order.cplorSelectOrder(color);
    ValidatableResponse response = ordersClient.create(order);
    assertNotEquals(0, (int)response.extract().path("track"));
  }
}