package aaronrebak.wordunscramble.api.systemtest.fourbyfour;

import static aaronrebak.wordunscramble.api.utility.JsonAssertionWrapper.thenJson;
import static aaronrebak.wordunscramble.api.utility.ResourceReader.readFileToString;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import aaronrebak.wordunscramble.api.systemtest.SystemTestPaths;
import aaronrebak.wordunscramble.api.systemtest.WordUnscrambleSystemTest;
import io.restassured.http.ContentType;
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
@DisplayName(
    "When a call to create a WordSquare is made - POST " + SystemTestPaths.WORDSQUARE_PATH)
class CreateWordSquare {

  private MockMvcResponse whenACallIsMadeToCreateAWordSquare(final String body) {
    return given()
        .contentType(ContentType.JSON)
        .body(body)
        .when()
        .post(SystemTestPaths.WORDSQUARE_PATH);
  }

  private MockMvcResponse response;

  @BeforeAll
  static void beforeAll(@Autowired final MockMvc mockMvc) {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @BeforeEach
  void beforeEach() {
    this.response = this.whenACallIsMadeToCreateAWordSquare(
        readFileToString("system.test.data/POST/wordsquare/4_by_4/request/request.body.json")
    );
  }

  @Test
  @DisplayName("Will return a HTTP status code of 201 (Created)")
  void createWordSquareStatusCode() {
    this.response.then().statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  @DisplayName("Will return a response body containing created word square")
  void createWordSquareBody() {
    final String responseJson = this.response.then().extract().asString();
    thenJson(responseJson)
        .allowingAnyArrayOrdering()
        .isSameJsonAs(
            readFileToString(
                "system.test.data/POST/wordsquare/4_by_4/response/response.body.json")
        );
  }

}