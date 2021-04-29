package aaronrebak.wordunscramble.api.data;

import aaronrebak.wordunscramble.api.classifier.GroupingWord;
import aaronrebak.wordunscramble.api.data.repository.DictionaryRepository;
import aaronrebak.wordunscramble.api.data.splitter.StringSplitter;
import aaronrebak.wordunscramble.api.factory.WordSquareGeneratorSingletonFactory;
import aaronrebak.wordunscramble.api.generator.CombinationGenerator;
import aaronrebak.wordunscramble.api.generator.WordSquareGenerator;
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
  private final WordSquareGeneratorSingletonFactory wordSquareGeneratorSingletonFactory;
  private final WordDomainTransformer wordDomainTransformer;

  public WordUnscrambleAccessorImpl(
      final StringSplitter stringSplitter,
      final CombinationGenerator combinationGenerator,
      final DictionaryRepository dictionaryRepository,
      final WordSquareGeneratorSingletonFactory wordSquareGeneratorSingletonFactory,
      final WordDomainTransformer wordDomainTransformer) {
    this.stringSplitter = stringSplitter;
    this.combinationGenerator = combinationGenerator;
    this.dictionaryRepository = dictionaryRepository;
    this.wordSquareGeneratorSingletonFactory = wordSquareGeneratorSingletonFactory;
    this.wordDomainTransformer = wordDomainTransformer;
  }

  @Override
  public Collection<WordDomain> createWords(final WordDomain wordDomain) {
    final List<String> individualLetters = this.stringSplitter
        .split(GroupingWord.sortWord(wordDomain.getCharacters()));
    final Integer wordLength = wordDomain.getLength();

    final Set<String> generatedLetterCombinations = this.combinationGenerator
        .generateLetterCombinations(individualLetters, wordLength);

    final Set<String> validWords = generatedLetterCombinations.stream()
        .filter(this.dictionaryRepository::doesNaturalWordExist)
        .map(this.dictionaryRepository::getWordsByNaturalWord)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());

    final WordSquareGenerator wordSquareGenerator = this.wordSquareGeneratorSingletonFactory
        .getWordSquareGenerator(validWords);

    final List<String> wordSquare = wordSquareGenerator.generateWordSquare(validWords, wordLength)
        .stream()
        .filter(list -> {
          final String characters = String.join("", list);
          final List<String> reducedSortedWordSquare = this.stringSplitter
              .split(GroupingWord.sortWord(characters));
          return reducedSortedWordSquare.equals(individualLetters);
        })
        .findFirst()
        .orElseThrow();

    return wordSquare.stream()
        .map(this.wordDomainTransformer::toWordDomain)
        .collect(Collectors.toList());
  }
}
