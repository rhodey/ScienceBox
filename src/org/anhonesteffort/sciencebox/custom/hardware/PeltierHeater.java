package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialDataSender;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierHeater extends SerialOnOffDevice {

  public PeltierHeater(SerialDataSender serialSender) {
    super(serialSender);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_PELTIER_HEATER;
  }

}
