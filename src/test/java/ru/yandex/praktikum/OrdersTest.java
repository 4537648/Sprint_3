package ru.yandex.praktikum;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.orders.Order;
import ru.yandex.praktikum.orders.OrdersClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@Feature("Тесты создания заказа")
@DisplayName("Тесты создания заказа")
public class OrdersTest extends Config {

  private OrdersClient ordersClient;
  private Order order;

  @Before
  public void setUp() {
    if(LOG_STATUS) { turnOnLogging();}
    ordersClient = new OrdersClient();
  }

  @Test
  @DisplayName("Проверка успешного создания заказа")
  public void orderCreateHappyPassTest() {
    order = Order.defaultOrder();
    ValidatableResponse response = ordersClient.create(order);
    assertNotEquals(0, (int)response.extract().path("track"));
  }

  @Test
  @DisplayName("Проверка успешного получения списка всех заказов")
  public void orderListTest() {
    ValidatableResponse response = ordersClient.list();
    assertEquals(
            (response.extract().jsonPath().getList("orders")).size(),
            (response.extract().jsonPath().getList("orders.id")).size());
    /*
    я исхожу из того, что если мы получаем массив orders,
    то сколько бы ни было в нем объектов, мы должны получить
    такое же количество ключевого параметра объекта - таковым я выбрал id
    если придет пустой массив, значит в системе нет заказов, и проверка будет успешной
    не вижу в этом противоречий, так как в спеке не заявлено, что заказов не может быть ноль
    больше идей, к сожалению, не возникло
     */

    }
}