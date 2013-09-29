package org.anhonesteffort.sciencebox.serial;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class ScienceProtocol {

  private static final byte CHANNEL_OFF = 0x00;
  private static final byte CHANNEL_ON  = 0x01;

  protected static final char DATA_VALUE_SEPARATOR = 0x2C;
  protected static final char DATA_END        = 0x0A;

  public static byte[] turnOffChannel(byte channel) {
    return new byte[] {channel, CHANNEL_OFF};
  }

  public static byte[] turnOnChannel(byte channel) {
    return new byte[] {channel, CHANNEL_ON};
  }

}
