package ru.yandex.praktikum.courier;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class Courier {
  private String login;
  private String password;
  private String firstName;

  public Courier(String login, String password, String firstName) {
    this.login = login;
    this.password = password;
    this.firstName = firstName;
  }

  public static Courier getRandom() {
    String login = RandomStringUtils.randomAlphanumeric(10);
    String password = RandomStringUtils.randomAlphanumeric(10);
    String firstName = RandomStringUtils.randomAlphanumeric(10);
    return new Courier(login, password, firstName);
  }

  public static Courier getRandomWithoutLogin() {
    String login = null;
    String password = RandomStringUtils.randomAlphanumeric(10);
    String firstName = RandomStringUtils.randomAlphanumeric(10);
    return new Courier(login, password, firstName);
  }

  public static Courier getRandomWithoutPassword() {
    String login = RandomStringUtils.randomAlphanumeric(10);
    String password = null;
    String firstName = RandomStringUtils.randomAlphanumeric(10);
    return new Courier(login, password, firstName);
  }

  public static Courier getRandomWithoutFirstName() {
    String login = RandomStringUtils.randomAlphanumeric(10);
    String password = RandomStringUtils.randomAlphanumeric(10);
    String firstName = null;
    return new Courier(login, password, firstName);
  }
}
