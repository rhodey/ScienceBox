package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.SerialProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialSender;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class PeltierCooler extends AbstractSerialOnOffDevice {

  public PeltierCooler(SerialSender serialSender) {
    super(serialSender);
    super.CHANNEL = SerialProtocol.CONTROL_CHANNEL_PELTIER_COOLER;
  }

  @Override
  public Hardware.HardwareType getHardwareType() {
    return Hardware.HardwareType.COOLER;
  }

}