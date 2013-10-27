package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.SerialProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialSender;
import org.anhonesteffort.sciencebox.standard.hardware.AbstractOnOffDevice;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public abstract class AbstractSerialOnOffDevice extends AbstractOnOffDevice {

  protected char CHANNEL = '0';
  private SerialSender serialSender;

  public AbstractSerialOnOffDevice(SerialSender serialSender) {
    this.serialSender = serialSender;
  }

  @Override
  protected void on() {
    serialSender.sendSerialData(SerialProtocol.turnOnChannel(CHANNEL));
  }

  @Override
  protected void off() {
    serialSender.sendSerialData(SerialProtocol.turnOffChannel(CHANNEL));
  }

}
