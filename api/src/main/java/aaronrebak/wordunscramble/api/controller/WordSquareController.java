package aaronrebak.wordunscramble.api.controller;

import aaronrebak.wordunscramble.api.exception.WordSquareServiceException;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.service.WordSquareService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wordsquare", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class WordSquareController {

  private final WordSquareService wordSquareService;

  public WordSquareController(final WordSquareService wordSquareService) {
    this.wordSquareService = wordSquareService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WordSquareResponse createWordSquare(
      @Valid
      @NotNull
      @RequestBody final WordSquareRequest wordSquareRequest) throws WordSquareServiceException {
    return this.wordSquareService.createWordSquare(wordSquareRequest);
  }
}
