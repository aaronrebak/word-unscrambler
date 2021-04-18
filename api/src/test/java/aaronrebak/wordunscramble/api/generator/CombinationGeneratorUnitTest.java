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

  private CombinationGenerator combinationGenerator;

  @BeforeEach
  void beforeEach() {
    this.combinationGenerator = new CombinationGenerator();
  }

  private static Stream<Arguments> willGenerateACombinationOfStrings() {
    return Stream.of(
        Arguments.of(List.of("a", "b", "c"), 1, Set.of("a", "b", "c")),
        Arguments.of(List.of("a", "b", "c"), 2, Set.of("ab", "ac", "bc")),
        Arguments.of(List.of("a", "b", "c"), 3, Set.of("abc"))
    );
  }

  @DisplayName("Will generate a combination of strings")
  @ParameterizedTest
  @MethodSource
  void willGenerateACombinationOfStrings(
      final List<String> letterList,
      final Integer combinationCount,
      final Set<String> output) {
    then(this.combinationGenerator
        .generateCombinations(letterList.toArray(String[]::new), combinationCount))
        .containsExactlyInAnyOrderElementsOf(output);
  }


}