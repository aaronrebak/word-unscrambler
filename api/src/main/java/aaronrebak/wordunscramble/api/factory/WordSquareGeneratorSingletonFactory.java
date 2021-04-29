package aaronrebak.wordunscramble.api.factory;

import aaronrebak.wordunscramble.api.generator.WordSquareGenerator;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class WordSquareGeneratorSingletonFactory {

  public WordSquareGenerator getWordSquareGenerator(final Collection<String> words) {
    return WordSquareGenerator.of(words);
  }

}
