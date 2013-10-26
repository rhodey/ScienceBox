package org.anhonesteffort.sciencebox.hardware.active;

import org.anhonesteffort.sciencebox.language.Grammar;

/**
 * Programmer: rhodey
 * Date: 10/25/13
 */
public interface HardwareSettings {

  public boolean onNewSetting(Grammar.SettingType settingType, double setting_value);

}
