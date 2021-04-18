package aaronrebak.wordunscramble.api.classifier;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GroupingWordUnitTest {

  private static final String UNSORTED_STRING = "cab";
  private static final String SORTED_STRING = "abc";

  @Test
  @DisplayName("Will sort string in natural order")
  void willSortStringInNaturalOrder() {
    then(GroupingWord.sortWord(UNSORTED_STRING)).isEqualTo(SORTED_STRING);
  }

}