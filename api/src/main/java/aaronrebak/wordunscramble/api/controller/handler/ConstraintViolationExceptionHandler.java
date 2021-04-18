package aaronrebak.wordunscramble.api.controller.handler;

import aaronrebak.wordunscramble.api.controller.WordSquareController;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestControllerAdvice(
    assignableTypes = {WordSquareController.class}
)
public class ConstraintViolationExceptionHandler {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  ModelAndView handleConstraintViolationException(
      final HttpServletResponse response) throws IOException {
    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    return new ModelAndView();
  }

}
