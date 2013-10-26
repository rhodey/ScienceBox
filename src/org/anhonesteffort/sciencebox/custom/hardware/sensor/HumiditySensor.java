package org.anhonesteffort.sciencebox.custom.hardware.sensor;

import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.SensorListener;
import org.anhonesteffort.sciencebox.custom.serial.ScienceProtocol;
import org.anhonesteffort.sciencebox.custom.serial.ScienceSerialServer;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class HumiditySensor extends SerialSensor {

  public HumiditySensor(ScienceSerialServer serialServer) {
    super(serialServer);
  }

  @Override
  public byte getChannel() {
    return ScienceProtocol.SENSOR_CHANNEL_HUMIDIFIER;
  }

  @Override
  protected void handleReadingChanged() {
    try {

      System.out.println("New humidity: " + new String(last_reading));
      for(SensorListener listener : sensorListeners)
        listener.onNewReading(Sensor.SensorType.HUMIDITY, Hardware.DataType.PERCENT, Double.parseDouble(new String(last_reading)));

    } catch (Exception e) {
      System.out.println("Could not parse double from " + last_reading);
    }
  }

}
