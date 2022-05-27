package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class Config {

  public static final boolean LOG_STATUS = true;

  public void turnOnLogging() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }
}