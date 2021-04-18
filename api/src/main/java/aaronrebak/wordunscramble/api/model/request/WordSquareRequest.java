package aaronrebak.wordunscramble.api.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WordSquareRequest {

  @NotEmpty
  @Pattern(regexp = "^[a-z]+$")
  String characters;
}
