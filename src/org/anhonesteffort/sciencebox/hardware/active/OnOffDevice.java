package org.anhonesteffort.sciencebox.hardware.active;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class OnOffDevice {

  protected byte CHANNEL = 0x00;

  private ScienceSerialServer io;

  public OnOffDevice(ScienceSerialServer io) {
    this.io = io;
  }

  public void on() {
    io.transmitData(ScienceProtocol.turnOnChannel(CHANNEL));
  }

  public void off() {
    io.transmitData(ScienceProtocol.turnOffChannel(CHANNEL));
  }

}
