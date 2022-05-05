package ru.yandex.praktikum;

import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
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
    ordersClient = new OrdersClient();
  }

  @Test
  public void orderCreateHappyPassTest2() {
    order = Order.defaultOrder();
    int orderTrack = ordersClient.create(order);
    int orderId = ordersClient.track(orderTrack);
  }

  @Test
  public void orderList() {
    Response response = ordersClient.list();
    List<String> jsonResponse = response.jsonPath().getList("orders.id");
    System.out.println(jsonResponse.toString());
  }
}
