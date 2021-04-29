package aaronrebak.wordunscramble.api;

import aaronrebak.wordunscramble.api.controller.WordSquareController;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest;
import aaronrebak.wordunscramble.api.model.request.WordSquareRequest.WordSquareRequestBuilder;
import aaronrebak.wordunscramble.api.printer.ConsolePrinter;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring.main", name = "web-application-type", havingValue = "command")
public class CommandLineExecutor implements CommandLineRunner {

  private final WordSquareController wordSquareController;
  private final ConsolePrinter consolePrinter;
  private final Scanner scanner;

  public CommandLineExecutor(
      final WordSquareController wordSquareController,
      final ConsolePrinter consolePrinter) {
    this.wordSquareController = wordSquareController;
    this.consolePrinter = consolePrinter;
    this.scanner = new Scanner(System.in);
  }

  @Override
  public void run(String... args) {
    this.consolePrinter.printWelcomeMessage();
    while (true) {
      final WordSquareRequestBuilder requestBuilder = WordSquareRequest.builder();
      this.consolePrinter
          .printMessageToConsole(
              "Enter a number to generate word square nxn (press 'T' to terminate program): ");
      String value = this.scanner.next();
      if (value.equals("T")) {
        break;
      }
      try {
        requestBuilder.wordSquareSize(Integer.parseInt(value));
      } catch (NumberFormatException e) {
        consolePrinter.printMessageToConsoleLine("That is not a known number. Try again...");
        continue;
      }
      this.consolePrinter
          .printMessageToConsole("Enter characters (press 'T' to terminate program): ");
      value = scanner.next();
      if (value.equals("T")) {
        break;
      }
      requestBuilder.characters(value);
      try {
        this.consolePrinter.printResponseToConsoleAsPrettyJson(
            this.wordSquareController.createWordSquare(requestBuilder.build()));
      } catch (final Exception exception) {
        this.consolePrinter.printMessageToConsoleLine(exception.getMessage());
      }
    }
  }

}
