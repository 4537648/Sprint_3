package ru.yandex.praktikum.orders;

import com.github.javafaker.Faker;
import lombok.Data;

import java.util.List;
import java.util.Locale;

@Data
public class Order {
  private String firstName;
  private String lastName;
  private String address;
  private int metroStation;
  private String phone;
  private int rentTime;
  private String deliveryDate;
  private String comment;
  private List<String> color;

  static Faker faker = new Faker(new Locale("ru"));

  public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.metroStation = metroStation;
    this.phone = phone;
    this.rentTime = rentTime;
    this.deliveryDate = deliveryDate;
    this.comment = comment;
    this.color = color;
  }

  public static Order defaultOrder() {
    return new Order(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.address().streetAddress(),
            4,
            faker.phoneNumber().cellPhone(),
            5,
            "2020-06-06",
            "Saske, come back to Konoha",
            List.of("BLACK"));
  }


  public static Order cplorSelectOrder(List<String> color) {
   return new Order(
           faker.name().firstName(),
           faker.name().lastName(),
           faker.address().streetAddress(),
           4,
           faker.phoneNumber().cellPhone(),
           5,
           "2020-06-06",
           faker.backToTheFuture().quote(),
           color);
  }
}
