package aaronrebak.wordunscramble.api.service;

import aaronrebak.wordunscramble.api.data.WordUnscrambleAccessor;
import aaronrebak.wordunscramble.api.exception.WordSquareServiceException;
import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.transformer.WordDomainTransformer;
import aaronrebak.wordunscramble.api.transformer.WordSquareResponseTransformer;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class WordSquareService {

  private final WordDomainTransformer wordDomainTransformer;
  private final WordUnscrambleAccessor wordUnscrambleAccessor;
  private final WordSquareResponseTransformer wordSquareResponseTransformer;

  public WordSquareService(
      final WordDomainTransformer wordDomainTransformer,
      final WordUnscrambleAccessor wordUnscrambleAccessor,
      final WordSquareResponseTransformer wordSquareResponseTransformer) {
    this.wordDomainTransformer = wordDomainTransformer;
    this.wordUnscrambleAccessor = wordUnscrambleAccessor;
    this.wordSquareResponseTransformer = wordSquareResponseTransformer;
  }

  public WordSquareResponse createWordSquare(
      final WordSquareRequest wordSquareRequest) throws WordSquareServiceException {
    try {
      final WordDomain wordDomain = this.wordDomainTransformer
          .toWordDomain(wordSquareRequest);

      final Collection<WordDomain> wordDomains = this.wordUnscrambleAccessor.createWords(wordDomain);

      return this.wordSquareResponseTransformer.toWordSquareResponse(wordDomains);
    } catch (final Exception exception) {
      throw new WordSquareServiceException(exception);
    }
  }
}
