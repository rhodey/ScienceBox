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

  private Parser parser;
  private BufferedReader tempFileReader;
  private List<InterpreterListener> listeners = new LinkedList<InterpreterListener>();

  private Map<String, Integer> procedureNames = new HashMap<String, Integer>();
  private Stack<Integer> procedureReturnStack = new Stack<Integer>();
  private Stack<LoopRecord> loopReturnStack = new Stack<LoopRecord>();

  private boolean exec = true;
  private int line_number = 0;

  public Interpreter(Parser parser) {
    if (!parser.isSyntaxCorrect())
      throw new IllegalArgumentException("Provided parser cannot verify syntax of its FanOn script.");

    this.parser = parser;
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

      tempFileReader.close();
      tempFileReader = parser.getTempFileReader();
      line_number = 0;
      while (line_number < dest_line_number && tempFileReader.readLine() != null) { line_number++; }

    } catch (IOException e) {
      System.out.println("IOException while goToLine(" + dest_line_number + "): " + e);
    }
  }

  private void goToProcedure(String procedureName) {
    procedureReturnStack.push(line_number + 1);
    goToLine(procedureNames.get(procedureName));
  }

  private void interpretLine(String line) throws IOException {
    try {

      if (GrammarHelper.isBlockBegin(line)) {
        if (GrammarHelper.isLoopBegin(line)) {
          if (exec)
            loopReturnStack.push(new LoopRecord(line_number + 1, GrammarHelper.getLoopCount(line)));
        }
        else {
          procedureNames.put(GrammarHelper.getProcedureName(line), line_number + 1);
          exec = false;
        }
      }

      else if (GrammarHelper.isBlockEnd(line)) {
        if (GrammarHelper.isLoopEnd(line)) {
          if (exec) {
            if (!loopReturnStack.empty()) {
              loopReturnStack.peek().loop_count--;

              if (loopReturnStack.peek().loop_count > 0) {
                goToLine(loopReturnStack.peek().line_number);
                line_number--;
              }
              else
                loopReturnStack.pop();
            }
          }
        }

        else {
          String endProcedureName = null;
          for (String procedureName : procedureNames.keySet()) {
            if (GrammarHelper.isProcedureEnd(line, procedureName)) {
              endProcedureName = procedureName;
              break;
            }
          }

          if (!procedureReturnStack.empty()) {
            goToLine(procedureReturnStack.pop());
            line_number--;
          }
          exec = true;
        }
      }

      else if (GrammarHelper.isWaitStatement(line)) {
        if (exec)
          System.out.println("wait " + GrammarHelper.getWaitCountMilliseconds(line) + " milliseconds.");
      }

      else if (GrammarHelper.isDeviceSetting(line)) {
        if (exec) {
          for (InterpreterListener listener : listeners)
            listener.onDeviceSetting(GrammarHelper.getDeviceType(line),
                GrammarHelper.getSettingType(line),
                GrammarHelper.getSettingValue(line));
        }
      }

      else if (GrammarHelper.isControlSetting(line)) {
        if (exec) {
          for (InterpreterListener listener : listeners)
            listener.onControlSetting(GrammarHelper.getControlType(line),
                GrammarHelper.getSettingType(line),
                GrammarHelper.getSettingValue(line));
        }
      }

      else if (procedureNames.get(line) != null) {
        if (exec) {
          goToProcedure(line);
          line_number--;
        }
      }

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
        line_number++;
      }

      for (InterpreterListener listener : listeners)
        listener.onInterpretComplete();

    } catch (IOException e) {
      System.out.println("IOException while interpreting script: " + e);
    }
  }

  private class LoopRecord {

    public int line_number;
    public int loop_count;

    public LoopRecord(int line_number, int loop_count) {
      this.line_number = line_number;
      this.loop_count = loop_count;
    }

  }

}
