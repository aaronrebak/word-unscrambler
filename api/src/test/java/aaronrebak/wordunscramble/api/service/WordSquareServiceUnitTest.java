package aaronrebak.wordunscramble.api.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;
import static org.mockito.BDDMockito.given;

import aaronrebak.wordunscramble.api.data.WordUnscrambleAccessor;
import aaronrebak.wordunscramble.api.exception.WordSquareServiceException;
import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.transformer.WordDomainTransformer;
import aaronrebak.wordunscramble.api.transformer.WordSquareResponseTransformer;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WordSquareServiceUnitTest {

  private static Arguments exceptionPropagatesWordSquareServiceException() {
    return Arguments.of(RuntimeException.class, WordSquareServiceException.class);
  }

  @Mock
  private WordDomainTransformer wordDomainTransformer;
  @Mock
  private WordUnscrambleAccessor wordUnscrambleAccessor;
  @Mock
  private WordSquareResponseTransformer wordSquareResponseTransformer;

  private WordSquareService wordSquareService;

  @BeforeEach
  void beforeEach() {
    this.wordSquareService = new WordSquareService(
        this.wordDomainTransformer,
        this.wordUnscrambleAccessor,
        this.wordSquareResponseTransformer
    );
  }

  @TestInstance(Lifecycle.PER_CLASS)
  @Nested
  class CreateWordSquare {

    @Test
    @DisplayName("Will create WordSquareResponse")
    void wilLCreateWordSquareResponse(
        @Mock final WordSquareRequest wordSquareRequest,
        @Mock final WordDomain wordDomain,
        @Mock final Collection<WordDomain> wordDomainCollection,
        @Mock final WordSquareResponse wordSquareResponse) throws Exception {

      given(wordDomainTransformer.toWordDomain(wordSquareRequest))
          .willReturn(wordDomain);
      given(wordUnscrambleAccessor.createWords(wordDomain)).willReturn(wordDomainCollection);
      given(wordSquareResponseTransformer.toWordSquareResponse(wordDomainCollection))
          .willReturn(wordSquareResponse);

      then(wordSquareService.createWordSquare(wordSquareRequest))
          .isEqualTo(wordSquareResponse);
    }

    private Stream<Arguments> willHandleExceptionsAppropriately() {
      return Stream.of(exceptionPropagatesWordSquareServiceException());
    }

    @DisplayName("Will handle all exceptions appropriately")
    @ParameterizedTest
    @MethodSource
    void willHandleExceptionsAppropriately(
        final Class<? extends Exception> inputException,
        final Class<? extends Exception> outputException,
        @Mock final WordSquareRequest wordSquareRequest) {
      given(wordDomainTransformer.toWordDomain(wordSquareRequest))
          .willThrow(inputException);

      thenExceptionOfType(outputException).isThrownBy(
          () -> wordSquareService.createWordSquare(wordSquareRequest));
    }

  }

}