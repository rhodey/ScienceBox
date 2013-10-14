package org.anhonesteffort.sciencebox.language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Programmer: rhodey
 * Date: 10/12/13
 */
public class GrammarHelper {

  public static final String[] DEVICE_TOKENS = {
      Grammar.TOKEN_DEVICE_FAN,
      Grammar.TOKEN_DEVICE_HEATER,
      Grammar.TOKEN_DEVICE_COOLER,
      Grammar.TOKEN_DEVICE_HUMIDIFIER
  };

  public static final String[] CONTROL_TOKENS = {
      Grammar.TOKEN_CONTROL_TEMPERATURE,
      Grammar.TOKEN_CONTROL_HUMIDITY
  };

  private static enum WaitType {
    MILLISECONDS,
    SECONDS,
    MINUTES,
    HOURS,
    DAYS
  };

  public static boolean isBlockBegin(String line) {
    return line.matches("^(" + Grammar.TOKEN_BLOCK_BEGIN + ")(\\s+).*");
  }

  public static boolean isBlockEnd(String line) {
    return line.matches("^(" + Grammar.TOKEN_BLOCK_END + ")(\\s+).*");
  }

  public static boolean isLoopBegin(String line) {
    return line.matches("^(" + Grammar.TOKEN_BLOCK_BEGIN + ")(\\s+)(" + Grammar.TOKEN_LOOP + ")(\\s+).*");
  }

  public static boolean isLoopEnd(String line) {
    return line.matches("^(" + Grammar.TOKEN_BLOCK_END + ")(\\s+)(" + Grammar.TOKEN_LOOP + ")(\\s*)$");
  }

  public static int getLoopCount(String line) throws IllegalSyntaxException {
    if (!isLoopBegin(line))
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN BLOCK BEGIN and/or TOKEN LOOP.");

    int num_pos = line.indexOf(Grammar.TOKEN_LOOP) + 1;
    String numString = line.substring(num_pos, line.length());
    return Integer.parseInt(numString);
  }

  public static String getProcedureName(String line) throws IllegalSyntaxException {
    if (!isBlockBegin(line))
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN BLOCK BEGIN.");

    int name_pos = Grammar.TOKEN_BLOCK_BEGIN.length() + 1;
    String nameFound = line.substring(name_pos).trim();

    if (nameFound.length() == 0)
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN PROCEDURE NAME.");

    if (nameFound.contains(" "))
      throw new IllegalSyntaxException("White space found inside TOKEN PROCEDURE NAME.");

    return nameFound;
  }

  public static boolean isProcedureEnd(String line, String procedureName) throws IllegalSyntaxException {
    if (!isBlockEnd(line))
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN BLOCK END.");

    int name_pos = Grammar.TOKEN_BLOCK_END.length() + 1;
    String nameFound = line.substring(name_pos).trim();

    if (nameFound.length() == 0)
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN PROCEDURE NAME.");

    if (nameFound.contains(" "))
      throw new IllegalSyntaxException("White space found inside TOKEN PROCEDURE NAME.");

    return nameFound.equals(procedureName);
  }

  public static boolean isWaitStatement(String line) {
    return line.matches("^(" + Grammar.TOKEN_WAIT + ")(\\s+).*");
  }

  private static WaitType getWaitType(String line) throws IllegalSyntaxException {
    Pattern pattern = Pattern.compile(
        "^(" + Grammar.TOKEN_WAIT + ")(\\s+)" +
          "([0-9]+)(\\s*)(\\S+)(\\s+)$");

    Matcher matcher = pattern.matcher(line);
    while (matcher.find())
      System.out.println("getWaitType matcher.group() -> " + matcher.group());

    if (line.matches("([0-9]+)(\\s*)(" + Grammar.TOKEN_TYPE_MILLISECONDS + ")(\\s+)$"))
      return WaitType.MILLISECONDS;
    if (line.matches("(" + Grammar.TOKEN_TYPE_SECONDS + ")(\\s+)$"))
      return WaitType.SECONDS;
    if (line.matches("(" + Grammar.TOKEN_TYPE_MINUTES + ")(\\s+)$"))
      return WaitType.MINUTES;
    if (line.matches("(" + Grammar.TOKEN_TYPE_HOURS + ")(\\s+)$"))
      return WaitType.HOURS;
    if (line.matches("(" + Grammar.TOKEN_TYPE_DAYS + ")(\\s+)$"))
      return WaitType.DAYS;


    throw new IllegalSyntaxException("Provided line does not contain legal TOKEN WAIT TYPE.");
  }

  public static long getWaitMilliseconds(String line) throws IllegalSyntaxException {
    if (!isWaitStatement(line))
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN WAIT.");

    int num_pos = Grammar.TOKEN_WAIT.length() + 1;
    String numFound = line.substring(num_pos).trim();

    if (numFound.length() == 0)
      throw new IllegalSyntaxException("Provided line does not contain legal TOKEN PROCEDURE NAME.");

    if (numFound.contains(" "))
      throw new IllegalSyntaxException("White space found inside TOKEN PROCEDURE NAME.");

    return 0;
  }

  public static boolean isDeviceSetting(String line) {
    for (int i = 0; i < DEVICE_TOKENS.length; i++) {
      if (line.matches("^(" + DEVICE_TOKENS[i] + ")(\\s+).*"))
        return true;
    }
    return false;
  }

  public static boolean isControlSetting(String line) {
    for (int i = 0; i < CONTROL_TOKENS.length; i++) {
      if (line.matches("^(" + CONTROL_TOKENS[i] + ")(\\s+).*"))
        return true;
    }
    return false;
  }

}
