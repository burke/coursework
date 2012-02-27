// CLASS: Employee
//
// REMARKS: Models an employee in an event-based simulation
//
// Input: None
//
// Output: None
//
//-----------------------------------------
import java.util.Random;

public class Employee extends Person {
  int quittingTime;
  int speed;
  static Random x = new Random(1000);

  //------------------------------------------------------
  // Employee
  //
  // PURPOSE: Construct a new Employee
  // PARAMETERS:
  //     employeeNumber: the ID number of the Employee
  //     quittingTime: the time at which this employee expects to end their shift
  //     arrivalTime: ths time at which this employee will arrive
  //------------------------------------------------------
  public Employee(int employeeNumber, int quittingTime, int arrivalTime) {
    this.id = employeeNumber;
    this.quittingTime = quittingTime;
    this.arrival = arrivalTime;
    setSpeed();
  }

  //------------------------------------------------------
  // unitsForItems
  //
  // PURPOSE: Return the number of time units this employee will take to process n items
  // PARAMETERS:  items: the number of itmems to process
  //------------------------------------------------------
  public int unitsForItems(int items) {
    return (int)Math.ceil((double)items / (double)speed);
  }
  
  //------------------------------------------------------
  // getQuittingTime
  //
  // PURPOSE: return the time at which this employee expects to leave
  //------------------------------------------------------
  public int getQuittingTime() {
    return this.quittingTime;
  }

  //------------------------------------------------------
  // setSpeed
  //
  // PURPOSE: Set the speed of this employee based on a predefined "random" sequence
  //     (see static Random x above)
  //------------------------------------------------------
  private void setSpeed() {
    speed = x.nextInt(10)+1;
  }

  //------------------------------------------------------
  // getSpeed
  //
  // PURPOSE: return the speed of this employee
  //------------------------------------------------------
  public int getSpeed() {
    return this.speed;
  }
  
}