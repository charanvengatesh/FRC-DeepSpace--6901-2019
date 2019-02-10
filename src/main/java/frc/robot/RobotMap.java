/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  
  //Spark motors for driving the robot
  public static int sparkLeft = 8; //Left Spark motor port
  public static int sparkRight = 9; //Right Spark motor port

  //Victor motors (intakes)
  public static int intake1 = 1;
  public static int intake2 = 2;
  //Arm motors
  public static int arm1 = 1; 
  public static int arm2 = 2;
  public static int wrist = 0;
  //Xbox Controlller Ports
  public static int Xbox1 = 0;
  public static int Xbox2 = 0;
  //limit switch
  public static int limitSwitch1 = 0;
  public static int limitSwitch2 = 0;


}
