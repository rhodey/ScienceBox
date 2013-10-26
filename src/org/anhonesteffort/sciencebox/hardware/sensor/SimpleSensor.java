package org.anhonesteffort.sciencebox.hardware.sensor;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class SimpleSensor implements RawDataListener {

  protected byte[] last_reading = new byte[] {0};
  protected List<SensorListener> sensorListeners = new LinkedList<SensorListener>();

  public void addListener(SensorListener listener) {
    sensorListeners.add(listener);
  }

  public void removeListener(SensorListener listener) {
    sensorListeners.remove(listener);
  }

  protected void handleReadingChanged() {
    // must be implemented in sub-class!
  }

  @Override
  public void onRawDataReceived(byte[] bytes) {
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

}
