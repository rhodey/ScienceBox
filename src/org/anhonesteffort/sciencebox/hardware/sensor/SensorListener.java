package org.anhonesteffort.sciencebox.hardware.sensor;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public interface SensorListener {

  public void onReadingChanged(byte[] new_reading);

}