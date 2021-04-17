package aaronrebak.wordunscramble.api.systemtest.health;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

import aaronrebak.wordunscramble.api.systemtest.SystemTestPaths;
import aaronrebak.wordunscramble.api.systemtest.WordUnscrambleSystemTest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@WordUnscrambleSystemTest
@DisplayName("When a call to health check is made - GET " + SystemTestPaths.HEALTH_CHECK_PATH)
public class HealthCheck {

  private MockMvcResponse response;

  @BeforeAll
  static void beforeAll(@Autowired final MockMvc mockMvc) {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @BeforeEach
  void beforeEach() {
    this.response = given().when().get(SystemTestPaths.HEALTH_CHECK_PATH);
  }

  @Test
  @DisplayName("Will return a HTTP status code of 200 (OK)")
  void willReturnStatusOk() {
    this.response.then().statusCode(HttpStatus.SC_OK);
  }

  @Test
  @DisplayName("Will return a response body with a status of UP")
  void willReturnResponseBodyWithStatusUp() {
    this.response.then().body("status", equalTo("UP"));
  }
}
