package aaronrebak.wordunscramble.api.data;

import aaronrebak.wordunscramble.api.data.repository.DictionaryRepository;
import aaronrebak.wordunscramble.api.data.splitter.StringSplitter;
import aaronrebak.wordunscramble.api.generator.CombinationGenerator;
import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.transformer.WordDomainTransformer;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class WordUnscrambleAccessorImpl implements WordUnscrambleAccessor {

  private final StringSplitter stringSplitter;
  private final CombinationGenerator combinationGenerator;
  private final DictionaryRepository dictionaryRepository;
  private final WordDomainTransformer wordDomainTransformer;

  public WordUnscrambleAccessorImpl(
      final StringSplitter stringSplitter,
      final CombinationGenerator combinationGenerator,
      final DictionaryRepository dictionaryRepository,
      final WordDomainTransformer wordDomainTransformer) {
    this.stringSplitter = stringSplitter;
    this.combinationGenerator = combinationGenerator;
    this.dictionaryRepository = dictionaryRepository;
    this.wordDomainTransformer = wordDomainTransformer;
  }

  @Override
  public Collection<WordDomain> createWords(final WordDomain wordDomain) {
    final List<String> individualLetters = this.stringSplitter
        .split(wordDomain.getCharacters());
    final Integer wordLength = wordDomain.getLength();

    final Set<String> generatedCombinations = this.combinationGenerator
        .generateCombinations(individualLetters, wordLength);

    return generatedCombinations.stream()
        .filter(this.dictionaryRepository::doesNaturalWordExist)
        .limit(wordLength)
        .map(this.dictionaryRepository::getWordsByNaturalWord)
        .flatMap(Collection::stream)
        .limit(wordLength)
        .map(this.wordDomainTransformer::toWordDomain)
        .collect(Collectors.toSet());
  }
}
