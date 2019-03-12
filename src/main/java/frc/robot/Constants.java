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
  public static int sparkLeft = 2; //Left Spark motor port
  public static int sparkRight = 3; //Right Spark motor port

  //Victor motors (intakes)
  public static int intake1 = 0;
  public static int intake2 = 1;
  //Arm motors
  public static int arm1 = 1; 
  public static int arm2 = 2;
  public static int wrist = 0;
  //Xbox Controlller Ports
  public static int Xbox1 = 0;
  public static int Xbox2 = 1;
  //limit switch
  public static int limitSwitch1 = 0;
  public static int limitSwitch2 = 1;
  /*** Robot Constants values ***/
  //Arm values:
  public static int ball1 = -8700;
  public static int ball2 = -17000;
  public static int ball3 = -21000;
  public static int hatch1 = -3150;
  public static int hatch2 = -12000;
  public static int hatch3 = -20700;
  public static int hatchPickup =-1900; 
  //TODO: Configure the hatch up and down values
  public static int hatchMovement = 1300;
  public static int wristUp = -500;
  public static int defaultPosition = 0;
  public static int maxPosition = 0;
  //Arm motion magic values:
  public static int acceleration = 1000;
  public static int velocity = 1100;
  //Arm PIDF values:
  public static int armKp = 10;
  public static double armKi = .003;
  public static double armKd = 100;
  public static double armKf = 5;
  public static double intakeOut = -1;
  public static double intakeIn = .5;
  //Limelight values
  public static String limeLightKey = "limelight";
  public static String targetXKey = "tx";
  public static String targetYKey= "ty";
  public static String targetAreaKey = "ta";
  public static String camModeKey = "pipeline";
  public static String ledModeKey = "ledMode";
  public static double visionP = .8;
  public static double visionI = 0;
  public static double visionD = 0;
  public static double minSpeedTurn = .4;
  public static double minSpeedForward =0.45;
  public static double maxSpeed = 1;
  public static double visionAreaP = 0.1;
  //drivetrain constants
  public static double driveAdjust =2; //proportional adjustment for driving straight
  public static double targetArea = 3;
  public static double turnDampen = .9;
  public static double turnDefault = .65;
  public static double turnFullSpeed = 1.2;
  
  public static double forwardDampen = .7;
  public static double forwardDefault = .95;
  public static double forwardFullSpeed = 1;
}
