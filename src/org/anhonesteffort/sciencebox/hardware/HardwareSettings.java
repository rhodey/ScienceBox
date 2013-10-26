package org.anhonesteffort.sciencebox.hardware;

/**
 * Programmer: rhodey
 * Date: 10/25/13
 */
public interface HardwareSettings {

  public boolean onNewSetting(Hardware.DataType dataType, double setting_value);

  public boolean onNewSettings(Hardware.DataType dataType, double[] setting_values);

}
