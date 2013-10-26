package org.anhonesteffort.sciencebox.standard.language;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;

/**
 * Programmer: rhodey
 * Date: 10/16/13
 */
public interface InterpreterListener {

  public void onInterpretBegin();

  public void onInterpretNextLine(int line_number);

  public void onInterpretComplete();

  public void onHardwareSetting(Hardware.HardwareType hardwareType, Hardware.DataType dataType, double setting_value);

  public void onControlSetting(Sensor.SensorType sensorType, Hardware.DataType dataType, double setting_value);

}
