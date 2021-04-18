package aaronrebak.wordunscramble.api.controller;

import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import aaronrebak.wordunscramble.api.service.WordSquareService;
import javax.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wordsquare", produces = MediaType.APPLICATION_JSON_VALUE)
public class WordSquareController {

  private final WordSquareService wordSquareService;

  public WordSquareController(final WordSquareService wordSquareService) {
    this.wordSquareService = wordSquareService;
  }

  @PostMapping("/{wordSquareCount}")
  @ResponseStatus(HttpStatus.CREATED)
  public WordSquareResponse createWordSquare(
      @Size(min = 1) @PathVariable final Integer wordSquareCount,
      @RequestBody final WordSquareRequest wordSquareRequest) {
    return this.wordSquareService.createWordSquare(wordSquareCount, wordSquareRequest);
  }
}
