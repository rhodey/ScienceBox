package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.SerialProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialSender;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class Humidifier extends AbstractSerialOnOffDevice {

  public Humidifier(SerialSender serialSender) {
    super(serialSender);
    super.CHANNEL = SerialProtocol.CONTROL_CHANNEL_HUMIDIFIER;
  }

  @Override
  public Hardware.HardwareType getHardwareType() {
    return Hardware.HardwareType.HUMIDIFIER;
  }

}
