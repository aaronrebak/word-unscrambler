package aaronrebak.wordunscramble.api.transformer;

import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import org.springframework.stereotype.Component;

@Component
public class WordDomainTransformer {

  public WordDomain toWordDomain(
      final WordSquareRequest wordSquareRequest) {
    return WordDomain.builder()
        .characters(wordSquareRequest.getCharacters())
        .length(wordSquareRequest.getWordSquareSize())
        .build();
  }

  public WordDomain toWordDomain(final String characters) {
    return WordDomain.builder()
        .characters(characters)
        .build();
  }
}
