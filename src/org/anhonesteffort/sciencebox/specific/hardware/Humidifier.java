package org.anhonesteffort.sciencebox.specific.hardware;

import org.anhonesteffort.sciencebox.specific.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.specific.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class Humidifier extends SerialOnOffDevice {

  public Humidifier(ScienceSerialServer io) {
    super(io);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_HUMIDIFIER;
  }

}
