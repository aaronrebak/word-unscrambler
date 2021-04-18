package aaronrebak.wordunscramble.api.data.splitter;

import static org.assertj.core.api.BDDAssertions.then;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringSplitterNoDelimiterUnitTest {

  private static final String CHAR_A = "a";
  private static final String CHAR_COMMA = ",";
  private static final String CHAR_B = "b";
  private static final String CHAR_PIPE = "|";
  private static final String CHAR_C = "c";

  private static final String STRING = Strings
      .concat(CHAR_A, CHAR_COMMA, CHAR_B, CHAR_PIPE, CHAR_C);

  private StringSplitter stringSplitter;

  @BeforeEach
  void beforeEach() {
    this.stringSplitter = new StringSplitterNoDelimiter();
  }

  @Test
  @DisplayName("Will split a string without a delimiter")
  void willSplitStringWithoutADelimiter() {
    then(this.stringSplitter.split(STRING))
        .containsExactlyInAnyOrder(
            CHAR_A,
            CHAR_COMMA,
            CHAR_B,
            CHAR_PIPE,
            CHAR_C
        );
  }

}