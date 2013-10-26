package org.anhonesteffort.sciencebox.custom.hardware;

import org.anhonesteffort.sciencebox.custom.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialDataSender;
import org.anhonesteffort.sciencebox.standard.hardware.OnOffDevice;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class SerialOnOffDevice extends OnOffDevice {

  protected char CHANNEL = '0';
  protected SerialDataSender serialSender;

  public SerialOnOffDevice(SerialDataSender serialSender) {
    this.serialSender = serialSender;
  }

  @Override
  protected void on() {
    serialSender.transmitSerialData(ScienceProtocol.turnOnChannel(CHANNEL));
  }

  @Override
  protected void off() {
    serialSender.transmitSerialData(ScienceProtocol.turnOffChannel(CHANNEL));
  }

}
