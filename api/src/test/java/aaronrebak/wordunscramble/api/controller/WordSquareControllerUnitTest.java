package aaronrebak.wordunscramble.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.BDDAssertions.then;

import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.service.WordSquareService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
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

  private static final Integer WORD_SQUARE_COUNT = 1;
  private static final String WORD_SQUARE_PATH = "/wordsquare/{wordSquareCount}";

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

    private MockMvcResponse whenACallIsMadeToCreateAWordSquare(
        final WordSquareRequest wordSquareRequest) {
      return given().body(wordSquareRequest).when().post(WORD_SQUARE_PATH, WORD_SQUARE_COUNT);
    }

    @Test
    @DisplayName("ControllerMethod will return WordSquareResponse")
    void controllerMethodWillReturnWordSquareResponse(
        @Mock final WordSquareRequest wordSquareRequest,
        @Mock final WordSquareResponse wordSquareResponse) {
      BDDMockito.given(wordSquareService.createWordSquare(WORD_SQUARE_COUNT, wordSquareRequest))
          .willReturn(wordSquareResponse);
      then(wordSquareController.createWordSquare(WORD_SQUARE_COUNT, wordSquareRequest))
          .isEqualTo(wordSquareResponse);
    }
  }

}