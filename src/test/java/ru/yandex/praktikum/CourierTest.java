package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.courier.Courier;
import ru.yandex.praktikum.courier.CourierClient;
import ru.yandex.praktikum.courier.CourierCredentials;

import static org.junit.Assert.*;

public class CourierTest {

  private Courier courier;
  private CourierClient courierClient;
  private int couriedId;

  @Before
  public void setUp() {
    courierClient = new CourierClient();
  }

  @Test
  public void courierCreationHappyPassTest() {
    courier = Courier.getRandom();
    boolean created = courierClient.create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier));
    assertTrue(created);
    assertNotEquals(0, couriedId);
  }

  @Test
  public void courierDuplicatedCreationErrorTest() {
    courier = Courier.getRandom();
    boolean created = courierClient.create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier));
    courier.setPassword("dupePassword");
    String createdDuplicate = courierClient.createDuplicate(courier);
    assertEquals("Этот логин уже используется. Попробуйте другой.", createdDuplicate);
  }

  @Test
  public void courierWOPasswordErrorTest() {
    courier = Courier.getRandomWithoutPassword();
    String created = courierClient.createWithoutFullCreds(courier);
    assertEquals("Недостаточно данных для создания учетной записи", created);
  }

  @Test
  public void courierWOLoginErrorTest() {
    courier = Courier.getRandomWithoutLogin();
    String created = courierClient.createWithoutFullCreds(courier);
    assertEquals("Недостаточно данных для создания учетной записи", created);
  }

  @Test
  public void courierWOfirstNameErrorTest() {
    courier = Courier.getRandomWithoutFirstName();
    String created = courierClient.createWithoutFullCreds(courier);
    assertEquals("Недостаточно данных для создания учетной записи", created);
  }

  @After
  public void tearDown() {
    if (couriedId > 0) {
      courierClient.delete(couriedId);
      System.out.println("Test courier deleted successfully");
    }
  }
}