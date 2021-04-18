package aaronrebak.wordunscramble.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.BDDAssertions.then;

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

  private static final Integer WORD_SQUARE_LENGTH = 1;
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
    RestAssuredMockMvc.standaloneSetup(this.wordSquareController);
  }

  @Nested
  class CreateWordSquare {

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
        @Mock final WordSquareResponse wordSquareResponse) {
      BDDMockito.given(wordSquareService.createWordSquare(WORD_SQUARE_LENGTH, wordSquareRequest))
          .willReturn(wordSquareResponse);
      then(wordSquareController.createWordSquare(WORD_SQUARE_LENGTH, wordSquareRequest))
          .isEqualTo(wordSquareResponse);
    }

    @Nested
    @DisplayName("When a WordSquareResponse is returned by the service")
    class WordSquareResponseReturned {

      private final WordSquareRequest wordSquareRequest = aWordSquareRequest("characters");
      private final WordSquareResponse serviceResult = aWordSquareResponse("wordOne", "wordTwo");

      private MockMvcResponse response;

      @BeforeEach
      void beforeEach() {
        BDDMockito
            .given(wordSquareService.createWordSquare(WORD_SQUARE_LENGTH, this.wordSquareRequest))
            .willReturn(this.serviceResult);

        this.response = givenPostRequestForCreatingWordSquare(
            this.wordSquareRequest,
            WORD_SQUARE_LENGTH);
      }

      @Test
      @DisplayName("Will call the controller method")
      void willCallControllerMethod() {
        BDDMockito.then(wordSquareService).should()
            .createWordSquare(WORD_SQUARE_LENGTH, this.wordSquareRequest);
      }

      @Test
      @DisplayName("Will return a HTTP Status code of 201 (CREATED)")
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
  }

}