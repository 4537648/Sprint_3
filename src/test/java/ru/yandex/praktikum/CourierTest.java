package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
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
  private String errMsg;
  private int statusCode;
  private int couriedId;

  @Before
  public void setUp() {
    courierClient = new CourierClient();
  }

  @Test
  public void courierCreateHappyPassTest() {
    courier = Courier.getRandom();
    ValidatableResponse responce = courierClient.create(courier);
    statusCode = responce.extract().statusCode();
    couriedId = responce.extract().path("ok");
    assertEquals(statusCode, 201);
    assertNotEquals(0, couriedId);
  }

  @Test
  public void courierCreateHappyPassTestOld() {
    courier = Courier.getRandom();
    boolean created = courierClient.createOld(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier));
    assertTrue(created);
    assertNotEquals(0, couriedId);
  }

  @Test
  public void courierCreateDuplicatedErrorTest() {
    courier = Courier.getRandom();
    boolean created = courierClient.createOld(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier));
    courier.setPassword("dupePassword");
    String createdDuplicate = courierClient.createDuplicate(courier);
    assertEquals("Этот логин уже используется. Попробуйте другой.", createdDuplicate);
  }

  @Test
  public void courierCreateWithoutPasswordErrorTest() {
    courier = Courier.getRandomWithoutPassword();
    errMsg = courierClient.createWithoutFullCreds(courier);
    assertEquals("Недостаточно данных для создания учетной записи", errMsg);
  }

  @Test
  public void courierCreateWithoutLoginErrorTest() {
    courier = Courier.getRandomWithoutLogin();
    errMsg = courierClient.createWithoutFullCreds(courier);
    assertEquals("Недостаточно данных для создания учетной записи", errMsg);
  }

  @Test
  public void courierCreateWithoutFirstNameErrorTest() {
    courier = Courier.getRandomWithoutFirstName();
    errMsg = courierClient.createWithoutFullCreds(courier);
    assertEquals("Недостаточно данных для создания учетной записи", errMsg);
  }

  @After
  public void tearDown() {
    if (couriedId > 0) {
      courierClient.delete(couriedId);
      System.out.println("Test courier deleted successfully");
    }
  }
}