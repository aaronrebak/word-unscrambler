package aaronrebak.wordunscramble.api.model.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WordDomain {

  String characters;
  Integer length;

}
