package org.anhonesteffort.sciencebox.serial;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public interface ChannelListener {

  public int getChannel();

  public void onDataReceived(byte[] data);

}
