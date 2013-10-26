package org.anhonesteffort.sciencebox.standard.hardware;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class OnOffDevice implements StandardHardware {

  protected void on() {
    // should be implemented in sub-class!.
  }

  protected void off() {
    // should be implemented in sub-class!
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

}
