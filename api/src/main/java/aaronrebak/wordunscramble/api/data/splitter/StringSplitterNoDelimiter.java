package aaronrebak.wordunscramble.api.data.splitter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StringSplitterNoDelimiter implements StringSplitter {

  private static final String DELIMITER = "";

  @Override
  public List<String> split(final String string) {
    return Arrays.stream(string.split(DELIMITER)).collect(Collectors.toList());
  }
}
