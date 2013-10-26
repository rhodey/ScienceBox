package org.anhonesteffort.sciencebox.hardware.sensor;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface RawDataListener {

  public void onRawDataReceived(byte[] bytes);

}
