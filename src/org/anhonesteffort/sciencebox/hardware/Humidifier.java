package org.anhonesteffort.sciencebox.hardware;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class Humidifier {

  private static final byte CHANNEL = 0x03;

  private ScienceSerialServer io;

  public Humidifier(ScienceSerialServer io) {
    this.io = io;
  }

  public void on() {
    io.transmitData(ScienceProtocol.turnOnChannel(CHANNEL));
  }

  public void off() {
    io.transmitData(ScienceProtocol.turnOffChannel(CHANNEL));
  }

}
