package aaronrebak.wordunscramble.api.factory;

import static org.assertj.core.api.BDDAssertions.then;

import aaronrebak.wordunscramble.api.generator.WordSquareGenerator;
import aaronrebak.wordunscramble.api.model.domain.TreeDomain;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WordSquareGeneratorSingletonFactoryUnitTest {

  private static final Collection<String> STRING_COLLECTION = List.of("string");

  private WordSquareGeneratorSingletonFactory singletonFactory;

  @BeforeEach
  void beforeEach() {
    this.singletonFactory = new WordSquareGeneratorSingletonFactory();
  }

  @Test
  @DisplayName("Will construct a wordSquareGenerator")
  void willConstructWordSquareGenerator() {
    then(this.singletonFactory.getWordSquareGenerator(STRING_COLLECTION))
        .isEqualTo(new WordSquareGenerator(new TreeDomain(STRING_COLLECTION)));
  }

}