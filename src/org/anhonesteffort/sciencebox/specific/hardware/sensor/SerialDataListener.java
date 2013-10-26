package org.anhonesteffort.sciencebox.specific.hardware.sensor;

import org.anhonesteffort.sciencebox.hardware.sensor.RawDataListener;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface SerialDataListener extends RawDataListener {

  public byte getChannel();

}
