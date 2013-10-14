package org.anhonesteffort.sciencebox.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class ScienceSerialServer implements SerialPortEventListener {

  private SerialPort serialPort;
  private List<Byte> receivedData = new LinkedList<Byte>();
  private List<ChannelListener> channelListeners = new LinkedList<ChannelListener>();

  public ScienceSerialServer(SerialPort serialPort) throws SerialPortException {
    this.serialPort = serialPort;
    serialPort.addEventListener(this);
  }

  private void callDataListeners() {
    int channel_number = 0;
    List<Byte> channelData = new LinkedList<Byte>();
    byte[] channel_data;

    for(int i = 0; i < receivedData.size(); i++) {
      if(receivedData.get(i).byteValue() == ScienceProtocol.SENSOR_READ_SEPARATOR || i == (receivedData.size() - 1)) {
        channel_data = new byte[channelData.size()];
        for(int c = 0; c < channelData.size(); c++)
          channel_data[c] = channelData.get(c);

        for(ChannelListener listener : channelListeners) {
          if(listener.getChannel() == channel_number)
            listener.onDataReceived(channel_data);
        }
        channelData.clear();
        channel_number++;
      }
      else
        channelData.add(receivedData.get(i).byteValue());
    }
  }

  public void serialEvent(SerialPortEvent serialPortEvent) {
    byte[] bytes;

    if(serialPortEvent.isRXCHAR()) {
      try {
        bytes = serialPort.readBytes();
        for(int i = 0; i < bytes.length; i++) {
          if(bytes[i] == ScienceProtocol.SENSOR_READ_END) {
            callDataListeners();
            receivedData = new LinkedList<Byte>();
          }
          else
            receivedData.add(bytes[i]);
        }
      }
      catch (SerialPortException e) {
        System.out.println("Error reading bytes from port! " + e);
      }
    }
  }

  public void addChannelListener(ChannelListener listener) {
    channelListeners.add(listener);
  }

  public void removeChannelListener(ChannelListener listener) {
    channelListeners.remove(listener);
  }

  public void transmitData(byte[] data) {
    System.out.println("Data transmitted: " + new String(data));

    try {
      serialPort.writeBytes(data);
    } catch (SerialPortException e) {
      System.out.println("Data transmit error, closing!");
      close();
    }
  }

  public void close() {
    try {
      serialPort.removeEventListener();
      serialPort.closePort();
    } catch (SerialPortException e) {
      System.out.println("Failed to close serial port!");
    }
  }

}

