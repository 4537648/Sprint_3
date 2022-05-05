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

public class CourierTest {

  private ValidatableResponse response;
  private Courier courier;
  private CourierClient courierClient;
  private int couriedId;

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    courierClient = new CourierClient();
    courier = Courier.getRandom();
  }

  @Test
  public void courierCreateHappyPassTest() {
    response = courierClient.create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    assertEquals(201, response.extract().statusCode());
    assertTrue(response.extract().path("ok"));
  }

  @Test
  public void courierCreateDuplicatedErrorTest() {
    response = courierClient.create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    courier.setPassword("dupePassword");
    response = courierClient.create(courier);
    assertEquals(409, response.extract().statusCode());
    assertEquals("Этот логин уже используется. Попробуйте другой.", response.extract().path("message"));
  }

  @Test
  public void courierCreateWithoutLoginErrorTest() {
    courier.setLogin("");
    response = courierClient.create(courier);
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
  }

  @Test
  public void courierCreateWithoutPasswordErrorTest() {
    courier.setPassword("");
    response = courierClient.create(courier);
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
  }

/*
  @Test
  public void courierCreateWithoutFirstNameErrorTest() {
    courier.setFirstName("");
    response = courierClient.create(courier);
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
  }
*/

  @After
  public void tearDown() {
    if (couriedId > 0) {
      courierClient.delete(couriedId);
    }
  }
}