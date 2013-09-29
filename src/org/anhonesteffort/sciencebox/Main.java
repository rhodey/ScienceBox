package org.anhonesteffort.sciencebox;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.anhonesteffort.sciencebox.hardware.active.Humidifier;
import org.anhonesteffort.sciencebox.hardware.active.PeltierCooler;
import org.anhonesteffort.sciencebox.hardware.active.PeltierHeater;
import org.anhonesteffort.sciencebox.hardware.sensor.HumiditySensor;
import org.anhonesteffort.sciencebox.hardware.sensor.TemperatureSensor;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;
import org.eclipse.jetty.server.Server;

public class Main {

  public static void main(String[] args) {

    SerialPort sciencePort = new SerialPort("/dev/ttyACM0");

    try {

      // Http application server.
      Server server = new Server(8080);
      server.setHandler(new ScienceHttpHandler());
      server.join();
      server.start();

      // Serial port and server thing.
      sciencePort.openPort();
      sciencePort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      ScienceSerialServer scienceSerialServer = new ScienceSerialServer(sciencePort);

      // Sensors.
      TemperatureSensor tempSensor = new TemperatureSensor(scienceSerialServer);
      HumiditySensor humiditySensor = new HumiditySensor(scienceSerialServer);

      // Active hardware.
      PeltierCooler cooler = new PeltierCooler(scienceSerialServer);
      PeltierHeater heater = new PeltierHeater(scienceSerialServer);
      Humidifier humidifier = new Humidifier(scienceSerialServer);

      // Controllers
      TemperatureController tempController = new TemperatureController(tempSensor, cooler, heater);
      HumidityController humidityController = new HumidityController(humiditySensor, humidifier);

      tempController.setTarget(80.0);
      humidityController.setTarget(20.0);

      //scienceSerialServer.close();

    } catch (SerialPortException e) {
      System.out.println("Serial port is mad: " + e);
    } catch (Exception e) {
      System.out.println("Http server is mad: " + e);
    }
  }

}
