package org.anhonesteffort.sciencebox;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.anhonesteffort.sciencebox.hardware.control.Blower;
import org.anhonesteffort.sciencebox.hardware.control.Humidifier;
import org.anhonesteffort.sciencebox.hardware.control.PeltierCooler;
import org.anhonesteffort.sciencebox.hardware.control.PeltierHeater;
import org.anhonesteffort.sciencebox.hardware.sensor.HumiditySensor;
import org.anhonesteffort.sciencebox.hardware.sensor.TemperatureSensor;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;

public class Main {

  public static void main(String[] args) {

    SerialPort sciencePort = new SerialPort("/dev/ttyACM0");

    try {

      // Serial port and server thing.
      sciencePort.openPort();
      sciencePort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      ScienceSerialServer scienceSerialServer = new ScienceSerialServer(sciencePort);

      // Sensors.
      TemperatureSensor scienceTemperature = new TemperatureSensor(scienceSerialServer);
      HumiditySensor humiditySensor = new HumiditySensor(scienceSerialServer);

      // Active hardware.
      Blower scienceBlower = new Blower(scienceSerialServer);
      scienceBlower.on();
      Humidifier scienceHumidifier = new Humidifier(scienceSerialServer);
      scienceHumidifier.on();
      PeltierCooler scienceCooler = new PeltierCooler(scienceSerialServer);
      scienceCooler.on();
      PeltierHeater scienceHeater = new PeltierHeater(scienceSerialServer);
      scienceHeater.on();

      //scienceSerialServer.close();

    } catch (SerialPortException e) {
      System.out.println("Serial port is mad: " + e);
    }
  }

}
