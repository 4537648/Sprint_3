package ru.yandex.praktikum.orders;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.courier.Courier;
import ru.yandex.praktikum.courier.RestAssuredClient;

public class OrdersClient extends RestAssuredClient {

  private final String ROOT = "/orders";
  private final String TRACK = ROOT + "/track";
  private final String TRACK_ID = TRACK + "?t={trackId}";

  public ValidatableResponse create(Order order) {
    return (ValidatableResponse) reqSpec
            .body(order)
            .when()
            .post(ROOT)
            .then();
  }

  public int createOld(Order order) {
    return reqSpec
            .body(order)
            .when()
            .post(ROOT)
            .then().log().all()
            .assertThat()
            .statusCode(201)
            .extract()
            .path("track");
  }

  public int track(int trackId) {
    return reqSpec
            .pathParams("trackId", trackId)
            .when()
            .get(TRACK_ID)
            .then().log().all()
            .assertThat()
            .statusCode(200)
            .extract()
            .path("order.id");
  }

  public Response list() {
     return reqSpec
            .when()
            .get(ROOT)
            .then().contentType(ContentType.JSON)
             .extract()
             .response();
  }

  }
