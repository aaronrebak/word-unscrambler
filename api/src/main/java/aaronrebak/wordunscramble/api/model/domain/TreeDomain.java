package aaronrebak.wordunscramble.api.model.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Value;

@Value
public class TreeDomain {

  private static final char CHAR_A = 'a';

  Node rootNode;

  public TreeDomain(final Collection<String> words) {
    this.rootNode = new Node();
    for (final String word : words) {
      Node node = rootNode;
      for (char character : word.toCharArray()) {
        int index = Math.subtractExact(character, CHAR_A);
        if (node.getChild()[index] == null) {
          node.getChild()[index] = new Node();
        }
        node.getChild()[index].getStartWith().add(word);
        node = node.getChild()[index];
      }
    }
  }

  public List<String> findByPrefix(final String prefix) {
    final List<String> candidates = new ArrayList<>();
    if (prefix == null || prefix.length() == 0) {
      return candidates;
    }
    Node node = rootNode;
    for (char character : prefix.toCharArray()) {
      int index = Math.subtractExact(character, CHAR_A);
      if (node.getChild()[index] == null) {
        return candidates;
      }
      node = node.getChild()[index];
    }
    candidates.addAll(node.getStartWith());
    return node.getStartWith();
  }

  @Value
  public static class Node {

    Node[] child = new Node[26];
    List<String> startWith = new ArrayList<>();
  }
}
