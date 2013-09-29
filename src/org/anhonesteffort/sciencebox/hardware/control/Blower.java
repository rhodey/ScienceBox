package org.anhonesteffort.sciencebox.hardware.control;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class Blower extends OnOffDevice {

  public Blower(ScienceSerialServer io) {
    super(io);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_BLOWER;
  }

}