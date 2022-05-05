package ru.yandex.praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.orders.Order;
import ru.yandex.praktikum.orders.OrdersClient;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrdersColorParamTest {

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
    ordersClient = new OrdersClient();
  }

  @Test
  public void orderCreateHappyPassTest() {
    order = Order.cplorSelectOrder(color);
    int orderTrack = ordersClient.create(order);
    System.out.println(orderTrack);
    assertNotEquals(0, orderTrack);
  }
}
