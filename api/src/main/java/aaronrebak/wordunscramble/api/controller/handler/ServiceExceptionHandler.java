package aaronrebak.wordunscramble.api.controller.handler;

import aaronrebak.wordunscramble.api.controller.WordSquareController;
import aaronrebak.wordunscramble.api.exception.WordSquareServiceException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(
    assignableTypes = {WordSquareController.class}
)
public class ServiceExceptionHandler {

  private static final Map<String, String> ERROR_RESPONSE_MESSAGE = Map
      .of("response",
          "The server encountered an unexpected condition that prevented it from fulfilling the request");

  @ExceptionHandler(value = {WordSquareServiceException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  Map<String, String> handleServiceException(final Exception exception) {
    log.error("unexpected error", exception);
    return ERROR_RESPONSE_MESSAGE;
  }

}
