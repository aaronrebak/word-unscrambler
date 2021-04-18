package aaronrebak.wordunscramble.api.data;

import aaronrebak.wordunscramble.api.model.domain.WordDomain;
import java.util.Collection;

public interface WordUnscrambleAccessor {

  Collection<WordDomain> createWords(WordDomain wordDomain);

}
