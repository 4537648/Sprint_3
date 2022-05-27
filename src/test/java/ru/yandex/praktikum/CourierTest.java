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

@Feature("Тесты создания курьера")
@DisplayName("Тесты создания курьера")
public class CourierTest extends Config{

  private ValidatableResponse response;
  private Courier courier;
  private CourierClient courierClient;
  private int couriedId;

  @Before
  public void setUp() {
    if(LOG_STATUS) { turnOnLogging();}
    courierClient = new CourierClient();
    courier = Courier.getRandom();
  }

  @Test
  @DisplayName("Проверка успешного создания курьера")
  public void courierCreateHappyPassTest() {
    response = courierClient.create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    assertEquals(201, response.extract().statusCode());
    assertTrue(response.extract().path("ok"));
  }

  @Test
  @DisplayName("Проверка ошибки при создании дублирующего курьера")
  public void courierCreateDuplicatedErrorTest() {
    response = courierClient.create(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    courier.setPassword("dupePassword");
    response = courierClient.create(courier);
    assertEquals(409, response.extract().statusCode());
    assertEquals("Этот логин уже используется. Попробуйте другой.", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при создании курьера без заполнения логина")
  public void courierCreateWithoutLoginErrorTest() {
    courier.setLogin("");
    response = courierClient.create(courier);
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при создании курьера без заполнения пароля")
  public void courierCreateWithoutPasswordErrorTest() {
    courier.setPassword("");
    response = courierClient.create(courier);
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
  }

  @Test
  @DisplayName("Проверка ошибки при создании курьера без заполнения имени")
  public void courierCreateWithoutFirstNameErrorTest() {
    courier.setFirstName("");
    response = courierClient.create(courier);
    assertEquals(400, response.extract().statusCode());
    assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
  }

  @After
  public void tearDown() {
    if (couriedId > 0) {
      courierClient.delete(couriedId);
    }
  }
}