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
public class Constants {
  /**** Robot Ports ****/
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
  public static int Xbox1 = 2;
  public static int Xbox2 = 1;
  //limit switch
  public static int limitSwitch1 = 0;
  public static int limitSwitch2 = 1;
  /*** Robot Constants values ***/
  //Arm values:
  public static int ball1 = -1850;
  public static int ball2 = -4400;
  public static int ball3 = -5750;
  public static int hatch1 = -1000;
  public static int hatch2 = -3500;
  public static int hatch3 = -5500;
  //TODO: Configure the hatch up and down values
  public static int hatchUp = 0; 
  public static int hatchDown = 0;
  public static int defaultPosition = 0;
  //Arm motion magic values:
  public static int acceleration = 60;
  public static int velocity = 200;
  //Arm PIDF values:
  public static int armKp = 3;
  public static double armKi = .006;
  public static double armKd = .1;
  public static double armKf = 5;


}
