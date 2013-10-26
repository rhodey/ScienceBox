package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialDataSender;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierCooler extends SerialOnOffDevice {

  public PeltierCooler(SerialDataSender serialSender) {
    super(serialSender);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_PELTIER_COOLER;
  }

}