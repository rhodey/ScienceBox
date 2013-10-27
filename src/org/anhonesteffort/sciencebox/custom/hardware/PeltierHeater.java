package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.SerialProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialSender;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierHeater extends AbstractSerialOnOffDevice {

  public PeltierHeater(SerialSender serialSender) {
    super(serialSender);
    super.CHANNEL = SerialProtocol.CONTROL_CHANNEL_PELTIER_HEATER;
  }

  @Override
  public Hardware.HardwareType getHardwareType() {
    return Hardware.HardwareType.HEATER;
  }

}
