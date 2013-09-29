ScienceBox
=================

ScienceBox is a box for doing science.

// hardware parts
  
  // active
    blower
    ultrasound humidifier
    peltier cooler/heater
  
  // sensor
    temperature
    humidity
    beurametric(?) pressure

// software parts

  serial server
    read sensor values
    callbacks on sensor values
    maintains a queue of pending serial writes
    writes things to serial
  
  blower control
    turn blower on
    turn blower off
  
  peltier control
    heat dat plate
    cool dat plate
    let it be, man
  
  temperature sensor
    last temperature with timestamp
    running average
  
  humidity sensor
    last humidity reading with timestamp
    running average