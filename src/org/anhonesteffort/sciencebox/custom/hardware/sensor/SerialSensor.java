package org.anhonesteffort.sciencebox.custom.hardware.sensor;

import org.anhonesteffort.sciencebox.custom.serial.SerialDataListener;
import org.anhonesteffort.sciencebox.custom.serial.SerialDataSource;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.SensorListener;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.StandardSensor;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class SerialSensor implements StandardSensor, SerialDataListener {

  protected SerialDataSource serialSource;
  protected byte[] last_reading = new byte[] {0};
  protected List<SensorListener> sensorListeners = new LinkedList<SensorListener>();

  public SerialSensor(SerialDataSource serialSource) {
    this.serialSource = serialSource;
    serialSource.addSerialDataListener(this);
  }

  @Override
  public void addListener(SensorListener listener) {
    sensorListeners.add(listener);
  }

  @Override
  public void removeListener(SensorListener listener) {
    sensorListeners.remove(listener);
  }

  @Override
  public byte getChannel() {
    return 0x00; // should be implemented in sub-class!
  }

  protected void handleReadingChanged() {
    // should be implemented in sub-class!
  }

  @Override
  public void onSerialDataReceived(byte[] bytes) {
    boolean are_equal = true;

    if(last_reading.length != bytes.length)
      are_equal = false;
    else {
      for(int i = 0; i < last_reading.length; i++) {
        if (last_reading[i] != bytes[i])
          are_equal = false;
      }
    }

    if (!are_equal) {
      last_reading = bytes;
      handleReadingChanged();
    }
  }

  public void finish() {
    serialSource.removeSerialDataListener(this);
  }

}
