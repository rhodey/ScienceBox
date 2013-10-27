package org.anhonesteffort.sciencebox.standard.language;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.IllegalSettingException;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

/**
 * Programmer: rhodey
 * Date: 10/16/13
 */
public interface Executor {

  public void onExecuteBegin();

  public void onExecuteHardwareSetting(Hardware.HardwareType hardwareType,
                                       Hardware.TypedValue hardwareSetting) throws IllegalSettingException;

  public void onExecuteControlSetting(Sensor.SensorType controlType,
                                      Hardware.TypedValue controlSetting) throws IllegalSettingException;

  public void onExecuteComplete();

}
