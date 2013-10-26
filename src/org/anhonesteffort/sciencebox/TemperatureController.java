package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.active.PeltierCooler;
import org.anhonesteffort.sciencebox.hardware.active.PeltierHeater;
import org.anhonesteffort.sciencebox.hardware.passive.SensorListener;
import org.anhonesteffort.sciencebox.hardware.passive.TemperatureSensor;
import org.anhonesteffort.sciencebox.language.Grammar;

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
      cooler.onNewSetting(Grammar.SettingType.ON_OFF, 0);
      heater.onNewSetting(Grammar.SettingType.ON_OFF, 1);
    }
    else {
      cooler.onNewSetting(Grammar.SettingType.ON_OFF, 1);
      heater.onNewSetting(Grammar.SettingType.ON_OFF, 0);
    }
  }

  public double getLastReading() {
    return last_temp;
  }

  public double getTarget() {
    return target_temp;
  }

  public void setTarget(double target_temp) {
    this.target_temp = target_temp;
    handleTempChange();
  }

  @Override
  public void onNewReading(byte[] bytes) {
    try {

      last_temp = Double.parseDouble(new String(bytes));
      handleTempChange();

    } catch (Exception e) {
      System.out.println("Could not parse double from " + bytes);
    }
  }

}
