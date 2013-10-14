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

    inputFileReader.reset();
    String line;
    while ((line = inputFileReader.readLine()) != null)
      tempFileWriter.append(line.toUpperCase());

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

    return true;
  }

}
