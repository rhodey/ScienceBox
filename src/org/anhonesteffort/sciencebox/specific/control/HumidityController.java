package org.anhonesteffort.sciencebox.specific.control;

import org.anhonesteffort.sciencebox.control.SimpleController;
import org.anhonesteffort.sciencebox.hardware.Hardware;
import org.anhonesteffort.sciencebox.specific.hardware.Humidifier;
import org.anhonesteffort.sciencebox.specific.hardware.sensor.HumiditySensor;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class HumidityController extends SimpleController {

  private Humidifier humidifier;

  public HumidityController(HumiditySensor humiditySensor, Humidifier humidifier) {
    this.humidifier = humidifier;
    humiditySensor.addListener(this);
  }

  @Override
  protected void handleNewReading() {
    if (super.getLastReading() < super.getTargetReading())
      humidifier.onNewSetting(Hardware.DataType.ON_OFF, 1);
    else
      humidifier.onNewSetting(Hardware.DataType.ON_OFF, 0);
  }

}
