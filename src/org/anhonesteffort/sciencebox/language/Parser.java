package org.anhonesteffort.sciencebox.language;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 10/12/13
 */
public class Parser {

  private static final String TEMP_FILE_NAME = "FanOn.temp";

  public Parser(FileInputStream inputFile) throws IOException {
    DataInputStream in = new DataInputStream(inputFile);
    InputStreamReader inReader = new InputStreamReader(in);
    BufferedReader inputFileReader = new BufferedReader(inReader);
    makeTempFile(inputFileReader);
  }

  private void makeTempFile(BufferedReader inputFileReader) throws IOException {
    BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(TEMP_FILE_NAME));

      String line;
    while ((line = inputFileReader.readLine()) != null) {
      tempFileWriter.append(line.trim().toUpperCase());
      tempFileWriter.newLine();
    }

    tempFileWriter.close();
    inputFileReader.close();
  }

  protected BufferedReader getTempFileReader() {
    try {

      DataInputStream temp = new DataInputStream(new FileInputStream(TEMP_FILE_NAME));
      InputStreamReader tempReader = new InputStreamReader(temp);
      return new BufferedReader(tempReader);

    } catch (Exception e) {
      System.out.println("Exception opening temp file for reader? " + e);
    }

    return null;
  }

  public boolean isSyntaxCorrect() {
    BufferedReader tempReader = getTempFileReader();

    List<String> procedureNames = new LinkedList<String>();
    List<String> proceduresWithNoEnd = new LinkedList<String>();
    int loops_with_no_end = 0;

    try {

      String line;
      while ((line = tempReader.readLine()) != null) {
        boolean line_verified = false;

        if (GrammarHelper.isComment(line))
          line_verified = true;

        else if (GrammarHelper.isBlockBegin(line)) {
          if (GrammarHelper.isLoopBegin(line)) {
            line_verified = (GrammarHelper.getLoopCount(line) != 0);
            loops_with_no_end++;
          }
          else {
            if (loops_with_no_end > 0)
              throw new IllegalSyntaxException("Procedure declarations not allowed inside loops.");

            if (proceduresWithNoEnd.size() > 0)
              throw new IllegalSyntaxException("Procedure declarations not allowed inside other procedures.");

            if (procedureNames.contains(GrammarHelper.getProcedureName(line)))
              throw new IllegalSyntaxException("Procedure " + GrammarHelper.getProcedureName(line) +
                  " has already been declared.");

            procedureNames.add(GrammarHelper.getProcedureName(line));
            proceduresWithNoEnd.add(GrammarHelper.getProcedureName(line));
            line_verified = true;
          }
        }

        else if (GrammarHelper.isBlockEnd(line)) {
          if (GrammarHelper.isLoopEnd(line)) {
            line_verified = true;
            loops_with_no_end--;
          }

          else {
            String endProcedureName = null;
            for (String procedureName : procedureNames) {
              if (GrammarHelper.isProcedureEnd(line, procedureName)) {
                endProcedureName = procedureName;
                break;
              }
            }
            if (endProcedureName != null) {
              line_verified = true;
              proceduresWithNoEnd.remove(endProcedureName);
            }
            else
              throw new IllegalSyntaxException("Cannot verify syntax of end block: " + line);
          }
        }

        else if (GrammarHelper.isWaitStatement(line))
          line_verified = (GrammarHelper.getWaitCountMilliseconds(line) != 0);

        else if (GrammarHelper.isDeviceSetting(line)) {
          GrammarHelper.getDeviceType(line);
          GrammarHelper.getSettingType(line);
          GrammarHelper.getSettingValue(line);
          line_verified = true;
        }

        else if (GrammarHelper.isControlSetting(line)) {
          GrammarHelper.getControlType(line);
          GrammarHelper.getSettingType(line);
          GrammarHelper.getSettingValue(line);
          line_verified = true;
        }

        else if (procedureNames.contains(line))
          line_verified = true;

        else if (line.equals(""))
          line_verified = true;

        if (!line_verified)
          throw new IllegalSyntaxException("Syntax of line cannot be verified: " + line);
      }

      if (proceduresWithNoEnd.size() != 0)
        throw new IllegalSyntaxException("Not all procedures have an end.");

      if (loops_with_no_end != 0)
        throw new IllegalSyntaxException("Number of BEGIN LOOP and END LOOP statements do not match: " + loops_with_no_end);

    } catch (IOException e) {
      System.out.println("IOException while verifying syntax: " + e);
      return false;
    } catch (IllegalSyntaxException e) {
      System.out.println(e);
      return false;
    }
    finally {
      try {
        tempReader.close();
      } catch (IOException e) {
        System.out.println("IOException while closing temp file: " + e);
      }
    }

    return true;
  }

}
