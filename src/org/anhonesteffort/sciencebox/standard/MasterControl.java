package org.anhonesteffort.sciencebox.standard;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.ScienceHardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

import java.util.List;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class MasterControl {

  private List<ScienceHardware> availableHardware;
  private List<EnvironmentControl> environmentControls;

  protected MasterControl(List<ScienceHardware> availableHardware, List<EnvironmentControl> environmentControls) {
    this.availableHardware = availableHardware;
    this.environmentControls = environmentControls;
  }

  protected void onHardwareSetting(Hardware.HardwareType hardwareType, Hardware.TypedValue hardwareSetting) {
    boolean found = false;
    for (ScienceHardware hardware : availableHardware) {
      if (hardware.getHardwareType() == hardwareType) {
        found = true;
        hardware.onNewSetting(hardwareSetting);
      }
    }

    if (!found)
      throw new IllegalArgumentException("Error - " + hardwareType + " hardware is not available!");

    System.out.println("Set " + hardwareType + " hardware to " +
        hardwareSetting.getValue() + " " + hardwareSetting.getType());
  }

  protected void onControlSetting(Sensor.SensorType controlType, Hardware.TypedValue controlSetting) {
    boolean found = false;
    for (EnvironmentControl controller : environmentControls) {
      if (controller.getControlType() == controlType) {
        found = true;
        controller.setTargetReading(controlSetting);
      }
    }

    if (!found)
      throw new IllegalArgumentException("Error - " + controlType + " control is not available!");

    System.out.println("Set " + controlType + " control to " +
        controlSetting.getValue() + " " + controlSetting.getType());
  }

}
