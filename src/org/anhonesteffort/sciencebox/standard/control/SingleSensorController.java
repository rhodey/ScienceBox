package org.anhonesteffort.sciencebox.standard.control;

import org.anhonesteffort.sciencebox.standard.hardware.sensor.StandardSensor;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.SensorListener;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class SingleSensorController implements StandardController, SensorListener {

  protected double last_reading    = 0.0;
  protected double target_reading  = 0.0;

  public SingleSensorController(StandardSensor sensor) {
    sensor.addListener(this);
  }

  protected void handleNewReading() {
    // should be implemented in sub-class!
  }

  @Override
  public double getLastReading() {
    return last_reading;
  }

  @Override
  public double getTargetReading() {
    return target_reading;
  }

  @Override
  public void setTargetReading(double target_reading) {
    this.target_reading = target_reading;
    handleNewReading();
  }

  @Override
  public void onNewReading(Sensor.SensorType sensorType, Hardware.DataType dataType, double reading) {
    last_reading = reading;
    handleNewReading();
  }

}
