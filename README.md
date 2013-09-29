ScienceBox
=================

ScienceBox is a box for doing science.

Architecture
------------------------

ScienceBox is built around the RaspberryPi, however the
relays we found switch with 5V (and the pi @ 3.3v) so we
had to add an Arduino into the mix in lue of a custom PCB.
The Arduino is only used for basic DIO and is controllable
over a simple serial protocol.  
  
The hardware components consist of a blower (fan), peltier
heater, peltier cooler, ultrasonic humidifier, temperature
sensor and humidity sensor. All the active hardware components are powered by a repurposed
ATX power supply.  
  
Eventually the Arduino will be removed from the system
entirely in favor of a simple PCB.

Control Program
------------------------

ScienceBox is controllable from a simple REST API. Current
functionality includes the ability to set target temperature
and humidity values and have ScienceBox manipulate the
hardware components to achieve the desired state.  
  
Because we're limited to switching power with relays, no
fancy PID control is possible :(