package aaronrebak.wordunscramble.api.generator;

import aaronrebak.wordunscramble.api.model.domain.TreeDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class WordSquareGenerator {

  public static WordSquareGenerator of(final Collection<String> words) {
    return new WordSquareGenerator(new TreeDomain(words));
  }

  private final TreeDomain treeDomain;

  public WordSquareGenerator(final TreeDomain treeDomain) {
    this.treeDomain = treeDomain;
  }

  public List<List<String>> generateWordSquare(
      final Collection<String> words,
      final Integer count) {
    final List<List<String>> validWordSquares = new ArrayList<>();
    for (final String word : words) {
      final List<String> selectedWords = new ArrayList<>();
      selectedWords.add(word);
      this.depthFirstSearch(validWordSquares, selectedWords, count);
    }
    return validWordSquares;
  }

  private void depthFirstSearch(
      final List<List<String>> validWordSquares,
      final List<String> selected,
      final Integer count) {
    if (selected.size() == count) {
      validWordSquares.add(new ArrayList<>(selected));
      return;
    }

    int prefixIndex = selected.size();
    final StringBuilder stringBuilder = new StringBuilder();
    for (final String word : selected) {
      stringBuilder.append(word.charAt(prefixIndex));
    }
    final List<String> newCandidates = this.treeDomain.findByPrefix(stringBuilder.toString());

    for (final String candidate : newCandidates) {
      selected.add(candidate);
      this.depthFirstSearch(validWordSquares, selected, count);
      selected.remove(selected.size() - 1);
    }
  }
}
