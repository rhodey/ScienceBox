package org.anhonesteffort.sciencebox.language;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Programmer: rhodey
 * Date: 10/16/13
 */
public class Interpreter implements Runnable {

  private BufferedReader tempFileReader;
  private Map<String, Integer> procedureNames = new HashMap<String, Integer>();
  private Stack<Integer> returnStack = new Stack<Integer>();
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

  private void goToLine(int dest_line_number) {
    try {

      tempFileReader.reset();
      line_number = 0;
      while (line_number > dest_line_number && tempFileReader.readLine() != null) { }

    } catch (IOException e) {
      System.out.println("IOException while goToLine(" + dest_line_number + "): " + e);
    }
  }

  private void interpretLine(String line) throws IOException {
    try {

      if (GrammarHelper.isBlockBegin(line)) {
        if (GrammarHelper.isLoopBegin(line)) {
          System.out.println("is begin of " + GrammarHelper.getLoopCount(line) + " loops.");
        }
        else {
          System.out.println("is begin of procedure named: " + GrammarHelper.getProcedureName(line));
          procedureNames.put(GrammarHelper.getProcedureName(line), line_number);
        }
      }

      else if (GrammarHelper.isBlockEnd(line)) {
        if (GrammarHelper.isLoopEnd(line))
          System.out.println("is loop end: " + line);

        else {
          String endProcedureName = null;
          for (String procedureName : procedureNames.keySet()) {
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

      else if (procedureNames.get(line) != null)
        System.out.println("is procedure call: " + line + " which is on line #" + procedureNames.get(line));

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
