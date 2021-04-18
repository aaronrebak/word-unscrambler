package aaronrebak.wordunscramble.api.generator;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Component;

@Component
public class CombinationGenerator {

  public Set<String> generateCombinations(
      final Collection<String> letters,
      final Integer count) {
    return Generator
        .combination(letters)
        .simple(count)
        .stream()
        .map(strings -> strings.stream().sorted().collect(Collectors.joining()))
        .collect(Collectors.toSet());
  }
}
