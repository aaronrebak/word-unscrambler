package aaronrebak.wordunscramble.api.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import aaronrebak.wordunscramble.api.data.WordUnscrambleAccessor;
import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.transformer.WordDomainTransformer;
import aaronrebak.wordunscramble.api.transformer.WordSquareResponseTransformer;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WordSquareServiceUnitTest {

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

  @Nested
  class CreateWordSquare {

    private final Integer wordSquareCount = 1;

    @Test
    @DisplayName("Will create WordSquareResponse")
    void wilLCreateWordSquareResponse(
        @Mock final WordSquareRequest wordSquareRequest,
        @Mock final WordDomain wordDomain,
        @Mock final Collection<WordDomain> wordDomainCollection,
        @Mock final WordSquareResponse wordSquareResponse) {

      given(wordDomainTransformer.toWordDomain(this.wordSquareCount, wordSquareRequest))
          .willReturn(wordDomain);
      given(wordUnscrambleAccessor.createWords(wordDomain)).willReturn(wordDomainCollection);
      given(wordSquareResponseTransformer.toWordSquareResponse(wordDomainCollection))
          .willReturn(wordSquareResponse);

      then(wordSquareService.createWordSquare(this.wordSquareCount, wordSquareRequest))
          .isEqualTo(wordSquareResponse);
    }

  }

}