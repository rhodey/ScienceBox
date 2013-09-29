package org.anhonesteffort.sciencebox.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.ArrayList;

/**
 * Programmer: rhodey
 * Date: 9/28/13
 */
public class ScienceSerialServer implements SerialPortEventListener {

  private SerialPort serialPort;
  private byte receiveChannel = 0x00;
  private ArrayList<Byte> receivedData = new ArrayList<Byte>();
  private ArrayList<ChannelListener> channelListeners = new ArrayList<ChannelListener>();

  private enum DataState {READ_HUMIDITY, READ_TEMPERATURE};
  private DataState state = DataState.READ_HUMIDITY;

  public ScienceSerialServer(SerialPort serialPort) throws SerialPortException {
    this.serialPort = serialPort;
    serialPort.addEventListener(this);
  }

  private void callDataListeners() {
    byte[] received_data = new byte[receivedData.size()];

    for(int i = 0; i < received_data.length; i++)
      received_data[i] = receivedData.get(i).byteValue();

    for(ChannelListener listener : channelListeners) {
      if(listener.getChannel() == receiveChannel)
        listener.onDataReceived(received_data);
    }
  }

  public void serialEvent(SerialPortEvent serialPortEvent) {
    byte[] bytes;

    if(serialPortEvent.isRXCHAR()) {
      try {

        bytes = serialPort.readBytes();
        for(int i = 0; i < bytes.length; i++) {
          switch (state) {

            case READ_HUMIDITY:
              if(bytes[i] == ScienceProtocol.SENSOR_READ_SEPARATOR) {
                callDataListeners();
                state = DataState.READ_TEMPERATURE;
                receiveChannel++;
                receivedData = new ArrayList<Byte>();
                break;
              }

              receivedData.add(bytes[i]);
              break;


            case READ_TEMPERATURE:
              if(bytes[i] == ScienceProtocol.SENSOR_READ_END) {
                state = DataState.READ_HUMIDITY;
                callDataListeners();
                receiveChannel = 0x00;
                receivedData = new ArrayList<Byte>();
                break;
              }

              receivedData.add(bytes[i]);
              break;
          }
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

  public void removeKISSFrameListener(ChannelListener listener) {
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

