package aaronrebak.wordunscramble.api.model.response;

import java.util.Collection;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WordSquareResponse {

  Collection<String> words;
}
