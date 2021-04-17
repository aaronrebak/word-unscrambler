package aaronrebak.wordunscramble.api.classifier;

import java.util.Arrays;

public class GroupingWord {

  public static String sortWord(final String input) {
    final char[] characters = input.toCharArray();
    Arrays.sort(characters);
    return new String(characters);
  }

}
