package org.anhonesteffort.sciencebox.language;

/**
 * Programmer: rhodey
 * Date: 10/12/13
 */
public class Grammar {

  public static final String TOKEN_COMMENT_BEGIN = "//";

  public static final String TOKEN_BLOCK_BEGIN = "BEGIN";
  public static final String TOKEN_BLOCK_END   = "END";

  public static final String TOKEN_LOOP = "LOOP";

  public static final String TOKEN_TYPE_PERCENT      = "%";
  public static final String TOKEN_TYPE_CELSIUS      = "C";
  public static final String TOKEN_TYPE_FAHRENHEIT   = "F";
  public static final String TOKEN_TYPE_MILLISECONDS = "MILLISECONDS";
  public static final String TOKEN_TYPE_SECONDS      = "SECONDS";
  public static final String TOKEN_TYPE_MINUTES      = "MINUTES";
  public static final String TOKEN_TYPE_HOURS        = "HOURS";
  public static final String TOKEN_TYPE_DAYS         = "DAYS";

  public static final String TOKEN_VALUE_ON  = "ON";
  public static final String TOKEN_VALUE_OFF = "OFF";

  public static final String TOKEN_WAIT = "WAIT";

  public static final String TOKEN_HARDWARE_FAN        = "FAN";
  public static final String TOKEN_HARDWARE_HEATER     = "HEATER";
  public static final String TOKEN_HARDWARE_COOLER     = "COOLER";
  public static final String TOKEN_HARDWARE_HUMIDIFIER = "HUMIDIFIER";

  public static final String TOKEN_CONTROL_TEMPERATURE = "TEMPERATURE";
  public static final String TOKEN_CONTROL_HUMIDITY    = "HUMIDITY";

  public static enum SettingType {
    ON_OFF,
    PERCENT,
    CELSIUS,
    FAHRENHEIT
  }

  public static enum WaitType {
    MILLISECONDS,
    SECONDS,
    MINUTES,
    HOURS,
    DAYS
  }

  public static enum HardwareType {
    FAN,
    HEATER,
    COOLER,
    HUMIDIFIER
  }

  public static enum ControlType {
    TEMPERATURE,
    HUMIDITY
  }

}
