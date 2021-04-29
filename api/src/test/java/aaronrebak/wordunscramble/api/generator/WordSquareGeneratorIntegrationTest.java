package aaronrebak.wordunscramble.api.generator;

import static org.assertj.core.api.BDDAssertions.then;

import aaronrebak.wordunscramble.api.model.domain.TreeDomain;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WordSquareGeneratorIntegrationTest {

  private static final String AB = "ab";
  private static final String BA = "ba";
  private static final String CD = "cd";
  private static final String DC = "dc";
  private static final List<String> WORDS = List.of(AB, BA, CD, DC);
  private static final TreeDomain TREE_DOMAIN = new TreeDomain(WORDS);

  private WordSquareGenerator wordSquareGenerator;

  @BeforeEach
  void beforeEach() {
    this.wordSquareGenerator = new WordSquareGenerator(TREE_DOMAIN);
  }

  private static Stream<Arguments> willGenerateACombinationOfWordSquares() {
    return Stream.of(
        Arguments.of(
            List.of(AB, BA, CD, DC), 2,
            List.of(
                List.of(AB, BA),
                List.of(BA, AB),
                List.of(CD, DC),
                List.of(DC, CD)
            )
        )
    );
  }

  @DisplayName("Will generate a combination of word squares")
  @ParameterizedTest
  @MethodSource
  void willGenerateACombinationOfWordSquares(
      final List<String> wordList,
      final Integer wordSquareSize,
      final List<List<String>> output) {
    then(this.wordSquareGenerator
        .generateWordSquare(wordList, wordSquareSize))
        .containsExactlyInAnyOrderElementsOf(output);
  }

}