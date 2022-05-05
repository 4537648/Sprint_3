package ru.yandex.praktikum.courier;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RestAssuredClient {

  protected final String URL = "https://qa-scooter.praktikum-services.ru/api/v1";

  protected final RequestSpecification reqSpec =
          given()
          .header("Content-Type", "application/json")
          .baseUri(URL);
}
