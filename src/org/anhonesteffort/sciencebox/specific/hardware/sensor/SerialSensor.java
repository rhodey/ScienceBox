package org.anhonesteffort.sciencebox.specific.hardware.sensor;

import org.anhonesteffort.sciencebox.hardware.sensor.SimpleSensor;
import org.anhonesteffort.sciencebox.specific.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class SerialSensor extends SimpleSensor implements SerialDataListener {

  protected ScienceSerialServer serialServer;

  public SerialSensor(ScienceSerialServer serialServer) {
    this.serialServer = serialServer;
    serialServer.addSerialDataListener(this);
  }

  @Override
  public byte getChannel() {
    return 0x00;
  }

  @Override
  protected void handleReadingChanged() {
    // must be implemented in sub-class!
  }

  public void finish() {
    serialServer.removeSerialDataListener(this);
  }

}
