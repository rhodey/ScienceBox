package org.anhonesteffort.sciencebox.custom.serial;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface SerialSource {

  public void addSerialListener(SerialListener listener);

  public void removeSerialListener(SerialListener listener);

}