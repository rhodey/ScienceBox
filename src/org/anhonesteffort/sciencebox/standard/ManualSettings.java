package org.anhonesteffort.sciencebox.standard;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.IllegalSettingException;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface ManualSettings {

  public void manualOverride();

  public void onManualHardwareSetting(Hardware.HardwareType hardwareType,
                                      Hardware.TypedValue hardwareSetting) throws IllegalSettingException;

  public void onManualControlSetting(Sensor.SensorType controlType,
                                     Hardware.TypedValue controlSetting) throws IllegalSettingException;

}
