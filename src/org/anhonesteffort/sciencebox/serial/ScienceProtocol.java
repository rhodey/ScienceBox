package org.anhonesteffort.sciencebox.serial;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class ScienceProtocol {

  private static final byte CONTROL_CHANNEL_OFF = 0x00;
  private static final byte CONTROL_CHANNEL_ON = 0x01;

  public static final byte CONTROL_CHANNEL_BLOWER         = 0x00;
  public static final byte CONTROL_CHANNEL_HUMIDIFIER     = 0x01;
  public static final byte CONTROL_CHANNEL_PELTIER_COOLER = 0x02;
  public static final byte CONTROL_CHANNEL_PELTIER_HEATER = 0x03;

  protected static final char SENSOR_READ_SEPARATOR = 0x2C;
  protected static final char SENSOR_READ_END       = 0x0A;

  public static final byte SENSOR_CHANNEL_TEMPERATURE = 0x00;
  public static final byte SENSOR_CHANNEL_HUMIDIFIER  = 0x01;

  public static byte[] turnOffChannel(byte channel) {
    return new byte[] {channel, CONTROL_CHANNEL_OFF};
  }

  public static byte[] turnOnChannel(byte channel) {
    return new byte[] {channel, CONTROL_CHANNEL_ON};
  }

}
