package aaronrebak.wordunscramble.api.generator;

import java.util.Set;
import java.util.stream.Collectors;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Component;

@Component
public class CombinationGenerator {

  public Set<String> generateCombinations(
      final String[] letters,
      final Integer count,
      final String delimiter) {
    return Generator
        .combination(letters)
        .simple(count)
        .stream()
        .map(strings -> strings.stream().sorted().collect(Collectors.toList()))
        .map(strings -> String.join(delimiter, strings))
        .collect(Collectors.toSet());
  }
}
