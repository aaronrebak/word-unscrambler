package aaronrebak.wordunscramble.api.transformer;

import static org.assertj.core.api.BDDAssertions.then;

import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WordDomainTransformerUnitTest {

  private static final Integer CHARACTER_LENGTH = 1;
  private static final String CHARACTERS = "characters";
  private static final WordSquareRequest WORD_SQUARE_REQUEST = WordSquareRequest.builder()
      .characters(CHARACTERS).build();

  private WordDomainTransformer wordDomainTransformer;

  @BeforeEach
  void beforeEach() {
    this.wordDomainTransformer = new WordDomainTransformer();
  }

  @Test
  @DisplayName("Will transform wordSquareCount, characters into wordDomain")
  void willTransformWordSquareCount_CharactersIntoWordDomain() {
    then(this.wordDomainTransformer.toWordDomain(CHARACTER_LENGTH, WORD_SQUARE_REQUEST))
        .isEqualTo(WordDomain.builder()
            .characterLength(CHARACTER_LENGTH)
            .characters(CHARACTERS)
            .build()
        );
  }

  @Test
  @DisplayName("Will transform characters into wordDomain")
  void willTransformCharactersIntoWordDomain() {
    then(this.wordDomainTransformer.toWordDomain(CHARACTERS))
        .isEqualTo(WordDomain.builder()
            .characters(CHARACTERS)
            .build()
        );
  }

}