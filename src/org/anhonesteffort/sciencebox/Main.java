package org.anhonesteffort.sciencebox;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.anhonesteffort.sciencebox.hardware.active.Blower;
import org.anhonesteffort.sciencebox.hardware.active.Humidifier;
import org.anhonesteffort.sciencebox.hardware.active.PeltierCooler;
import org.anhonesteffort.sciencebox.hardware.active.PeltierHeater;
import org.anhonesteffort.sciencebox.hardware.passive.HumiditySensor;
import org.anhonesteffort.sciencebox.hardware.passive.TemperatureSensor;
import org.anhonesteffort.sciencebox.language.Interpreter;
import org.anhonesteffort.sciencebox.language.Parser;
import org.anhonesteffort.sciencebox.serial.ScienceSerialServer;
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
      Blower blower = new Blower(scienceSerialServer);

      // Controllers
      TemperatureController tempController = new TemperatureController(tempSensor, cooler, heater);
      HumidityController humidityController = new HumidityController(humiditySensor, humidifier);

      // Http control server.
      Server server = new Server(8080);
      server.setHandler(new ScienceHttpControl(tempController, humidityController, blower));
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
