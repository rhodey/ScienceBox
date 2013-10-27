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

  public static class TypedValue {
    private DataType dataType;
    private double value;

    public TypedValue(DataType dataType, double value) {
      this.dataType = dataType;
      this.value = value;
    }

    public DataType getType() {
      return dataType;
    }

    public double getValue() {
      return value;
    }
  }
}
