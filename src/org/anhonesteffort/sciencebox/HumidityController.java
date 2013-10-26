package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.active.Humidifier;
import org.anhonesteffort.sciencebox.hardware.passive.HumiditySensor;
import org.anhonesteffort.sciencebox.hardware.passive.SensorListener;
import org.anhonesteffort.sciencebox.language.Grammar;

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
    if (last_humidity < target_humidity)
      humidifier.onNewSetting(Grammar.SettingType.ON_OFF, 1);
    else
      humidifier.onNewSetting(Grammar.SettingType.ON_OFF, 0);
  }

  public double getLastReading() {
    return last_humidity;
  }

  public double getTarget() {
    return target_humidity;
  }

  public void setTarget(double target_humidity) {
    this.target_humidity = target_humidity;
    handleHumidityChange();
  }

  @Override
  public void onNewReading(byte[] bytes) {
    try {

      last_humidity = Double.parseDouble(new String(bytes));
      handleHumidityChange();

    } catch (Exception e) {
      System.out.println("Could not parse double from " + bytes);
    }
  }

}
