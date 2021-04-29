package aaronrebak.wordunscramble.api.model.request.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
    ElementType.METHOD,
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.PARAMETER,
    ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RequestSquaredLengthCheckValidator.class)
public @interface SquaredLengthCheck {

  String message() default "wordSquareSize(squared) must be equal to character length";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
