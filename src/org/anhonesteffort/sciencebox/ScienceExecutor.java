package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.hardware.Hardware;
import org.anhonesteffort.sciencebox.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.language.InterpreterListener;

/**
 * Programmer: rhodey
 * Date: 10/20/13
 */
public class ScienceExecutor implements InterpreterListener {

  @Override
  public void onInterpretBegin() {
    System.out.println("Interpretation has begun!");
  }

  @Override
  public void onInterpretNextLine(int line_number) {
    System.out.println("Interpreter is now interpreting line #" + line_number);
  }

  @Override
  public void onInterpretComplete() {
    System.out.println("Interpretation has completed!");
  }

  @Override
  public void onHardwareSetting(Hardware.HardwareType hardwareType, Hardware.DataType dataType, double setting_value) {
    System.out.println("Set device " + hardwareType + " to " + setting_value + " " + dataType);
  }

  @Override
  public void onControlSetting(Sensor.SensorType sensorType, Hardware.DataType dataType, double setting_value) {
    System.out.println("Set control " + sensorType + " to " + setting_value + " " + dataType);
  }

}
