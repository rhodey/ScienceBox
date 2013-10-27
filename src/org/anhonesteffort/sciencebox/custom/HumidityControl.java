package org.anhonesteffort.sciencebox.custom;

import org.anhonesteffort.sciencebox.custom.hardware.Humidifier;
import org.anhonesteffort.sciencebox.standard.AbstractEnvironmentControl;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.ScienceSensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class HumidityControl extends AbstractEnvironmentControl {

  private Humidifier humidifier;
  private Hardware.TypedValue lastReading   = null;
  private Hardware.TypedValue targetReading = null;

  public HumidityControl(ScienceSensor humiditySensor, Humidifier humidifier) {
    super(humiditySensor);
    this.humidifier = humidifier;
  }

  @Override
  public Hardware.TypedValue getLastReading() {
    return lastReading;
  }

  @Override
  public Hardware.TypedValue getTargetReading() {
    return targetReading;
  }

  private Hardware.TypedValue handleTypeCheck(Hardware.TypedValue reading) {
    if (reading.getType() != Hardware.DataType.PERCENT)
      throw new IllegalArgumentException("I am a humidity controller, you expect me to understand " + reading.getType() + "?!");

    return reading;
  }

  private void handleNewReading() {
    if (lastReading.getValue() < targetReading.getValue())
      humidifier.onNewSetting(new Hardware.TypedValue(Hardware.DataType.ON_OFF, 1));
    else
      humidifier.onNewSetting(new Hardware.TypedValue(Hardware.DataType.ON_OFF, 0));
  }

  @Override
  public void setTargetReading(Hardware.TypedValue targetReading) {
    this.targetReading = handleTypeCheck(targetReading);
    handleNewReading();
  }

  @Override
  public void onNewReading(Sensor.SensorType sensorType, Hardware.TypedValue sensorReading) {
    lastReading = handleTypeCheck(sensorReading);
    handleNewReading();
  }

}
