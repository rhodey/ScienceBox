package org.anhonesteffort.sciencebox.standard;

import org.anhonesteffort.sciencebox.standard.language.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class Terminal implements Runnable {

  private static final String MENU = "#######################\n" +
                                     "#   ScienceBox v0.1   #\n" +
                                     "#      Science!!      #\n" +
                                     "#                     #\n" +
                                     "# _Options_           #\n" +
                                     "#   0) show menu      #\n" +
                                     "#   1) run script     #\n" +
                                     "#   2) quit           #\n" +
                                     "#######################\n";

  private static final String MESSAGE_SCIENCE_PREVAILS = "science prevails!\n";
  private static final String MESSAGE_SYNTAX_VERIFIED  = "FanOn syntax verified.\n";

  private static final String PROMPT_OPTION      = "option: ";
  private static final String PROMPT_SCRIPT_PATH = "full path to FanOn script: ";

  private static final String ERROR_INVALID_OPTION   = "invalid option: ";
  private static final String ERROR_BAD_SCRIPT_PATH  = "bad script path.\n";
  private static final String ERROR_SYNTAX_INCORRECT = "FanOn syntax incorrect!\n";

  private static final int OPTION_SHOW_MENU  = 0;
  private static final int OPTION_RUN_SCRIPT = 1;
  private static final int OPTION_QUIT       = 2;

  private BufferedReader userInput;
  private boolean quit = false;

  private ControlledEnvironment environment;

  public Terminal(ControlledEnvironment environment) {
    this.environment = environment;
  }

  private void handleRunScript(String scriptPath) {
    try {
      FileInputStream fileIn = new FileInputStream(scriptPath);
      Parser fanParse = new Parser(fileIn);
      if (fanParse.isSyntaxCorrect())
        System.out.print(MESSAGE_SYNTAX_VERIFIED);
      else
        System.out.print(ERROR_SYNTAX_INCORRECT);
    } catch (IOException e) {
      System.out.print(ERROR_BAD_SCRIPT_PATH);
    }
  }

  private void handleGetScriptPath() throws IOException {
    System.out.print(PROMPT_SCRIPT_PATH);
    handleRunScript(userInput.readLine());
  }

  private void handleInput(String line) throws IOException {
    try {

      switch (Integer.parseInt(line)) {
        case OPTION_SHOW_MENU:
          System.out.print(MENU);
          break;

        case OPTION_RUN_SCRIPT:
          handleGetScriptPath();
          break;

        case OPTION_QUIT:
          quit = true;
          System.out.print(MESSAGE_SCIENCE_PREVAILS);
          break;

        default:
          System.out.println(ERROR_INVALID_OPTION + line);
          break;
      }

    } catch (NumberFormatException e) {
      System.out.println(ERROR_INVALID_OPTION + line);
    }
  }

  @Override
  public void run() {
    userInput = new BufferedReader(new InputStreamReader(System.in));
    System.out.print(MENU);

    try {

      while (!quit) {
        System.out.print(PROMPT_OPTION);
        handleInput(userInput.readLine());
        System.out.println();
      }

    } catch (IOException e) {
      System.out.println("caught IOException while reading user input, exception: " + e);
    }
  }

}
