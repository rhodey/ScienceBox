package org.anhonesteffort.sciencebox.hardware.passive;

import org.anhonesteffort.sciencebox.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class TemperatureSensor extends SimpleSensor {

  public TemperatureSensor(ScienceSerialServer io) {
    super(io);
  }

  @Override
  public byte getChannel() {
    return ScienceProtocol.SENSOR_CHANNEL_TEMPERATURE;
  }

  @Override
  public void onDataReceived(byte[] new_reading) {
    System.out.println("New temperature: " + new String(new_reading));
    super.onDataReceived(new_reading);
  }

}
