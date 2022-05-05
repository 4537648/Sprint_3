package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.courier.Courier;
import ru.yandex.praktikum.courier.CourierClient;
import ru.yandex.praktikum.courier.CourierCredentials;

import static org.junit.Assert.*;

public class LoginTest {

  private ValidatableResponse response;
  private Courier courier;
  private CourierClient courierClient;
  private int couriedId;

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    courierClient = new CourierClient();
    courier = Courier.getRandom();
    response = new CourierClient().create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    assertEquals(201, response.extract().statusCode());
    assertTrue(response.extract().path("ok"));
  }

  @Test
  public void courierLoginHappyPassTest() {
    assertNotEquals(0, couriedId);
  }

  @Test
  public void courierLoginEmptyLoginErrorTest() {
    courier.setLogin("");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  public void courierLoginEmptyPasswordErrorTest() {
    courier.setPassword("");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  public void courierLoginNullLoginErrorTest() {
    courier.setLogin(null);
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  public void courierLoginNullPasswordErrorTest() {
    courier.setPassword(null);
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  public void courierLoginWrongLoginErrorTest() {
    courier.setLogin(courier.getLogin() + "WRONG");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(404, response.extract().statusCode());
    assertEquals("Учетная запись не найдена", response.extract().path("message"));
  }

  @Test
  public void courierLoginWrongPasswordErrorTest() {
    courier.setPassword("wrongpassword");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(404, response.extract().statusCode());
    assertEquals("Учетная запись не найдена", response.extract().path("message"));
  }

  @After
  public void tearDown() {
    if (couriedId > 0) {
      courierClient.delete(couriedId);
    }
  }
}
