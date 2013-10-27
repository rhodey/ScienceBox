package org.anhonesteffort.sciencebox.standard.hardware;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public abstract class AbstractOnOffDevice implements ScienceHardware {

  protected abstract void on();

  protected abstract void off();

  @Override
  public void onNewSetting(Hardware.TypedValue newSetting) {
    switch (newSetting.getType()) {
      case ON_OFF:
        if (newSetting.getValue() == 1) {
          on();
          return;
        }
        else if (newSetting.getValue() == 0) {
          off();
          return;
        }
        break;

      case PERCENT:
        if (newSetting.getValue() == 100) {
          on();
          return;
        }
        else if (newSetting.getValue() == 0) {
          off();
          return;
        }
        break;
    }

    throw new IllegalArgumentException("I am an AbstractOnOffDevice, you expect me to understand " +
        newSetting.getValue() + newSetting.getType() + "?!");
  }

}
