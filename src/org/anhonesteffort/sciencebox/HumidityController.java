package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.active.Humidifier;
import org.anhonesteffort.sciencebox.hardware.sensor.HumiditySensor;
import org.anhonesteffort.sciencebox.hardware.sensor.SensorListener;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class HumidityController implements SensorListener {

  private static final double MARGIN_PERCENT = 2.0;

  private HumiditySensor humiditySensor;
  private Humidifier humidifier;

  private double target_humidity = 0.0;
  private double last_humidity = 0.0;

  public HumidityController(HumiditySensor humiditySensor, Humidifier humidifier) {
    this.humiditySensor = humiditySensor;
    this.humidifier = humidifier;

    humiditySensor.addListener(this);
  }

  private void handleHumidityChange() {
    if (last_humidity < target_humidity) {
      humidifier.on();
    }
    else {
      humidifier.off();
    }
  }

  public void setTarget(double target_humidity) {
    this.target_humidity = target_humidity;
    handleHumidityChange();
  }

  @Override
  public void onReadingChanged(byte[] new_reading) {
    last_humidity = Double.parseDouble(new String(new_reading));
    handleHumidityChange();
  }

}
