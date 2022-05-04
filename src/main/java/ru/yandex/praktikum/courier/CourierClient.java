package ru.yandex.praktikum.courier;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

  private final String ROOT = "/courier";
  private final String LOGIN = ROOT + "/login";
  private final String COURIER = ROOT + "/{courierId}";


  public boolean create(Courier courier) {
    return reqSpec
            .body(courier)
            .when()
            .post(ROOT)
            .then().log().all()
            .assertThat()
            .statusCode(201)
            .extract()
            .path("ok");
  }

  public int login(CourierCredentials creds) {
    return reqSpec
            .body(creds)
            .when()
            .post(LOGIN)
            .then().log().all()
            .assertThat()
            .statusCode(200)
            .extract()
            .path("id");
  }

  public void delete(int couriedId) {
     reqSpec
             .pathParams("courierId", couriedId)
            .when()
            .delete(COURIER)
            .then().log().all()
            .assertThat()
            .statusCode(200);
  }
}
