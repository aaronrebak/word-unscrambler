package aaronrebak.wordunscramble.api.data.repository;

import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository {

  Collection<String> getWordsByNaturalWord(String naturalWord);

  Boolean doesNaturalWordExist(String naturalWord);
}
