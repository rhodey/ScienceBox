package org.anhonesteffort.sciencebox.hardware.sensor;

import org.anhonesteffort.sciencebox.serial.ChannelListener;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 9/29/13
 */
public class SimpleSensor implements ChannelListener {

  private byte[] last_reading;
  private long reading_count = 0;
  private List<SensorListener> sensorListeners = new LinkedList<SensorListener>();

  public SimpleSensor(ScienceSerialServer io) {
    io.addChannelListener(this);
  }

  public byte getChannel() {
    return 0x00;
  }

  public void addListener(SensorListener listener) {
    sensorListeners.add(listener);
  }

  public void removeListener(SensorListener listener) {
    sensorListeners.remove(listener);
  }

  private void handleReadingChanged() {
    for(SensorListener listener : sensorListeners)
      listener.onReadingChanged(last_reading);
  }

  public void onDataReceived(byte[] new_reading) {
    reading_count++;
    boolean are_equal = true;

    if(last_reading.length != new_reading.length)
      are_equal = false;
    else {
      for(int i = 0; i < last_reading.length; i++) {
        if (last_reading[i] != new_reading[i])
          are_equal = false;
      }
    }

    if (!are_equal) {
      last_reading = new_reading;
      handleReadingChanged();
    }
  }

  public long getReadingCount() {
    return reading_count;
  }

}
