package org.anhonesteffort.sciencebox.specific.hardware;

import org.anhonesteffort.sciencebox.hardware.OnOffDevice;
import org.anhonesteffort.sciencebox.specific.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.specific.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class SerialOnOffDevice extends OnOffDevice {

  protected char CHANNEL = '0';
  protected ScienceSerialServer io;

  public SerialOnOffDevice(ScienceSerialServer io) {
    this.io = io;
  }

  @Override
  protected void on() {
    io.transmitData(ScienceProtocol.turnOnChannel(CHANNEL));
  }

  @Override
  protected void off() {
    io.transmitData(ScienceProtocol.turnOffChannel(CHANNEL));
  }

}
