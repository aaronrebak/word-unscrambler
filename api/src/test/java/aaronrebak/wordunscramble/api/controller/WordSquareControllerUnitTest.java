package aaronrebak.wordunscramble.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;

import aaronrebak.wordunscramble.api.controller.handler.ConstraintViolationExceptionHandler;
import aaronrebak.wordunscramble.api.controller.handler.ServiceExceptionHandler;
import aaronrebak.wordunscramble.api.exception.WordSquareServiceException;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.service.WordSquareService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import java.util.Arrays;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WordSquareControllerUnitTest {

  private static final String WORD_SQUARE_PATH = "/wordsquare/{wordSquareLength}";

  private static WordSquareRequest aWordSquareRequest(final String characters) {
    return WordSquareRequest.builder()
        .characters(characters)
        .build();
  }

  private static WordSquareResponse aWordSquareResponse(final String... words) {
    return WordSquareResponse.builder()
        .words(Arrays.asList(words))
        .build();
  }

  @Mock
  private WordSquareService wordSquareService;

  @Spy
  @InjectMocks
  private WordSquareController wordSquareController;

  @BeforeEach
  void beforeEach() {
    RestAssuredMockMvc.standaloneSetup(
        this.wordSquareController,
        new ServiceExceptionHandler(),
        new ConstraintViolationExceptionHandler());
  }

  @Nested
  class CreateWordSquare {

    private final Integer wordSquareLength = 1;

    private MockMvcResponse givenPostRequestForCreatingWordSquare(
        final WordSquareRequest wordSquareRequest,
        final Integer wordSquareLength) {
      return given()
          .contentType(ContentType.JSON)
          .and()
          .body(wordSquareRequest)
          .when()
          .post(WORD_SQUARE_PATH, wordSquareLength);
    }

    @Test
    @DisplayName("The controller method returns a WordSquareResponse")
    void willReturnWordSquareResponse(
        @Mock final WordSquareRequest wordSquareRequest,
        @Mock final WordSquareResponse wordSquareResponse) throws Exception {
      BDDMockito.given(wordSquareService.createWordSquare(wordSquareLength, wordSquareRequest))
          .willReturn(wordSquareResponse);
      then(wordSquareController.createWordSquare(wordSquareLength, wordSquareRequest))
          .isEqualTo(wordSquareResponse);
    }

    @Nested
    @DisplayName("When a WordSquareResponse is returned by the service")
    class WordSquareReturned {

      private final WordSquareRequest wordSquareRequest = aWordSquareRequest("characters");
      private final WordSquareResponse serviceResult = aWordSquareResponse("wordOne", "wordTwo");

      private MockMvcResponse response;

      @BeforeEach
      void beforeEach() throws Exception {
        BDDMockito
            .given(wordSquareService.createWordSquare(wordSquareLength, wordSquareRequest))
            .willReturn(this.serviceResult);

        this.response = givenPostRequestForCreatingWordSquare(
            wordSquareRequest,
            wordSquareLength);
      }

      @Test
      @DisplayName("Will call the controller method")
      void willCallControllerMethod() throws Exception {
        BDDMockito.then(wordSquareService).should()
            .createWordSquare(wordSquareLength, wordSquareRequest);
      }

      @Test
      @DisplayName("Will return a HTTP Status code of 201 (Created)")
      void resultsIn200() {
        this.response.then().statusCode(HttpStatus.SC_CREATED);
      }

      @Test
      @DisplayName("Returns a WordSquareResponse in the response body")
      void returnsWordSquareInResponseBody() {
        then(this.response.then().extract().as(WordSquareResponse.class))
            .isEqualTo(this.serviceResult);
      }
    }

    @Nested
    @DisplayName("When an invalid request is made")
    class InvalidRequest {

      private final WordSquareRequest wordSquareRequest = aWordSquareRequest("!!invalid!!");

      private MockMvcResponse response;

      @BeforeEach
      void beforeEach() {
        this.response = givenPostRequestForCreatingWordSquare(
            wordSquareRequest,
            wordSquareLength);
      }

      @Test
      @DisplayName("Will not call the controller method")
      void willNotCallControllerMethod() throws Exception {
        BDDMockito.then(wordSquareService).should(never())
            .createWordSquare(anyInt(), any(WordSquareRequest.class));
      }

      @Test
      @DisplayName("Will return a HTTP Status code of 400 (Bad Request)")
      void resultsIn400() {
        this.response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
      }
    }

    @Nested
    @DisplayName("When a technical exception is thrown by the service")
    class TechnicalException {

      private final WordSquareRequest wordSquareRequest = aWordSquareRequest("characters");

      private MockMvcResponse response;

      @BeforeEach
      void beforeEach() throws Exception {
        BDDMockito
            .given(wordSquareService.createWordSquare(wordSquareLength, this.wordSquareRequest))
            .willThrow(WordSquareServiceException.class);

        this.response = givenPostRequestForCreatingWordSquare(this.wordSquareRequest,
            wordSquareLength);
      }

      @Test
      @DisplayName("Will call the controller method")
      void willCallControllerMethod() throws Exception {
        BDDMockito.then(wordSquareService).should()
            .createWordSquare(wordSquareLength, this.wordSquareRequest);
      }

      @Test
      @DisplayName("Will return a HTTP Status code of 500 (Internal Server Error)")
      void resultsIn500() {
        this.response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      }

      @Test
      @DisplayName("Returns a WordSquareResponse in the response body")
      void returnsErrorResponseBody() {
        this.response.then()
            .body("response",
                is("The server encountered an unexpected condition that prevented it from fulfilling the request"));
      }
    }
  }

}