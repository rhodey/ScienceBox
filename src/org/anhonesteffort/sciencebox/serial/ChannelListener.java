package org.anhonesteffort.sciencebox.serial;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public interface ChannelListener {

  public byte getChannel();

  public void onDataReceived(byte[] reading);

}
