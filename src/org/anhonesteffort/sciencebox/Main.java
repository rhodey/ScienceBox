package org.anhonesteffort.sciencebox;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.anhonesteffort.sciencebox.custom.control.HumidityController;
import org.anhonesteffort.sciencebox.custom.control.TemperatureController;
import org.anhonesteffort.sciencebox.custom.hardware.Fan;
import org.anhonesteffort.sciencebox.custom.hardware.Humidifier;
import org.anhonesteffort.sciencebox.custom.hardware.sensor.HumiditySensor;
import org.anhonesteffort.sciencebox.custom.hardware.PeltierCooler;
import org.anhonesteffort.sciencebox.custom.hardware.PeltierHeater;
import org.anhonesteffort.sciencebox.standard.language.Interpreter;
import org.anhonesteffort.sciencebox.standard.language.Parser;
import org.anhonesteffort.sciencebox.custom.serial.ScienceSerialServer;
import org.anhonesteffort.sciencebox.custom.hardware.sensor.TemperatureSensor;
import org.eclipse.jetty.server.Server;

import java.io.FileInputStream;

public class Main {

  private static void stuff() {

    SerialPort sciencePort = new SerialPort("/dev/ttyACM0");

    try {

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
      Fan fan = new Fan(scienceSerialServer);

      // Controllers
      TemperatureController tempController = new TemperatureController(tempSensor, cooler, heater);
      HumidityController humidityController = new HumidityController(humiditySensor, humidifier);

      // Http control server.
      Server server = new Server(8080);
      server.setHandler(new ScienceHttpControl(tempController, humidityController, fan));
      server.join();
      server.start();

    } catch (SerialPortException e) {
      System.out.println("Serial port is mad: " + e);
    } catch (Exception e) {
      System.out.println("Http server is mad: " + e);
    }
  }

  private static void otherStuff() {
    try {

      FileInputStream fileIn = new FileInputStream("/home/rhodey/dev/ScienceBox/test.fan");
      Parser fanParse = new Parser(fileIn);
      System.out.println("FanOn script has correct syntax? " + fanParse.isSyntaxCorrect());

      Interpreter fanInterpret = new Interpreter(fanParse);
      ScienceExecutor executor = new ScienceExecutor();
      fanInterpret.addInterpreterListener(executor);
      fanInterpret.run();

    } catch (Exception e) {
      System.out.println("the sky is falling: " + e);
    }
  }

  public static void main(String[] args) {

    //stuff();
    otherStuff();

  }

}
