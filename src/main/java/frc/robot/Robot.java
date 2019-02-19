/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//TODO organize inputs and variables & make sure the vision stuff works
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
 
   Command m_autonomousCommand;
  // Command hello;
  // SendableChooser<Command> m_chooser = new SendableChooser<>();
  // SmartDashboard dash;
 
  // public GripPipeline grip;
  // public Timer  timer;
   public int armPosition;
   public double turnMagnitude;
   public double forwardMagnitude;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
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
    Lift.initializeLift();
    // thing = 0;  
    // camera = new UsbCamera("camera","cam0");
    // camera.setResolution(640, 480);
    //  cvSink = CameraServer.getInstance().getVideo();
    // grip = new GripPipeline();  //probably not going to do autunomous this year, leave this blank.
  }

  
  /**
   * This function is called periodically during autonomous, leave this blank.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    Lift.runArmHold(Lift.LiftPosition.HATCH1);
  }
    
  @Override
  public void teleopInit() {
    Lift.initializeLift();
    //armMotorMaster.setSelectedSensorPosition(0);
    //armPosition = 0;
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
    System.out.println(Lift.setPositionHold());
    if(OI.controller1.getAButton()){
      Lift.wristMotor.setSelectedSensorPosition(0);
    }
    Lift.setPosition();
    Lift.runArm();
    //Lift.runArmHold(Lift.setPositionHold());
    //Lift.moveManual();
    DriveTrain.customArcadeDriver();
    Intake.setIntake();
    Lift.wristMotor.set(ControlMode.PercentOutput,OI.controller2.getY(Hand.kRight)*.5);
  
  }

  /**
   * This function is called periodically during test mode.
   * Currently will be used as diagnostics on the full robot.
   */
  @Override
  public void testPeriodic() {

 //System.out.println(m_oi.controller1.getY(Left));
  //  armMotorMaster.set(ControlMode.PercentOutput,m_oi.controller2.getY(Left)*.5);
  }
}


