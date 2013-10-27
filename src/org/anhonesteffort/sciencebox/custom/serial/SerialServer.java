package org.anhonesteffort.sciencebox.custom.serial;

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
public class SerialServer implements SerialPortEventListener, SerialSource, SerialSender {

  private SerialPort serialPort;
  private List<Byte> receivedData = new LinkedList<Byte>();
  private List<SerialListener> serialListeners = new LinkedList<SerialListener>();

  public SerialServer(SerialPort serialPort) throws SerialPortException {
    this.serialPort = serialPort;
    serialPort.addEventListener(this);
  }

  private void callDataListeners() {
    int channel_number = 0;
    List<Byte> channelData = new LinkedList<Byte>();

    for(int i = 0; i < receivedData.size(); i++) {
      if(receivedData.get(i).byteValue() == SerialProtocol.SENSOR_READ_SEPARATOR || i == (receivedData.size() - 1)) {
        byte[] channel_data = new byte[channelData.size()];
        for(int c = 0; c < channelData.size(); c++)
          channel_data[c] = channelData.get(c);

        for(SerialListener listener : serialListeners) {
          if(listener.getChannel() == channel_number)
            listener.onSerialDataReceived(channel_data);
        }
        channelData.clear();
        channel_number++;
      }
      else
        channelData.add(receivedData.get(i).byteValue());
    }
  }

  @Override
  public void serialEvent(SerialPortEvent serialPortEvent) {
    byte[] bytes;

    if(serialPortEvent.isRXCHAR()) {
      try {
        bytes = serialPort.readBytes();
        for(int i = 0; i < bytes.length; i++) {
          if(bytes[i] == SerialProtocol.SENSOR_READ_END) {
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

  @Override
  public void addSerialListener(SerialListener listener) {
    serialListeners.add(listener);
  }

  @Override
  public void removeSerialListener(SerialListener listener) {
    serialListeners.remove(listener);
  }

  @Override
  public void sendSerialData(byte[] bytes) {
    try {
      serialPort.writeBytes(bytes);
      System.out.println("Data transmitted: " + new String(bytes));
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

