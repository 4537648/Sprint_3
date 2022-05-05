package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.orders.Order;
import ru.yandex.praktikum.orders.OrdersClient;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class OrdersTest {

  private OrdersClient ordersClient;
  private Order order;

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    ordersClient = new OrdersClient();
  }

  @Test
  public void orderCreateHappyPassTest() {
    order = Order.defaultOrder();
    ValidatableResponse response = ordersClient.create(order);
    assertNotEquals(0, (int)response.extract().path("track"));
  }

  @Test
  public void orderList() {
    Response response = ordersClient.list();
    List<String> jsonResponse = response.jsonPath().getList("orders.id");
    System.out.println(jsonResponse.toString());
  }
}
