package aaronrebak.wordunscramble.api.transformer;

import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class WordSquareResponseTransformer {

  public WordSquareResponse toWordSquareResponse(
      final Collection<WordDomain> wordDomains) {
    return WordSquareResponse.builder()
        .words(wordDomains.stream()
            .map(WordDomain::getCharacters)
            .collect(Collectors.toSet()))
        .build();
  }

}
