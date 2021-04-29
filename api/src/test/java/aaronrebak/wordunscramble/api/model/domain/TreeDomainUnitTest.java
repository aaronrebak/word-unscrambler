package aaronrebak.wordunscramble.api.model.domain;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TreeDomainUnitTest {

  private static final List<String> WORDS = List.of("ab", "ba");

  private TreeDomain treeDomain;

  @BeforeEach
  void beforeEach() {
    this.treeDomain = new TreeDomain(WORDS);
  }

  private static Stream<Arguments> willBuildSearchableTreeOfNodesIndexedByAlphabet() {
    return Stream.of(
        Arguments.of("a", List.of("ab")),
        Arguments.of("b", List.of("ba"))
    );
  }

  @DisplayName("Will build searchable tree of nodes indexed by alphabet")
  @ParameterizedTest
  @MethodSource
  void willBuildSearchableTreeOfNodesIndexedByAlphabet(
      final String prefix,
      final List<String> wordsThatStartWithPrefix) {
    then(this.treeDomain.findByPrefix(prefix)).containsAnyElementsOf(wordsThatStartWithPrefix);
  }

}