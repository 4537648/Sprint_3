package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.courier.Courier;
import ru.yandex.praktikum.courier.CourierClient;
import ru.yandex.praktikum.courier.CourierCredentials;

import static org.junit.Assert.*;

public class LoginTest {

  private Courier courier;
  private CourierClient courierClient;
  private String errMsg;
  private int couriedId;

  @Before
  public void setUp() {
    courierClient = new CourierClient();
    courier = Courier.getRandom();
    boolean created = courierClient.createOld(courier);
    couriedId = courierClient.login(CourierCredentials.from(courier));
    assertTrue(created);
  }

  @Test
  public void courierLoginHappyPassTest() {
    assertNotEquals(0, couriedId);
  }

  @Test
  public void courierLoginEmptyLoginErrorTest() {
    courier.setLogin("");
    errMsg = courierClient.loginWithoutFullCreds(CourierCredentials.from(courier));
    assertEquals("Недостаточно данных для входа", errMsg);
  }

  @Test
  public void courierLoginEmptyPasswordErrorTest() {
    courier.setPassword("");
    errMsg = courierClient.loginWithoutFullCreds(CourierCredentials.from(courier));
    assertEquals("Недостаточно данных для входа", errMsg);
  }

  @Test
  public void courierLoginNullLoginErrorTest() {
    courier.setLogin(null);
    errMsg = courierClient.loginWithoutFullCreds(CourierCredentials.from(courier));
    assertEquals("Недостаточно данных для входа", errMsg);
  }
/*
  @Test
  public void courierLoginNullPasswordErrorTest() {
    courier.setPassword(null);
    errMsg = courierClient.loginWithoutFullCreds(CourierCredentials.from(courier));
    assertEquals("Недостаточно данных для входа", errMsg);
  }
*/

  @Test
  public void courierLoginWrongLoginErrorTest() {
    courier.setLogin(courier.getLogin() + "WRONG");
    errMsg = courierClient.loginWithWrongCreds(CourierCredentials.from(courier));
    assertEquals("Учетная запись не найдена", errMsg);
  }

  @Test
  public void courierLoginWrongPasswordErrorTest() {
    courier.setPassword("wrongpassword");
    errMsg = courierClient.loginWithWrongCreds(CourierCredentials.from(courier));
    assertEquals("Учетная запись не найдена", errMsg);
  }

  @After
  public void tearDown() {
    if (couriedId > 0) {
      courierClient.delete(couriedId);
      System.out.println("Test courier deleted successfully");
    }
  }
}
