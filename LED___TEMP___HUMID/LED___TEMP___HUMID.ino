#include <LiquidCrystal.h>
#include <LCDKeypad.h>
#include "DHT.h" // includes temp and humidity library

#define DHTPIN 2
#define DHTTYPE DHT22   // DHT 22  (AM2302)

#define RELAY0 3
#define RELAY1 13
#define RELAY2 12
#define RELAY3 11

DHT dht(DHTPIN, DHTTYPE);

LiquidCrystal lcd(8, 10, 9, 4, 5, 6, 7);
char msgs[5][16] = {"Right Key OK ",
                    "Up Key OK    ",               
                    "Down Key OK  ",
                    "Left Key OK  ",
                    "Select Key OK" };
int adc_key_val[5] ={50, 200, 400, 600, 800 };
int NUM_KEYS = 5;
int adc_key_in;
int key=-1;
int oldkey=-1;
char inData[20]; // Allocate some space for the string
char inChar; // Where to store the character read
byte index = 0; // Index into array; where to store the character
 
 
 
void setup()
{
  digitalWrite(RELAY0, LOW);
  digitalWrite(RELAY1, LOW);
  digitalWrite(RELAY2, LOW);
  digitalWrite(RELAY3, LOW);
  pinMode(RELAY0, OUTPUT);
  pinMode(RELAY1, OUTPUT);
  pinMode(RELAY2, OUTPUT);
  pinMode(RELAY3, OUTPUT);
    Serial.begin(9600); 
    Serial.println("DHTxx test!");

    dht.begin();

  lcd.begin(16, 2);
  lcd.print("Science Box!"); 
}







void loop()
{
  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  // check if returns are valid, if they are NaN (not a number) then something went wrong!
  if (isnan(t) || isnan(h)) {
    Serial.println("Failed to read from DHT");
  } else {
//    Serial.print("Humidity: "); 
    Serial.print(h);
    Serial.print(",");
  //  Serial.print("Temperature: "); 
    Serial.println(t);
 //   Serial.println(" *C");
    
    
//         ----------------
  lcd.setCursor(0, 2);
  lcd.print(t);
  lcd.print("c");
  
//         ----------------
  lcd.setCursor(10, 2);
  lcd.print(h);
  lcd.print("%");
  }
  
   while(Serial.available() > 0) // Don't read unless
                                                  // there you know there is data
   {
       if(index < 19) // One less than the size of the array
       {
           inChar = Serial.read(); // Read a character
           inData[index] = inChar; // Store it
           index++; // Increment where to write next
           inData[index] = '\0'; // Null terminate the string  
           if (inChar =='\n') {
             doMsg();
             index = 0;
             inData[index] = '\0'; // Null terminate the string
           }
       } else {
           index = 0;
           inData[index] = '\0'; // Null terminate the string
       }
   }

}

void doMsg() {
  if (inData[0] =='0') {
   if (inData[1] =='1') {
      digitalWrite(RELAY0, HIGH);
    } else {
      digitalWrite(RELAY0, LOW);
    }
  }
  if (inData[0] =='1') {
    if (inData[1] =='1') {
      digitalWrite(RELAY1, HIGH);
    } else {
      digitalWrite(RELAY1, LOW);
    }
  }
  if (inData[0] =='2') {
    if (inData[1] =='1') {
      digitalWrite(RELAY2, HIGH);
    } else {
      digitalWrite(RELAY2, LOW);
    }
  }
  if (inData[0] =='3') {
    if (inData[1] =='1') {
      digitalWrite(RELAY3, HIGH);
    } else {
      digitalWrite(RELAY3, LOW);
    }
  }
}




