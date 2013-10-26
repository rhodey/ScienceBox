package org.anhonesteffort.sciencebox.custom.control;

import org.anhonesteffort.sciencebox.custom.hardware.PeltierCooler;
import org.anhonesteffort.sciencebox.custom.hardware.PeltierHeater;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.StandardSensor;
import org.anhonesteffort.sciencebox.standard.control.SingleSensorController;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class TemperatureController extends SingleSensorController {

  private PeltierCooler cooler;
  private PeltierHeater heater;

  public TemperatureController(StandardSensor temperatureSensor, PeltierCooler cooler, PeltierHeater heater) {
    super(temperatureSensor);
    this.cooler = cooler;
    this.heater =  heater;
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
