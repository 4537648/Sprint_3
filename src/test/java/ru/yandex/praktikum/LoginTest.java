package ru.yandex.praktikum;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.courier.Courier;
import ru.yandex.praktikum.courier.CourierClient;
import ru.yandex.praktikum.courier.CourierCredentials;

import static org.junit.Assert.*;

@Feature("Тесты авторизации курьера")
@DisplayName("Тесты авторизации курьера")
public class LoginTest extends Config {

  private ValidatableResponse response;
  private Courier courier;
  private CourierClient courierClient;
  private int couriedId;

  @Before
  public void setUp() {
    if(LOG_STATUS) { turnOnLogging();}
    courierClient = new CourierClient();
    courier = Courier.getRandom();
    response = new CourierClient().create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    assertEquals(201, response.extract().statusCode());
    assertTrue(response.extract().path("ok"));
  }

  @Test
  @DisplayName("Проверка успешной авторизации курьера")
  public void courierLoginHappyPassTest() {
    assertNotEquals(0, couriedId);
  }

  @Test
  @DisplayName("Проверка ошибки при авторизации курьера без заполнения логина")
  public void courierLoginEmptyLoginErrorTest() {
    courier.setLogin("");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при авторизации курьера без заполнения пароля")
  public void courierLoginEmptyPasswordErrorTest() {
    courier.setPassword("");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при авторизации курьера без передачи логина")
  public void courierLoginNullLoginErrorTest() {
    courier.setLogin(null);
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при авторизации курьера без передачи пароля")
  public void courierLoginNullPasswordErrorTest() {
    courier.setPassword(null);
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для входа", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при авторизации курьера с некорректным логином")
  public void courierLoginWrongLoginErrorTest() {
    courier.setLogin(courier.getLogin() + "WRONG");
    response = new CourierClient().login(CourierCredentials.from(courier));
    assertEquals(404, response.extract().statusCode());
    assertEquals("Учетная запись не найдена", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при авторизации курьера с некорректным паролем")
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