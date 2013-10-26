package org.anhonesteffort.sciencebox.custom.control;

import org.anhonesteffort.sciencebox.custom.hardware.Humidifier;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.StandardSensor;
import org.anhonesteffort.sciencebox.standard.control.SingleSensorController;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class HumidityController extends SingleSensorController {

  private Humidifier humidifier;

  public HumidityController(StandardSensor humiditySensor, Humidifier humidifier) {
    super(humiditySensor);
    this.humidifier = humidifier;
  }

  @Override
  protected void handleNewReading() {
    if (super.getLastReading() < super.getTargetReading())
      humidifier.onNewSetting(Hardware.DataType.ON_OFF, 1);
    else
      humidifier.onNewSetting(Hardware.DataType.ON_OFF, 0);
  }

}
