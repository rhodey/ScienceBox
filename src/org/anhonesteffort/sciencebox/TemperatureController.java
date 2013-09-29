package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.active.PeltierCooler;
import org.anhonesteffort.sciencebox.hardware.active.PeltierHeater;
import org.anhonesteffort.sciencebox.hardware.sensor.SensorListener;
import org.anhonesteffort.sciencebox.hardware.sensor.TemperatureSensor;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class TemperatureController implements SensorListener {

  private static final double MARGIN_F = 2.0;

  private TemperatureSensor tempSensor;
  private PeltierCooler cooler;
  private PeltierHeater heater;

  private double target_temp = 0.0;
  private double last_temp = 0.0;

  public TemperatureController(TemperatureSensor tempSensor, PeltierCooler cooler, PeltierHeater heater) {
    this.tempSensor = tempSensor;
    this.cooler = cooler;
    this.heater =  heater;

    tempSensor.addListener(this);
  }

  private void handleTempChange() {
    if (last_temp < target_temp) {
      cooler.off();
      heater.on();
    }
    else {
      cooler.on();
      heater.off();
    }
  }

  public void setTarget(double target_temp) {
    this.target_temp = target_temp;
    handleTempChange();
  }

  @Override
  public void onReadingChanged(byte[] new_reading) {
    last_temp = Double.parseDouble(new String(new_reading));
    handleTempChange();
  }

}
