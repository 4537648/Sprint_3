package ru.yandex.praktikum.courier;

import io.restassured.response.ValidatableResponse;

public class CourierClient extends RestAssuredClient {

  private final String ROOT = "/courier";
  private final String LOGIN = ROOT + "/login";
  private final String COURIER = ROOT + "/{courierId}";

  public ValidatableResponse create(Courier courier) {
    return (ValidatableResponse) reqSpec
            .body(courier)
            .when()
            .post(ROOT)
            .then().log().all();
  }

  public boolean createOld(Courier courier) {
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

  public String createDuplicate(Courier courier) {
    return reqSpec
            .body(courier)
            .when()
            .post(ROOT)
            .then().log().all()
            .assertThat()
            .statusCode(409)
            .extract()
            .path("message");
  }

  public String createWithoutFullCreds(Courier courier) {
    return reqSpec
            .body(courier)
            .when()
            .post(ROOT)
            .then().log().all()
            .assertThat()
            .statusCode(400)
            .extract()
            .path("message");
  }

  public String loginWithoutFullCreds(CourierCredentials creds) {
    return reqSpec
            .body(creds)
            .when()
            .post(LOGIN)
            .then().log().all()
            .assertThat()
            .statusCode(400)
            .extract()
            .path("message");
  }

  public String loginWithWrongCreds(CourierCredentials creds) {
    return reqSpec
            .body(creds)
            .when()
            .post(LOGIN)
            .then().log().all()
            .assertThat()
            .statusCode(404)
            .extract()
            .path("message");
  }
}
