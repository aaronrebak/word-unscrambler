package aaronrebak.wordunscramble.api.model.request.validation;

import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequestSquaredLengthCheckValidator implements
    ConstraintValidator<SquaredLengthCheck, WordSquareRequest> {

  @Override
  public boolean isValid(
      final WordSquareRequest value,
      final ConstraintValidatorContext context) {

    if (value == null) {
      return false;
    }

    final String characters = value.getCharacters();
    final Integer wordSquareSize = value.getWordSquareSize();

    if (value.getCharacters() == null || value.getWordSquareSize() == null) {
      return false;
    }

    return Math.multiplyExact(wordSquareSize, wordSquareSize) == characters.length();
  }
}
