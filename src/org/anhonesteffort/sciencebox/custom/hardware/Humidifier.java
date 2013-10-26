package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialDataSender;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class Humidifier extends SerialOnOffDevice {

  public Humidifier(SerialDataSender serialSender) {
    super(serialSender);
    CHANNEL = ScienceProtocol.CONTROL_CHANNEL_HUMIDIFIER;
  }

}
