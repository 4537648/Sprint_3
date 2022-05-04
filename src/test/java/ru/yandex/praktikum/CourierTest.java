package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.courier.Courier;
import ru.yandex.praktikum.courier.CourierClient;
import ru.yandex.praktikum.courier.CourierCredentials;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CourierTest {

  private CourierClient courierClient;
  private int couriedId;

  @Before
  public void setUp() {
    courierClient = new CourierClient();

  }

  @Test
  public void courier() {
    Courier courier = Courier.getRandom();
    boolean created = courierClient.create(courier);

    CourierCredentials creds =  CourierCredentials.from(courier);
    couriedId = courierClient.login(creds);

    assertTrue(created);
    assertNotEquals(0, couriedId);
  }

  @After
  public  void tearDown() {
    courierClient.delete(couriedId);
  }
}