package org.anhonesteffort.sciencebox.hardware;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class OnOffDevice implements HardwareSettings {

  protected void on() {
    // must be implemented in sub-class!.
  }

  protected void off() {
    // must be implemented in sub-class!
  }

  @Override
  public boolean onNewSetting(Hardware.DataType dataType, double setting_value) {
    switch (dataType) {
      case ON_OFF:
        if (setting_value == 1)
          on();
        else if (setting_value == 0)
          off();
        else
          return false;
        break;

      case PERCENT:
        if (setting_value == 100)
          on();
        else if (setting_value == 0)
          off();
        else
          return false;
        break;

      default:
        return false;
    }
    return true;
  }

  @Override
  public boolean onNewSettings(Hardware.DataType dataType, double[] setting_values) {
    return false;
  }

}
