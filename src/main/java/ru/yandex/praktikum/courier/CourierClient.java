package ru.yandex.praktikum.courier;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.RestAssuredClient;

public class CourierClient extends RestAssuredClient {

  private final String ROOT = "/courier";
  private final String LOGIN = ROOT + "/login";
  private final String COURIER = ROOT + "/{courierId}";

  public ValidatableResponse create(Courier courier) {
    return (ValidatableResponse) reqSpec
            .body(courier)
            .when()
            .post(ROOT)
            .then();
  }

  public ValidatableResponse login(CourierCredentials creds) {
    return (ValidatableResponse) reqSpec
            .body(creds)
            .when()
            .post(LOGIN)
            .then();
  }

  public void delete(int couriedId) {
     reqSpec
            .pathParams("courierId", couriedId)
            .when()
            .delete(COURIER)
            .then().log().body();
//            .assertThat()
//            .statusCode(200);
  }
}
