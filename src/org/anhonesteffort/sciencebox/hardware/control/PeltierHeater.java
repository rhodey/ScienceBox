package org.anhonesteffort.sciencebox.hardware.control;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierHeater extends OnOffDevice {

  public PeltierHeater(ScienceSerialServer io) {
    super(io);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_PELTIER_HEATER;
  }

}
