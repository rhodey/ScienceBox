package org.anhonesteffort.sciencebox.standard;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface EnvironmentControl {

  public Sensor.SensorType getControlType();

  public Hardware.TypedValue getLastReading();

  public Hardware.TypedValue getTargetReading();

  public void setTargetReading(Hardware.TypedValue targetReading);

}
