package org.anhonesteffort.sciencebox.standard.hardware;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class Hardware {

  public static enum HardwareType {
    FAN,
    HEATER,
    COOLER,
    HUMIDIFIER
  }

  public static enum DataType {
    ON_OFF,
    PERCENT,
    CELSIUS,
    FAHRENHEIT
  }

}
