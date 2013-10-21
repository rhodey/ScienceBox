package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.language.Grammar;
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
  public void onDeviceSetting(Grammar.DeviceType deviceType, Grammar.SettingType settingType, double setting_value) {
    System.out.println("Set device " + deviceType + " to " + setting_value + " " + settingType);
  }

  @Override
  public void onControlSetting(Grammar.ControlType controlType, Grammar.SettingType settingType, double setting_value) {
    System.out.println("Set control " + controlType + " to " + setting_value + " " + settingType);
  }

}
