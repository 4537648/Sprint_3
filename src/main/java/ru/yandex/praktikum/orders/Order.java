package ru.yandex.praktikum.orders;

import lombok.Data;

import java.util.List;

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
            "Naruto",
            "Uchiha",
            "Konoha, 142 apt.",
            4,
            "+7 800 355 35 35",
            5,
            "2020-06-06",
            "Saske, come back to Konoha",
            List.of("BLACK"));
  }


  public static Order cplorSelectOrder(List<String> color) {
   return new Order(
           "Naruto",
           "Uchiha",
           "Konoha, 142 apt.",
           4,
           "+7 800 355 35 35",
           5,
           "2020-06-06",
           "Saske, come back to Konoha",
           color);
  }
}
