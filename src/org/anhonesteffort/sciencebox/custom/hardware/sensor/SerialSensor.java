package org.anhonesteffort.sciencebox.custom.hardware.sensor;

import org.anhonesteffort.sciencebox.custom.serial.SerialListener;
import org.anhonesteffort.sciencebox.custom.serial.SerialSource;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.ScienceSensor;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.SensorListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public abstract class SerialSensor implements ScienceSensor, SerialListener {

  protected SerialSource serialSource;
  protected byte[] last_reading = new byte[] {0};
  protected List<SensorListener> sensorListeners = new LinkedList<SensorListener>();

  public SerialSensor(SerialSource serialSource) {
    this.serialSource = serialSource;
    serialSource.addSerialListener(this);
  }

  @Override
  public void addListener(SensorListener listener) {
    sensorListeners.add(listener);
  }

  @Override
  public void removeListener(SensorListener listener) {
    sensorListeners.remove(listener);
  }

  protected abstract void handleReadingChanged();

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
    serialSource.removeSerialListener(this);
  }

}
