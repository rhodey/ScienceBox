package org.anhonesteffort.sciencebox.standard.hardware.sensor;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface StandardSensor {

  public void addListener(SensorListener listener);

  public void removeListener(SensorListener listener);

}
