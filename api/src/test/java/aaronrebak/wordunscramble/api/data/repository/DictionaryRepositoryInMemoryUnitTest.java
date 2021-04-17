package aaronrebak.wordunscramble.api.data.repository;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DictionaryRepositoryInMemoryUnitTest {

  private static final String WORD_ONE = "ab";
  private static final String WORD_ONE_REVERSE = StringUtils.reverse(WORD_ONE);
  private static final String WORD_TWO = "cd";
  private static final String WORD_TWO_REVERSE = StringUtils.reverse(WORD_TWO);

  private static final List<String> DICTIONARY = List
      .of(WORD_ONE, WORD_ONE, WORD_ONE_REVERSE, WORD_TWO);

  private DictionaryRepository dictionaryRepository;

  @BeforeEach
  void beforeEach() {
    this.dictionaryRepository = new DictionaryRepositoryInMemory(DICTIONARY);
  }

  private static Stream<Arguments> willGetWordsByNaturalWord() {
    return Stream.of(
        Arguments.of(WORD_ONE, Set.of(WORD_ONE, WORD_ONE_REVERSE)),
        Arguments.of(WORD_ONE_REVERSE, Set.of()),
        Arguments.of(WORD_TWO, Set.of(WORD_TWO)),
        Arguments.of(WORD_TWO_REVERSE, Set.of())
    );
  }

  @DisplayName("Will get collection of words by natural word")
  @ParameterizedTest
  @MethodSource
  void willGetWordsByNaturalWord(final String input, final Collection<String> output) {
    then(this.dictionaryRepository.getWordsByNaturalWord(input))
        .containsExactlyInAnyOrderElementsOf(output);
  }

  private static Stream<Arguments> willCheckToSeeIfWordExists() {
    return Stream.of(
        Arguments.of(WORD_ONE, true),
        Arguments.of(WORD_ONE_REVERSE, false),
        Arguments.of(WORD_TWO, true),
        Arguments.of(WORD_TWO_REVERSE, false)
    );
  }

  @DisplayName("Will check to see if word exists")
  @ParameterizedTest
  @MethodSource
  void willCheckToSeeIfWordExists(final String input, final Boolean output) {
    then(this.dictionaryRepository.doesNaturalWordExist(input)).isEqualTo(output);
  }

}