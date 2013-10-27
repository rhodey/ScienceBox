package org.anhonesteffort.sciencebox.standard;

import org.anhonesteffort.sciencebox.standard.hardware.sensor.ScienceSensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.SensorListener;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public abstract class AbstractEnvironmentControl implements EnvironmentControl, SensorListener {

  private ScienceSensor sensor;

  public AbstractEnvironmentControl(ScienceSensor sensor) {
    this.sensor = sensor;
    sensor.addListener(this);
  }

  @Override
  public Sensor.SensorType getControlType() {
    return sensor.getSensorType();
  }

  public void finish() {
    sensor.removeListener(this);
  }

}
