package org.anhonesteffort.sciencebox.standard;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.IllegalSettingException;
import org.anhonesteffort.sciencebox.standard.hardware.ScienceHardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.standard.language.Executor;

import java.util.List;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class ControlledEnvironment implements ManualSettings, Executor {

  private MasterControl masterControl = null;
  private boolean am_interpreting = false;

  public ControlledEnvironment(List<ScienceHardware> availableHardware, List<EnvironmentControl> environmentControls) {
    masterControl = new MasterControl(availableHardware, environmentControls);
  }

  @Override
  public void manualOverride() {
    am_interpreting = false;
  }

  @Override
  public void onManualHardwareSetting(Hardware.HardwareType hardwareType,
                                      Hardware.TypedValue hardwareSetting) throws IllegalSettingException {
    if (am_interpreting)
      throw new IllegalSettingException("Interpreter is running, must override.");

    masterControl.onHardwareSetting(hardwareType, hardwareSetting);
  }

  @Override
  public void onManualControlSetting(Sensor.SensorType controlType,
                                     Hardware.TypedValue controlSetting) throws IllegalSettingException {
    if (am_interpreting)
      throw new IllegalSettingException("Interpreter is running, must override.");

    masterControl.onControlSetting(controlType, controlSetting);
  }

  @Override
  public void onExecuteBegin() {
    am_interpreting = true;
  }

  @Override
  public void onExecuteHardwareSetting(Hardware.HardwareType hardwareType,
                                       Hardware.TypedValue hardwareSetting) throws IllegalSettingException {
    if (!am_interpreting)
      throw new IllegalSettingException("Interpreter has been manually overridden.");

    masterControl.onHardwareSetting(hardwareType, hardwareSetting);
  }

  @Override
  public void onExecuteControlSetting(Sensor.SensorType controlType,
                                      Hardware.TypedValue controlSetting) throws IllegalSettingException {
    if (!am_interpreting)
      throw new IllegalSettingException("Interpreter has been manually overridden.");

    masterControl.onControlSetting(controlType, controlSetting);
  }

  @Override
  public void onExecuteComplete() {
    am_interpreting = false;
  }

}
