package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.standard.language.Executor;

/**
 * Programmer: rhodey
 * Date: 10/20/13
 */
public class DebugExecutor implements Executor {

  @Override
  public void onExecuteBegin() {
    System.out.println("Interpretation has begun!");
  }

  @Override
  public void onExecuteComplete() {
    System.out.println("Interpretation has completed!");
  }

  @Override
  public void onExecuteHardwareSetting(Hardware.HardwareType hardwareType, Hardware.TypedValue hardwareSetting) {
    System.out.println("Set device " + hardwareType + " to " + hardwareSetting.getValue() + " " + hardwareSetting.getType());
  }

  @Override
  public void onExecuteControlSetting(Sensor.SensorType controlType, Hardware.TypedValue controlSetting) {
    System.out.println("Set control " + controlType + " to " + controlSetting.getValue() + " " + controlSetting.getType());
  }

}
