

//-----------------------------------------------------------------------------------------------------------//
//                                                                                                           //
//  Slave_ELEC1601_Student_2019_v3                                                                           //
//  The Instructor version of this code is identical to this version EXCEPT it also sets PIN codes           //
//  20191008 Peter Jones                                                                                     //
//                                                                                                           //
//  Bi-directional passing of serial inputs via Bluetooth                                                    //
//  Note: the void loop() contents differ from "capitalise and return" code                                  //
//                                                                                                           //
//  This version was initially based on the 2011 Steve Chang code but has been substantially revised         //
//  and heavily documented throughout.                                                                       //
//                                                                                                           //
//  20190927 Ross Hutton                                                                                     //
//  Identified that opening the Arduino IDE Serial Monitor asserts a DTR signal which resets the Arduino,    //
//  causing it to re-execute the full connection setup routine. If this reset happens on the Slave system,   //
//  re-running the setup routine appears to drop the connection. The Master is unaware of this loss and      //
//  makes no attempt to re-connect. Code has been added to check if the Bluetooth connection remains         //
//  established and, if so, the setup process is bypassed.                                                   //
//                                                                                                           //
//-----------------------------------------------------------------------------------------------------------//

#include <SoftwareSerial.h>   //Software Serial Port

#define RxD 7
#define TxD 6
#define ConnStatus A1

#define DEBUG_ENABLED  1

// ##################################################################################
// ### EDIT THE LINES BELOW TO MATCH YOUR SHIELD NUMBER AND CONNECTION PIN OPTION ###
// ##################################################################################

int shieldPairNumber = 7;

// CAUTION: If ConnStatusSupported = true you MUST NOT use pin A1 otherwise "random" reboots will occur
// CAUTION: If ConnStatusSupported = true you MUST set the PIO[1] switch to A1 (not NC)

boolean ConnStatusSupported = true;   // Set to "true" when digital connection status is available on Arduino pin

// #######################################################

// The following two string variable are used to simplify adaptation of code to different shield pairs

String slaveNameCmd = "\r\n+STNA=Slave";   // This is concatenated with shieldPairNumber later

char pos;
int i = 0;
char movementsMade[0] = {};
char invertedMovements[0] = {};
#include <Servo.h>    
Servo servoRight;  
Servo servoLeft; 


SoftwareSerial blueToothSerial(RxD,TxD);


void setup()
{
    Serial.begin(9600);
    blueToothSerial.begin(38400);                    // Set Bluetooth module to default baud rate 38400

    

    pinMode(RxD, INPUT);
    pinMode(TxD, OUTPUT);
    pinMode(ConnStatus, INPUT); 
    //pinMode(A0, INPUT);  pinMode(9, OUTPUT);
    //pinMode(A1, INPUT);  pinMode(2, OUTPUT);
    pinMode(10, INPUT);  pinMode(9, OUTPUT); // Use Analog Pins
    pinMode(3, INPUT);  pinMode(2, OUTPUT);  
   

    //  Check whether Master and Slave are already connected by polling the ConnStatus pin (A1 on SeeedStudio v1 shield)
    //  This prevents running the full connection setup routine if not necessary.

    if(ConnStatusSupported) Serial.println("Checking Slave-Master connection status.");

    if(ConnStatusSupported && digitalRead(ConnStatus)==1)
    {
        Serial.println("Already connected to Master - remove USB cable if reboot of Master Bluetooth required.");
    }
    else
    {
        Serial.println("Not connected to Master.");
        
        setupBlueToothConnection();   // Set up the local (slave) Bluetooth module

        delay(1000);                  // Wait one second and flush the serial buffers
        Serial.flush();
        blueToothSerial.flush();
    }
}



int irDetect(int irLedPin, int irReceiverPin, long frequency)
{
  tone(irLedPin, frequency, 8);              // IRLED 38 kHz for at least 1 ms
  delay(1);                                  // Wait 1 ms
  int ir = digitalRead(irReceiverPin);       // IR receiver -> ir variable
  delay(1);                                  // Down time before recheck
  return ir;                                 // Return 1 no detect, 0 detect
}
void moveForward() {

  servoLeft.writeMicroseconds(2000);  // Left wheel counterclockwise
  servoRight.writeMicroseconds(1000); // Right wheel clockwise
  delay(50);                          // Maneuver for time ms
}

void rotateRight() {

//Rotate Right by a little bit
  Serial.println("hhhh");
  servoLeft.writeMicroseconds(1000);  // Left wheel counterclockwise
  servoRight.writeMicroseconds(1500); // stop right wheel
  delay(10);                          // Maneuver for time ms

}

void rotateLeft() {

//Rotate Left by a little bit
  servoLeft.writeMicroseconds(1500);  // stop left wheel
  servoRight.writeMicroseconds(1000); // Right wheel clockwise
  delay(10);                          // Maneuver for time ms

}
void movebackward() {

//Rotate Left by a little bit
 servoLeft.writeMicroseconds(1000);  // Left wheel clockwise
 servoRight.writeMicroseconds(2000); // Right wheel cclockwise
  delay(10);                          // Maneuver for time ms

}

void stop() {

//Rotate Left by a little bit
 servoLeft.writeMicroseconds(1540);  // Left wheel clockwise
 servoRight.writeMicroseconds(1540); // Right wheel cclockwise
  delay(10);                          // Maneuver for time ms

}


void loop()
{
    
    char recvChar;

   
        if(blueToothSerial.available())   // Check if there's any data sent from the remote Bluetooth shield
        {
            pos = blueToothSerial.read();
            Serial.print(pos);
        }

        if(Serial.available())            // Check if there's any data sent from the local serial terminal. You can add the other applications here.
        {
            pos  = Serial.read();
            Serial.print(recvChar);
            blueToothSerial.print(pos);
        }

     if (pos == 'w'){

          moveForward();
      }else if (pos == 'd'){ 
          
          rotateRight();
      }else if (pos == 'a'){
        
          rotateLeft();
      }else if (pos == 's'){
        
          movebackward();
      } else if (pos == 'm'){
          stop();
      }
    
    
    if (pos == 'x'){
      servoLeft.attach(13);                      // Attach left signal to pin 13
      servoRight.attach(12); 
      delay(1000);
      int i = 5;
      while (i > 0){
        delay(100);
        if (pos == 'x'){
          break;
        }
        else {
     int irLeft = irDetect(2, 3, 38000);
     int irRight = irDetect(9, 10, 38000);
     //int irLeft = irDetect(2, A1, 38000);
     //int irRight = irDetect(9, A0, 38000);
  
     Serial.println("L" + irLeft); 
     Serial.println("R" + irRight); 
     delay(100);
     
    if(irLeft == 0 and irRight == 0) {       // Controls movement 
      
      moveForward();
    }
    else if(irRight == 0 and irLeft == 1) {

      rotateLeft();
    }
    else if (irRight == 1 and irLeft == 0){

      rotateRight();
    }else if (irRight == 1 and irLeft == 1){
      stop();
    }

    delay(100);   // 0.1 second delay
    }
    }
    }
  
      
      
      
    
  
}
  

void setupBlueToothConnection()
{
    Serial.println("Setting up the local (slave) Bluetooth module.");

    slaveNameCmd += shieldPairNumber;
    slaveNameCmd += "\r\n";

    blueToothSerial.print("\r\n+STWMOD=0\r\n");      // Set the Bluetooth to work in slave mode
    blueToothSerial.print(slaveNameCmd);             // Set the Bluetooth name using slaveNameCmd
    blueToothSerial.print("\r\n+STAUTO=0\r\n");      // Auto-connection should be forbidden here
    blueToothSerial.print("\r\n+STOAUT=1\r\n");      // Permit paired device to connect me
    
    //  print() sets up a transmit/outgoing buffer for the string which is then transmitted via interrupts one character at a time.
    //  This allows the program to keep running, with the transmitting happening in the background.
    //  Serial.flush() does not empty this buffer, instead it pauses the program until all Serial.print()ing is done.
    //  This is useful if there is critical timing mixed in with Serial.print()s.
    //  To clear an "incoming" serial buffer, use while(Serial.available()){Serial.read();}

    blueToothSerial.flush();
    delay(2000);                                     // This delay is required

    blueToothSerial.print("\r\n+INQ=1\r\n");         // Make the slave Bluetooth inquirable
    
    blueToothSerial.flush();
    delay(2000);                                     // This delay is required
    
    Serial.println("The slave bluetooth is inquirable!");
}














//

//boolean mode = false
// if (pos == 'x'){
//  delay(1000);
//    mode = true
// }
// else {
//  delay(1000);
//  mode = false
// }
// 
//      
//
//
//  if (mode == true){
//    
//      delay(1000);
//      int i = 5
//     int irLeft = irDetect(2, 3, 38000);
//     int irRight = irDetect(9, 10, 38000);
//     //int irLeft = irDetect(2, A1, 38000);
//     //int irRight = irDetect(9, A0, 38000);
//  
//     Serial.println("L" + irLeft); 
//     Serial.println("R" + irRight); 
//     delay(100);
//     
//    if(irLeft == 0 and irRight == 0) {       // Controls movement 
//      
//      moveForward();
//    }
//    else if(irRight == 0 and irLeft == 1) {
//
//      rotateLeft();
//    }
//    else if (irRight == 1 and irLeft == 0){
//
//      rotateRight();
//    }else if (irRight == 1 and irLeft == 1){
//      stop();
//    }
//
//    delay(100);   // 0.1 second delay
//    }
//    
// 

  //
