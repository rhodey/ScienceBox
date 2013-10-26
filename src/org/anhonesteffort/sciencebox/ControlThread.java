package org.anhonesteffort.sciencebox;

import org.anhonesteffort.sciencebox.standard.control.StandardController;
import org.anhonesteffort.sciencebox.standard.hardware.sensor.StandardSensor;
import org.anhonesteffort.sciencebox.standard.hardware.StandardHardware;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public class ControlThread implements Runnable {

  // Sensors.
  private StandardSensor temperatureSensor;
  private StandardSensor humiditySensor;

  // Active hardware.
  private StandardHardware hardwareCooler;
  private StandardHardware hardwareHeater;
  private StandardHardware hardwareHumidifier;
  private StandardHardware hardwareFan;

  // Controllers
  private StandardController temperatureController;
  private StandardController humidityController;

  public ControlThread() {

  }

  @Override
  public void run() {

  }

}
