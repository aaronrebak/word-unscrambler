package aaronrebak.wordunscramble.api.data;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import aaronrebak.wordunscramble.api.data.repository.DictionaryRepository;
import aaronrebak.wordunscramble.api.data.splitter.StringSplitter;
import aaronrebak.wordunscramble.api.factory.WordSquareGeneratorSingletonFactory;
import aaronrebak.wordunscramble.api.generator.CombinationGenerator;
import aaronrebak.wordunscramble.api.generator.WordSquareGenerator;
import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import aaronrebak.wordunscramble.api.transformer.WordDomainTransformer;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.api.IterableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WordUnscrambleAccessorImplUnitTest {

  private static final String CHARACTERS = "abba";
  private static final String SORTED_CHARACTERS = "aabb";
  private static final Integer CHARACTER_LENGTH = 2;
  private static final List<String> INDIVIDUAL_LETTERS = List
      .of("a", "a", "b", "b");

  private static final String GENERATED_COMBINATION_ONE = "generatedCombinationOne";
  private static final String GENERATED_COMBINATION_TWO = "generatedCombinationTwo";

  private static final String WORD_ONE = "ab";
  private static final String WORD_TWO = "ba";

  private static final WordDomain WORD_DOMAIN_ONE = mock(WordDomain.class);
  private static final WordDomain WORD_DOMAIN_TWO = mock(WordDomain.class);

  private static WordDomain aWordDomain(final String characters, final Integer characterLength) {
    return WordDomain.builder()
        .characters(characters)
        .length(characterLength)
        .build();
  }

  @Mock
  private StringSplitter stringSplitter;
  @Mock
  private CombinationGenerator combinationGenerator;
  @Mock
  private DictionaryRepository dictionaryRepository;
  @Mock
  private WordDomainTransformer wordDomainTransformer;
  @Mock
  private WordSquareGeneratorSingletonFactory wordSquareGeneratorSingletonFactory;
  @Mock
  private WordSquareGenerator wordSquareGenerator;

  private WordUnscrambleAccessor wordUnscrambleAccessor;

  @BeforeEach
  void beforeEach() {
    this.wordUnscrambleAccessor = new WordUnscrambleAccessorImpl(
        this.stringSplitter,
        this.combinationGenerator,
        this.dictionaryRepository,
        this.wordSquareGeneratorSingletonFactory,
        this.wordDomainTransformer
    );
  }

  @Nested
  class CreateWordDomains {

    private IterableAssert<WordDomain> givenAWordDomainIsProcessed() {
      given(stringSplitter.split(SORTED_CHARACTERS)).willReturn(INDIVIDUAL_LETTERS);
      given(combinationGenerator
          .generateLetterCombinations(INDIVIDUAL_LETTERS, CHARACTER_LENGTH))
          .willReturn(Set.of(GENERATED_COMBINATION_ONE, GENERATED_COMBINATION_TWO));
      given(dictionaryRepository.doesNaturalWordExist(GENERATED_COMBINATION_ONE))
          .willReturn(true);
      given(dictionaryRepository.doesNaturalWordExist(GENERATED_COMBINATION_TWO))
          .willReturn(true);
      given(dictionaryRepository.getWordsByNaturalWord(GENERATED_COMBINATION_ONE))
          .willReturn(List.of(WORD_ONE));
      given(dictionaryRepository.getWordsByNaturalWord(GENERATED_COMBINATION_TWO))
          .willReturn(List.of(WORD_TWO));
      given(wordSquareGeneratorSingletonFactory.getWordSquareGenerator(Set.of(WORD_ONE, WORD_TWO)))
          .willReturn(wordSquareGenerator);
      given(wordSquareGenerator.generateWordSquare(Set.of(WORD_ONE, WORD_TWO), CHARACTER_LENGTH))
          .willReturn(List.of(List.of(WORD_ONE, WORD_TWO)));
      given(stringSplitter.split(SORTED_CHARACTERS)).willReturn(INDIVIDUAL_LETTERS);
      given(wordDomainTransformer.toWordDomain(WORD_ONE)).willReturn(WORD_DOMAIN_ONE);
      given(wordDomainTransformer.toWordDomain(WORD_TWO)).willReturn(WORD_DOMAIN_TWO);

      return BDDAssertions
          .then(wordUnscrambleAccessor.createWords(aWordDomain(CHARACTERS, CHARACTER_LENGTH)));
    }

    private IterableAssert<WordDomain> thenCreateWordDomainsResult;

    @BeforeEach
    void beforeEach() {
      this.thenCreateWordDomainsResult = this.givenAWordDomainIsProcessed();
    }

    @Test
    @DisplayName("WordSquareDomainTransformer interaction is limited by character length")
    void wordSquareDomainTransformerInteractionIsLimitedByCharacterLength() {
      then(wordDomainTransformer).should(times(CHARACTER_LENGTH))
          .toWordDomain(anyString());
    }

    @Test
    @DisplayName("Will return a collection specified by character length")
    void willReturnCollectionWithSizeOfCharacterLength() {
      this.thenCreateWordDomainsResult.hasSize(CHARACTER_LENGTH);
    }

    @Test
    @DisplayName("Will return a collection of word domains")
    void willReturnCollectionOfWordDomains() {
      this.thenCreateWordDomainsResult.containsExactlyInAnyOrder(WORD_DOMAIN_ONE, WORD_DOMAIN_TWO);
    }
  }

}