/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand; //ignore any green squiggly underlines
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //Operator Interface:
  private static final Hand Right = Hand.kRight; //Lefthand Side for controller
  private static final Hand Left = Hand.kLeft; //Righthand side for controller
  public static OI m_oi;
  public Gyro gyro;
  //Motors:
  public static DifferentialDrive m_driveTrain;
  public static Victor intake1;
  public static Victor intake3;
  
  //Sensors and variables:
  //public static Ultrasonic  ultra;
  public double intakePower; 

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
    m_oi = new OI(); //initializing the Operator Interface (OI) class
    //drive train initialization:
    m_driveTrain = new DifferentialDrive(new Spark(RobotMap.sparkLeft), new Spark(RobotMap.sparkRight));  //Creating a differential drive with the spark motors
    
    SmartDashboard.putData("Auto mode", m_chooser); //Modifying smart dashboard GUI
    
    //intake initialization:
    intake1 = new Victor(RobotMap.Victor1); //Intake motor 1
    intake3 = new Victor(RobotMap.Victor3); //Intake motor 2
    gyro = new AnalogGyro(1);
    //ultrasonic initialization:
    //ultra = new Ultrasonic(1,1); 
    //ultra.setAutomaticMode(true);
    gyro.reset();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    //probably not going to do autunomous this year, leave this blank.
  }

  
  /**
   * This function is called periodically during autonomous, leave this blank.
   */
  @Override
  public void autonomousPeriodic() {
    //probably not going to do autunomous this year.
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //m_driveTrain.arcadeDrive(m_oi.controller.getY(Left),m_oi.controller.getX(Right)); //arcade drive controlled by the XBOX controller
    }

  /**
   * This function is called periodically during test mode.
   * Currently just use this function whenever we test the robot.
   */
  @Override
  public void testPeriodic() {
    
    System.out.println(gyro.getAngle());
    //test to see if you can run programs on the robot:
    //m_driveTrain.tankDrive(.5,.5);
    //test drive train code with controller
    m_driveTrain.arcadeDrive(m_oi.controller.getY(Left),m_oi.controller.getX(Right));
    
    //uncomment lines 147-149 to test intake motors here
    //intakePower = 0.5; //DO NOT SET OVER .6!!!
    if (m_oi.controller.getAButton()){
      intakePower = 0.2; //Pulls in with A
    }
    else if (m_oi.controller.getBButton()){
      intakePower = -0.2; //Spits out with B
    }
    else if (
      !m_oi.controller.getAButton() || !m_oi.controller.getBButton()){
      intakePower=0; //Otherwise it doesn't do anything
    }
    

    intake3.set(-intakePower);
    intake1.set(intakePower);
    
      

  }
}
