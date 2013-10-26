package org.anhonesteffort.sciencebox.custom.serial;

/**
 * Programmer: rhodey
 * Date: 10/26/13
 */
public interface SerialDataSource {

  public void addSerialDataListener(SerialDataListener listener);

  public void removeSerialDataListener(SerialDataListener listener);

}