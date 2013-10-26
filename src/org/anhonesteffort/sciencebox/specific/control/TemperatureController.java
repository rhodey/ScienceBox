package org.anhonesteffort.sciencebox.specific.control;

import org.anhonesteffort.sciencebox.control.SimpleController;
import org.anhonesteffort.sciencebox.hardware.Hardware;
import org.anhonesteffort.sciencebox.specific.hardware.PeltierCooler;
import org.anhonesteffort.sciencebox.specific.hardware.PeltierHeater;
import org.anhonesteffort.sciencebox.specific.hardware.sensor.TemperatureSensor;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class TemperatureController extends SimpleController {

  private PeltierCooler cooler;
  private PeltierHeater heater;

  public TemperatureController(TemperatureSensor tempSensor, PeltierCooler cooler, PeltierHeater heater) {
    this.cooler = cooler;
    this.heater =  heater;
    tempSensor.addListener(this);
  }

  @Override
  protected void handleNewReading() {
    if (super.getLastReading() < super.getTargetReading()) {
      cooler.onNewSetting(Hardware.DataType.ON_OFF, 0);
      heater.onNewSetting(Hardware.DataType.ON_OFF, 1);
    }
    else {
      cooler.onNewSetting(Hardware.DataType.ON_OFF, 1);
      heater.onNewSetting(Hardware.DataType.ON_OFF, 0);
    }
  }

}
