package org.anhonesteffort.sciencebox.hardware.active;

import org.anhonesteffort.sciencebox.language.Grammar;
import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class OnOffDevice implements HardwareSettings {

  protected char CHANNEL = '0';

  private ScienceSerialServer io;

  public OnOffDevice(ScienceSerialServer io) {
    this.io = io;
  }

  protected void on() {
    io.transmitData(ScienceProtocol.turnOnChannel(CHANNEL));
  }

  protected void off() {
    io.transmitData(ScienceProtocol.turnOffChannel(CHANNEL));
  }

  @Override
  public boolean onNewSetting(Grammar.SettingType settingType, double setting_value) {
    switch (settingType) {
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
