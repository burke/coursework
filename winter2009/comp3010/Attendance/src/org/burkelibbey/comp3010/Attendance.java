package org.burkelibbey.comp3010;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.ITriColorLED;
import com.sun.spot.sensorboard.peripheral.LEDColor;
import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/*
 * Uses the Sun SPOT's radio to communicate with a Base Station and ultimately
 * recieve a sequence number that it will display in BCD notation on the
 * LED bank.
 *
 * @author Burke Libbey
 **/
public class Attendance extends MIDlet {

  private ITriColorLED [] leds = EDemoBoard.getInstance().getLEDs();

  // Turn off all the LEDs
  public void turnOff() {
    for (int i = 0; i < 8; i++) {
      leds[i].setOff();
    }
  }

  // Turn on all the LEDs
  public void turnOn() {
    for (int i = 0; i < 8; i++) {
      leds[i].setOn();
    }
  }

  // Set the color of all LEDs. This works regardless of their on/off state.
  public void setColor(LEDColor color) {
    for (int i = 0; i < 8; i++) {
      leds[i].setColor(color);
    }
  }

  // Manually convert number to BCD representation and display it on the LEDs.
  public void setBCD(int number) {
    turnOff();
    int digit1 = number % 10;
    int digit2 = number / 10;
    if(digit1 % 2 == 1)
      leds[0].setOn();
    if(digit1==2 || digit1==3 || digit1==6 || digit1==7)
      leds[1].setOn();
    if(digit1>3 && digit1<8)
      leds[2].setOn();
    if(digit1>7)
      leds[3].setOn();

    if(digit2 % 2 == 1)
      leds[4].setOn();
    if(digit2==2 || digit2==3 || digit2==6 || digit2==7)
      leds[5].setOn();
    if(digit2>3 && digit2<8)
      leds[6].setOn();
    if(digit2>7)
      leds[7].setOn();
  }

  // Blink all red on the LEDs indefinitely.
  public void blinkRed() {
    setColor(LEDColor.RED);
    try {
      while (true) {
        turnOn();
        Thread.sleep(500);
        turnOff();
        Thread.sleep(500);
      }
    } catch (Exception e) {}
  }

  // Listen for the target base station to broadcast its ID, then return it.
  private static String getBaseStationID() {
    // Receive broadcast and get the basestation's ID.
    String address = "";
    try {
      DatagramConnection recvConn = (DatagramConnection) Connector.open("radiogram://:37");
      Datagram dg = recvConn.newDatagram(recvConn.getMaximumLength());
      recvConn.receive(dg);
      address = dg.getAddress();
    } catch (IOException e) {
      //
    }
    return address;
  }

  // Send a message to a target.
  private static void sendMessage(String address, int port, String message) {
    try {
      DatagramConnection sendConn = (DatagramConnection) Connector.open("radiogram://"+address+":"+port);
      Datagram dg = sendConn.newDatagram(sendConn.getMaximumLength());
      dg.writeUTF(message);
      sendConn.send(dg);
    } catch (IOException e) {
      //
    }
  }

  // Receive a message from a target.
  private static String recvMessage(String address, int port) {
    // Receive broadcast and get the basestation's ID.
    String message = "";
    try {
      DatagramConnection recvConn = (DatagramConnection) Connector.open("radiogram://"+address+":"+port);
      Datagram dg = recvConn.newDatagram(recvConn.getMaximumLength());
      recvConn.receive(dg);
      message = dg.readUTF();
    } catch (IOException e) {
      //
    }
    return message;
  }


  protected void startApp() throws MIDletStateChangeException {
    // Listen for a broadcast and get the Basestation ID
    String bsID = getBaseStationID();

    // Format our local address for sending
    String localID = IEEEAddress.toDottedHex(
      RadioFactory.getRadioPolicyManager().getIEEEAddress());

    // Send our registration to the BS
    sendMessage(bsID,37,localID);
    // Listen for the sequence number
    int seqNum = Integer.parseInt(recvMessage(bsID,38));
    
    if(seqNum == 0) {
      blinkRed();
    }
    else {
      setColor(LEDColor.BLUE);
      setBCD(seqNum);
    }
  }

  protected void pauseApp() {
  }

  protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    turnOff();
  }
}
