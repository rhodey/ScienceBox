package org.anhonesteffort.sciencebox;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.anhonesteffort.sciencebox.custom.HumidityControl;
import org.anhonesteffort.sciencebox.custom.TemperatureControl;
import org.anhonesteffort.sciencebox.custom.hardware.Fan;
import org.anhonesteffort.sciencebox.custom.hardware.Humidifier;
import org.anhonesteffort.sciencebox.custom.hardware.PeltierCooler;
import org.anhonesteffort.sciencebox.custom.hardware.PeltierHeater;
import org.anhonesteffort.sciencebox.custom.hardware.sensor.HumiditySensor;
import org.anhonesteffort.sciencebox.custom.hardware.sensor.TemperatureSensor;
import org.anhonesteffort.sciencebox.custom.serial.SerialServer;
import org.anhonesteffort.sciencebox.standard.ControlledEnvironment;
import org.anhonesteffort.sciencebox.standard.EnvironmentControl;
import org.anhonesteffort.sciencebox.standard.Terminal;
import org.anhonesteffort.sciencebox.standard.hardware.ScienceHardware;
import org.anhonesteffort.sciencebox.standard.language.Interpreter;
import org.anhonesteffort.sciencebox.standard.language.Parser;
import org.eclipse.jetty.server.Server;

import java.io.FileInputStream;
import java.util.LinkedList;

public class Main {

  private static void stuff() {

    SerialPort sciencePort = new SerialPort("/dev/ttyACM0");

    try {

      // Serial port and server thing.
      sciencePort.openPort();
      sciencePort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      SerialServer scienceSerialServer = new SerialServer(sciencePort);

      // Sensors.
      TemperatureSensor tempSensor = new TemperatureSensor(scienceSerialServer);
      HumiditySensor humiditySensor = new HumiditySensor(scienceSerialServer);

      // Active hardware.
      PeltierCooler cooler = new PeltierCooler(scienceSerialServer);
      PeltierHeater heater = new PeltierHeater(scienceSerialServer);
      Humidifier humidifier = new Humidifier(scienceSerialServer);
      Fan fan = new Fan(scienceSerialServer);

      // Controllers
      TemperatureControl tempController = new TemperatureControl(tempSensor, cooler, heater);
      HumidityControl humidityController = new HumidityControl(humiditySensor, humidifier);

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
      DebugExecutor executor = new DebugExecutor();
      fanInterpret.addListener(executor);
      //fanInterpret.run();

    } catch (Exception e) {
      System.out.println("the sky is falling: " + e);
    }
  }

  public static void main(String[] args) {

    //stuff();
    otherStuff();

    ControlledEnvironment scienceBox = new ControlledEnvironment(new LinkedList<ScienceHardware>(),
                                                                 new LinkedList<EnvironmentControl>());

    Terminal scienceTerminal = new Terminal(scienceBox);
    Thread terminalThread = new Thread(scienceTerminal);
    terminalThread.start();
  }

}
