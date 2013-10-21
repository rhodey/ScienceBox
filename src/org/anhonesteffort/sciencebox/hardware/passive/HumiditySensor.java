package org.anhonesteffort.sciencebox.hardware.passive;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class HumiditySensor extends SimpleSensor {

  public HumiditySensor(ScienceSerialServer io) {
    super(io);
  }

  @Override
  public byte getChannel() {
    return ScienceProtocol.SENSOR_CHANNEL_HUMIDIFIER;
  }

  @Override
  public void onDataReceived(byte[] new_reading) {
    System.out.println("New humidity: " + new String(new_reading));
    super.onDataReceived(new_reading);
  }

}
