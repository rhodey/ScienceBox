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
public class TemperatureSensor extends SerialSensor {

  public TemperatureSensor(SerialServer serialServer) {
    super(serialServer);
  }

  @Override
  public Sensor.SensorType getSensorType() {
    return Sensor.SensorType.TEMPERATURE;
  }

  @Override
  public byte getChannel() {
    return SerialProtocol.SENSOR_CHANNEL_TEMPERATURE;
  }

  @Override
  protected void handleReadingChanged() {
    try {

      System.out.println("New temperature: " + new String(last_reading));
      for(SensorListener listener : sensorListeners)
        listener.onNewReading(Sensor.SensorType.TEMPERATURE,
            new Hardware.TypedValue(Hardware.DataType.CELSIUS, Double.parseDouble(new String(last_reading))));

    } catch (NumberFormatException e) {
      System.out.println("Could not parse double from " + last_reading);
    }
  }

}
