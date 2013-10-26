package org.anhonesteffort.sciencebox.control;

import org.anhonesteffort.sciencebox.hardware.Hardware;
import org.anhonesteffort.sciencebox.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.hardware.sensor.SensorListener;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class SimpleController implements SensorListener {

  protected double target_reading = 0.0;
  protected double last_reading   = 0.0;

  protected void handleNewReading() {
    // must be implemented in sub-class!
  }

  public double getLastReading() {
    return last_reading;
  }

  public double getTargetReading() {
    return target_reading;
  }

  public void setTargetReading(double target_reading) {
    this.target_reading = target_reading;
    handleNewReading();
  }

  @Override
  public void onNewReading(Sensor.SensorType sensorType, Hardware.DataType dataType, double reading) {
    last_reading = reading;
    handleNewReading();
  }

  @Override
  public void onNewReadings(Sensor.SensorType sensorType, Hardware.DataType dataType, double[] reading) {
    // not expecting multiple readings.
  }

}
