package org.anhonesteffort.sciencebox.custom;

import org.anhonesteffort.sciencebox.custom.hardware.PeltierCooler;
import org.anhonesteffort.sciencebox.custom.hardware.PeltierHeater;
import org.anhonesteffort.sciencebox.standard.AbstractEnvironmentControl;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.ScienceSensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class TemperatureControl extends AbstractEnvironmentControl {

  private PeltierCooler cooler;
  private PeltierHeater heater;
  private Hardware.TypedValue lastReading   = null;
  private Hardware.TypedValue targetReading = null;

  public TemperatureControl(ScienceSensor temperatureSensor, PeltierCooler cooler, PeltierHeater heater) {
    super(temperatureSensor);
    this.cooler = cooler;
    this.heater =  heater;
  }

  @Override
  public Hardware.TypedValue getLastReading() {
    return lastReading;
  }

  @Override
  public Hardware.TypedValue getTargetReading() {
    return targetReading;
  }

  private Hardware.TypedValue handleCelsiusConversion(Hardware.TypedValue reading) {
    if (reading.getType() == Hardware.DataType.CELSIUS)
      return reading;

    if (reading.getType() == Hardware.DataType.FAHRENHEIT)
      return new Hardware.TypedValue(Hardware.DataType.CELSIUS, (reading.getValue() - 32) * (5.0 / 9.0));

    throw new IllegalArgumentException("I am a temperature controller, you expect me to understand " + reading.getType() + "?!");
  }

  private void handleNewReading() {
    if (lastReading.getValue() < targetReading.getValue()) {
      cooler.onNewSetting(new Hardware.TypedValue(Hardware.DataType.ON_OFF, 0));
      heater.onNewSetting(new Hardware.TypedValue(Hardware.DataType.ON_OFF, 1));
    }
    else {
      cooler.onNewSetting(new Hardware.TypedValue(Hardware.DataType.ON_OFF, 1));
      heater.onNewSetting(new Hardware.TypedValue(Hardware.DataType.ON_OFF, 0));
    }
  }

  @Override
  public void setTargetReading(Hardware.TypedValue targetReading) {
    this.targetReading = handleCelsiusConversion(targetReading);
    handleNewReading();
  }

  @Override
  public void onNewReading(Sensor.SensorType sensorType, Hardware.TypedValue sensorReading) {
    lastReading = handleCelsiusConversion(sensorReading);
    handleNewReading();
  }

}
