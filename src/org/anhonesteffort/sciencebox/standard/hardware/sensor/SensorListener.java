package org.anhonesteffort.sciencebox.standard.hardware.sensor;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public interface SensorListener {

  public void onNewReading(Sensor.SensorType sensorType, Hardware.DataType dataType, double reading);

}
