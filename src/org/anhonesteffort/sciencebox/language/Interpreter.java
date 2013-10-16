package org.anhonesteffort.sciencebox.language;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 10/16/13
 */
public class Interpreter implements Runnable {

  private BufferedReader tempFileReader;
  private List<String> procedureNames = new LinkedList<String>();
  private int line_number = 0;

  private List<InterpreterListener> listeners = new LinkedList<InterpreterListener>();

  public Interpreter(Parser parser) {
    if (!parser.isSyntaxCorrect())
      throw new IllegalArgumentException("Provided parser cannot verify syntax of its FanOn script.");

    tempFileReader = parser.getTempFileReader();
  }

  public void addInterpreterListener(InterpreterListener listener) {
    listeners.add(listener);
  }

  public void removeInterpreterListener(InterpreterListener listener) {
    listeners.remove(listener);
  }

  private void interpretLine(String line) throws IOException {
    try {

      if (GrammarHelper.isBlockBegin(line)) {
        if (GrammarHelper.isLoopBegin(line)) {
          System.out.println("is begin of " + GrammarHelper.getLoopCount(line) + " loops.");
        }
        else {
          System.out.println("is begin of procedure named: " + GrammarHelper.getProcedureName(line));
          procedureNames.add(GrammarHelper.getProcedureName(line));
        }
      }

      else if (GrammarHelper.isBlockEnd(line)) {
        if (GrammarHelper.isLoopEnd(line))
          System.out.println("is loop end: " + line);

        else {
          String endProcedureName = null;
          for (String procedureName : procedureNames) {
            if (GrammarHelper.isProcedureEnd(line, procedureName)) {
              endProcedureName = procedureName;
              break;
            }
          }
          System.out.println("is end of procedure named: " + endProcedureName);
        }
      }

      else if (GrammarHelper.isWaitStatement(line)) {
        System.out.println("wait " + GrammarHelper.getWaitCountMilliseconds(line) + " milliseconds.");
      }

      else if (GrammarHelper.isDeviceSetting(line)) {
        for (InterpreterListener listener : listeners)
          listener.onDeviceSetting(GrammarHelper.getDeviceType(line),
              GrammarHelper.getSettingType(line),
              GrammarHelper.getSettingValue(line));
      }

      else if (GrammarHelper.isControlSetting(line)) {
        for (InterpreterListener listener : listeners)
          listener.onControlSetting(GrammarHelper.getControlType(line),
              GrammarHelper.getSettingType(line),
              GrammarHelper.getSettingValue(line));
      }

      else if (procedureNames.contains(line))
        System.out.println("is procedure call: " + line);

      line_number++;
    } catch (IllegalSyntaxException e) {
      System.out.println("Syntax *was* correct, what happened?! " + e);
    }
  }

  @Override
  public void run() {
    try {

      for (InterpreterListener listener : listeners)
        listener.onInterpretBegin();

      String line;
      while ((line = tempFileReader.readLine()) != null) {
        for (InterpreterListener listener : listeners)
          listener.onInterpretNextLine(line_number);

        interpretLine(line);
      }

      for (InterpreterListener listener : listeners)
        listener.onInterpretComplete();

    } catch (IOException e) {
      System.out.println("IOException while interpreting script: " + e);
    }
  }

}
