package aaronrebak.wordunscramble.api.model.request;

import aaronrebak.wordunscramble.api.model.request.validation.SquaredLengthCheck;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@SquaredLengthCheck
public class WordSquareRequest {

  @Min(1)
  Integer wordSquareSize;
  @NotEmpty
  @Pattern(regexp = "^[a-z]+$")
  String characters;
}
