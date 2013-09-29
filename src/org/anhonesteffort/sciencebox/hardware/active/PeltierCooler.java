package org.anhonesteffort.sciencebox.hardware.active;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierCooler extends OnOffDevice {

  public PeltierCooler(ScienceSerialServer io) {
    super(io);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_PELTIER_COOLER;
  }

}