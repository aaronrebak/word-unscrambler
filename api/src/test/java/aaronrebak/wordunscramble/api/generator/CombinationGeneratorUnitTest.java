package aaronrebak.wordunscramble.api.generator;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CombinationGeneratorUnitTest {

  private static final String EMPTY_DELIMITER = "";
  private static final String NON_EMPTY_DELIMITER = "|";

  private CombinationGenerator combinationGenerator;

  @BeforeEach
  void beforeEach() {
    this.combinationGenerator = new CombinationGenerator();
  }

  private static Stream<Arguments> willGenerateACombinationOfStrings() {
    return Stream.of(
        Arguments.of(List.of("a", "b", "c"), 1, EMPTY_DELIMITER, Set.of("a", "b", "c")),
        Arguments.of(List.of("a", "b", "c"), 2, EMPTY_DELIMITER,
            Set.of(
                String.format("a%sb", EMPTY_DELIMITER),
                String.format("a%sc", EMPTY_DELIMITER),
                String.format("b%sc", EMPTY_DELIMITER))
        ),
        Arguments.of(List.of("a", "b", "c"), 2, NON_EMPTY_DELIMITER,
            Set.of(
                String.format("a%sb", NON_EMPTY_DELIMITER),
                String.format("a%sc", NON_EMPTY_DELIMITER),
                String.format("b%sc", NON_EMPTY_DELIMITER))
        )
    );
  }

  @DisplayName("Will generate a combination of strings")
  @ParameterizedTest
  @MethodSource
  void willGenerateACombinationOfStrings(
      final List<String> letterList,
      final Integer combinationCount,
      final String delimiter,
      final Set<String> output) {
    then(this.combinationGenerator
        .generateCombinations(letterList.toArray(String[]::new), combinationCount, delimiter))
        .containsExactlyInAnyOrderElementsOf(output);
  }


}