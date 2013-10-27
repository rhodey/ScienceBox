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

import java.util.LinkedList;
import java.util.List;

public class Main {

  private static void stuff() {

    SerialPort sciencePort = new SerialPort("/dev/ttyACM0");

    try {

      // Implementation specific serial thing.
      //sciencePort.openPort();
      //sciencePort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      SerialServer serialServer = new SerialServer(sciencePort);

      // Implementation specific hardware.
      Fan fan               = new Fan(serialServer);
      PeltierHeater heater  = new PeltierHeater(serialServer);
      PeltierCooler cooler  = new PeltierCooler(serialServer);
      Humidifier humidifier = new Humidifier(serialServer);

      // Implementation specific sensors.
      TemperatureSensor tempSensor  = new TemperatureSensor(serialServer);
      HumiditySensor humiditySensor = new HumiditySensor(serialServer);

      // Implementation specific controllers.
      TemperatureControl tempController = new TemperatureControl(tempSensor, cooler, heater);
      HumidityControl humidityController = new HumidityControl(humiditySensor, humidifier);

      // Implementation agnostic hardware.
      List<ScienceHardware> availableHardware = new LinkedList<ScienceHardware>();
      availableHardware.add(fan);
      availableHardware.add(heater);
      availableHardware.add(cooler);
      availableHardware.add(humidifier);

      // Implementation agnostic controllers.
      List<EnvironmentControl> environmentControls = new LinkedList<EnvironmentControl>();
      environmentControls.add(tempController);
      environmentControls.add(humidityController);


      // Implementation agnostic... everything else.
      ControlledEnvironment scienceBox = new ControlledEnvironment(new LinkedList<ScienceHardware>(),
                                                                   new LinkedList<EnvironmentControl>());
      Thread terminalThread = new Thread(new Terminal(scienceBox));
      terminalThread.start();

    } catch (SerialPortException e) {
      System.out.println("Serial port is mad: " + e);
    }
  }

  public static void main(String[] args) {

    stuff();

  }

}
