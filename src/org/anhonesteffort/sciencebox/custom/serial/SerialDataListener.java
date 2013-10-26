package org.anhonesteffort.sciencebox.custom.serial;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface SerialDataListener {

  public byte getChannel();

  public void onSerialDataReceived(byte[] bytes);

}
