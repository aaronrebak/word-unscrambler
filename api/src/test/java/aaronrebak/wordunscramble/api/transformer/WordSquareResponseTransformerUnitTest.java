package aaronrebak.wordunscramble.api.transformer;

import static org.assertj.core.api.BDDAssertions.then;

import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WordSquareResponseTransformerUnitTest {

  private static final String CHARACTERS_ONE = "charactersOne";
  private static final String CHARACTERS_TWO = "charactersTwo";

  private WordSquareResponseTransformer wordSquareResponseTransformer;

  private static WordDomain aWordDomain(final String characters) {
    return WordDomain.builder().characters(characters).build();
  }

  private static WordSquareResponse aWordSquareResponse(final String... characters) {
    return WordSquareResponse.builder().words(List.of(characters)).build();
  }

  @BeforeEach
  void beforeEach() {
    this.wordSquareResponseTransformer = new WordSquareResponseTransformer();
  }

  @Test
  @DisplayName("Will transform collection of wordDomains to wordSquareResponse")
  void willTransformCollectionOfWordSquareDomainsToWordSquareResponse() {
    final Collection<WordDomain> collectionOfWordDomains = List
        .of(aWordDomain(CHARACTERS_ONE), aWordDomain(CHARACTERS_TWO));

    then(this.wordSquareResponseTransformer.toWordSquareResponse(collectionOfWordDomains))
        .isEqualTo(aWordSquareResponse(CHARACTERS_ONE, CHARACTERS_TWO));
  }

}