package org.anhonesteffort.sciencebox.custom.hardware.sensor;

import org.anhonesteffort.sciencebox.custom.serial.SerialProtocol;
import org.anhonesteffort.sciencebox.custom.serial.SerialServer;
import org.anhonesteffort.sciencebox.standard.hardware.Hardware;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.Sensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.SensorListener;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class HumiditySensor extends SerialSensor {

  public HumiditySensor(SerialServer serialServer) {
    super(serialServer);
  }

  @Override
  public Sensor.SensorType getSensorType() {
    return Sensor.SensorType.HUMIDITY;
  }

  @Override
  public byte getChannel() {
    return SerialProtocol.SENSOR_CHANNEL_HUMIDIFIER;
  }

  @Override
  protected void handleReadingChanged() {
    try {

      System.out.println("New humidity: " + new String(last_reading));
      for(SensorListener listener : sensorListeners)
        listener.onNewReading(Sensor.SensorType.HUMIDITY,
            new Hardware.TypedValue(Hardware.DataType.PERCENT, Double.parseDouble(new String(last_reading))));

    } catch (Exception e) {
      System.out.println("Could not parse double from " + last_reading);
    }
  }

}
