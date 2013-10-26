package org.anhonesteffort.sciencebox.hardware.sensor;

import org.anhonesteffort.sciencebox.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public interface SensorListener {

  public void onNewReading(Sensor.SensorType sensorType, Hardware.DataType dataType, double reading);

  public void onNewReadings(Sensor.SensorType sensorType, Hardware.DataType dataType, double[] readings);

}
