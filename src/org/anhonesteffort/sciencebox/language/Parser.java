package org.anhonesteffort.sciencebox.language;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Programmer: rhodey
 * Date: 10/12/13
 */
public class Parser {

  private static final String TEMP_FILE_NAME = "FanOn.temp";

  public Parser(FileInputStream inputFile) throws FileNotFoundException, IOException {
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

  private BufferedReader getTempBufferedReader() {
    try {

      DataInputStream temp = new DataInputStream(new FileInputStream(TEMP_FILE_NAME));
      InputStreamReader tempReader = new InputStreamReader(temp);
      return new BufferedReader(tempReader);

    } catch (Exception e) {
      System.out.println("lol, wut? " + e);
    }

    return null;
  }

  public boolean verifySyntax() {
    BufferedReader tempReader = getTempBufferedReader();

    try {

      String line;
      while ((line = tempReader.readLine()) != null) {
        if (GrammarHelper.isBlockBegin(line)) {
          System.out.println("is block begin: " + line);

          if (GrammarHelper.isLoopBegin(line)) {
            System.out.println("is loop begin: " + line);
            System.out.println("loop count: " + GrammarHelper.getLoopCount(line));
          }
          else
            System.out.println("is procedure named: " + GrammarHelper.getProcedureName(line));
        }

        else if (GrammarHelper.isBlockEnd(line)) {
          System.out.println("is block end: " + line);

          if (GrammarHelper.isLoopEnd(line))
            System.out.println("is loop end: " + line);
          else if (GrammarHelper.isProcedureEnd(line, "SANITIZE"))
            System.out.println("is end of " + "SANITIZE" + " procedure.");
        }

        else if (GrammarHelper.isWaitStatement(line)) {
          System.out.println("is wait statement: " + line);
          System.out.println("wait " + GrammarHelper.getWaitCountMilliseconds(line) + " milliseconds.");
        }

        else if (GrammarHelper.isDeviceSetting(line)) {
          System.out.println("is device setting: " + line);
          System.out.println("device: " + GrammarHelper.getDeviceType(line));
          System.out.println("setting type: " + GrammarHelper.getSettingType(line));
          System.out.println("setting value: " + GrammarHelper.getSettingValue(line));
        }

        else if (GrammarHelper.isControlSetting(line)) {
          System.out.println("is control setting: " + line);
          System.out.println("control type: " + GrammarHelper.getControlType(line));
          System.out.println("setting type: " + GrammarHelper.getSettingType(line));
          System.out.println("setting value: " + GrammarHelper.getSettingValue(line));
        }
      }
    } catch (IOException e) {
      System.out.println("lol, whut? " + e);
    } catch (IllegalSyntaxException e) {
      System.out.println("lgd, uw! " + e);
    }


    return true;
  }

}
