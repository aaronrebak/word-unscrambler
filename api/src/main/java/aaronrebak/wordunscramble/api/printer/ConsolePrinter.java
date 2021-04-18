package aaronrebak.wordunscramble.api.printer;

import aaronrebak.wordunscramble.api.model.response.WordSquareResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter {

  private final ObjectMapper objectMapper;

  public ConsolePrinter(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void printWelcomeMessage() {
    System.out.println("---------------------------");
    System.out.println("Welcome to word un-scrambler!");
    System.out.println("---------------------------");
  }

  public void printMessageToConsole(String message) {
    System.out.print(message);
  }

  public void printMessageToConsoleLine(String message) {
    System.out.println(message);
  }

  public void printResponseToConsoleAsPrettyJson(WordSquareResponse wordSquareResponse) {
    this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    try {
      System.out.println(objectMapper.writeValueAsString(wordSquareResponse));
    } catch (JsonProcessingException e) {
      System.out.println("Could not convert response object to pretty json.");
    }
  }
}

