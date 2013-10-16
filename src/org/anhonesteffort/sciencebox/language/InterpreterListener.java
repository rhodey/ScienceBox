package org.anhonesteffort.sciencebox.language;

/**
 * Programmer: rhodey
 * Date: 10/16/13
 */
public interface InterpreterListener {

  public void onInterpretBegin();

  public void onInterpretNextLine(int line_number);

  public void onInterpretComplete();

  public void onDeviceSetting(Grammar.DeviceType deviceType, Grammar.SettingType settingType, double setting_value);

  public void onControlSetting(Grammar.ControlType controlType, Grammar.SettingType settingType, double setting_value);

}
