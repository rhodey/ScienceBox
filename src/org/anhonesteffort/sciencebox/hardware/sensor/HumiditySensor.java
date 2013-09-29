package org.anhonesteffort.sciencebox.hardware.sensor;

import org.anhonesteffort.sciencebox.serial.ChannelListener;
import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class HumiditySensor implements ChannelListener {

  private ScienceSerialServer io;

  public HumiditySensor(ScienceSerialServer io) {
    this.io = io;
    io.addChannelListener(this);
  }

  public byte getChannel() {
    return ScienceProtocol.SENSOR_CHANNEL_HUMIDIFIER;
  }

  public void onDataReceived(byte[] reading) {
    System.out.println("Current humdity: " + new String(reading));
  }

}
