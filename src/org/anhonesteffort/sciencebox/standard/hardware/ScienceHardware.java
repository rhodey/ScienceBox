package org.anhonesteffort.sciencebox.standard.hardware;

/**
 * Programmer: rhodey
 * Date: 10/25/13
 */
public interface ScienceHardware {

  public Hardware.HardwareType getHardwareType();

  public void onNewSetting(Hardware.TypedValue newSetting);

}
