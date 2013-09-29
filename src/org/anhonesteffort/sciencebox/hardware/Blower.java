package org.anhonesteffort.sciencebox.hardware;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class Blower {

  private static final byte CHANNEL = 0x00;

  private ScienceSerialServer io;

  public Blower(ScienceSerialServer io) {
    this.io = io;
  }

  public void on() {
    io.transmitData(ScienceProtocol.turnOnChannel(CHANNEL));
  }

  public void off() {
    io.transmitData(ScienceProtocol.turnOffChannel(CHANNEL));
  }

}
