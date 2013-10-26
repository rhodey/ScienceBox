package org.anhonesteffort.sciencebox.specific.hardware;

import org.anhonesteffort.sciencebox.specific.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.specific.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierCooler extends SerialOnOffDevice {

  public PeltierCooler(ScienceSerialServer io) {
    super(io);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_PELTIER_COOLER;
  }

}