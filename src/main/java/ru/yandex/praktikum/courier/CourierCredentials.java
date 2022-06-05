package ru.yandex.praktikum.courier;

import lombok.Data;

@Data
public class CourierCredentials {
  private String login;
  private String password;

  public CourierCredentials (Courier courier) {
    this.login = courier.getLogin();
    this.password = courier.getPassword();
  }

  public static CourierCredentials from(Courier courier) {
    return new CourierCredentials(courier);
  }
}
