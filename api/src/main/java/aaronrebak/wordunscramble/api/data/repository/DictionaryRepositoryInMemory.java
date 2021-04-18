package aaronrebak.wordunscramble.api.data.repository;

import aaronrebak.wordunscramble.api.classifier.GroupingWord;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DictionaryRepositoryInMemory implements DictionaryRepository {

  private final Map<String, Set<String>> inMemoryDictionary;

  public DictionaryRepositoryInMemory(final Collection<String> dictionary) {
    this.inMemoryDictionary = dictionary.stream()
        .collect(Collectors.groupingBy(GroupingWord::sortWord))
        .entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
  }

  @Override
  public Collection<String> getWordsByNaturalWord(String naturalWord) {
    return this.inMemoryDictionary.getOrDefault(naturalWord, Set.of());
  }

  @Override
  public Boolean doesNaturalWordExist(String naturalWord) {
    return this.inMemoryDictionary.containsKey(naturalWord);
  }
}
