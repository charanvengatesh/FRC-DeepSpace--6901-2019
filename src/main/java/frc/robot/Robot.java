/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.subsystems.VisionAlgorithm;
//TODO organize inputs and variables & make sure the vision stuff works
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand; //ignore any green squiggly underlines
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Inputs.GripPipeline;
import frc.robot.subsystems.VisionAlgorithm;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.networktables.*;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionThread;
//import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Ultrasonic;
//imports for the talon and stuff
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


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
 
  //Motors:
  public static DifferentialDrive m_driveTrain;
  public static SpeedControllerGroup leftSide;
  public static SpeedControllerGroup rightSide;
  public static Spark intake1;
  public static Spark intake2;
  public static VictorSPX armMotorSlave;
  public static WPI_TalonSRX armMotorMaster;
  public static TalonSRX wristMotor;
  
  
  //Sensors and variables:
  public double intakePower; 
  public double thing;
  public double[] controllerValues;
  public double speedFactor;
  public VisionThread visionThread;
  public NetworkTable table;
  NetworkTableEntry xEntry;
  public UsbCamera camera;
  public DigitalInput limitSwitch1;
  public DigitalInput limitSwitch2;
  
  Command m_autonomousCommand;
  Command hello;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  SmartDashboard dash;
  public Mat mat;
  public double previousRight;
  public double previousLeft;
  public CvSink cvSink;
  public GripPipeline grip;
  public Timer  timer;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI(); //initializing the Operator Interface (OI) class
    //drive train initialization:
    m_driveTrain = new DifferentialDrive(new Spark(RobotMap.sparkLeft), new Spark(RobotMap.sparkRight));  //Creating a differential drive with the spark motors
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    //UsbCamera camera01 = CameraServer.getInstance().startAutomaticCapture();
    
    m_chooser.addOption("Hi", hello);
    previousLeft =0.4;
    previousRight=0.4;
    //intake initialization:
    intake1 = new Spark(RobotMap.intake1); //Intake motor 1
    intake2 = new Spark(RobotMap.intake2); //Intake motor 2
    // //ultrasonic initialization:
    //ultra = new Ultrasonic(1,1); 
    //ultra.setAutomaticMode(true);

    armMotorSlave = new VictorSPX(RobotMap.arm2);		// Follower MC, Could be a victor
    armMotorMaster = new WPI_TalonSRX(RobotMap.arm1);		// Master MC, Talon SRX for Mag Encoder
    // wristMotor = new TalonSRX(RobotMap.wrist); //creates an SRX for the robot wrist
    // limitSwitch1 = new DigitalInput(RobotMap.limitSwitch1);
    // limitSwitch2 = new DigitalInput(RobotMap.limitSwitch2);
    mat = new Mat();
    //  new Thread(() -> {
    //   //camera = CameraServer.getInstance().startAutomaticCapture();
    //   camera.setResolution(640, 480);

    //   CvSink cvSink = CameraServer.getInstance().getVideo();
    //   CvSource outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);

    //   Mat source = new Mat();
    //   Mat output = new Mat();

    //   while(!Thread.interrupted()) {
    //       cvSink.grabFrame(source);
    //       GripPipeline cam = new GripPipeline();
    //       cam.process(source);
    //       outputStream.putFrame(output);
    //   }
    // }).start();
  // //resets arm motors
    timer = new Timer();
    armMotorMaster.configFactoryDefault();
    armMotorSlave.configFactoryDefault();
    // wristMotor.configFactoryDefault();
    armMotorSlave.follow(armMotorMaster);
    armMotorMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
    armMotorMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    armMotorMaster.setSelectedSensorPosition(0);
    // wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    armMotorMaster.config_kP(0,3);
    armMotorMaster.config_kI(0,.006);
    armMotorMaster.config_kD(0, .1);
    armMotorMaster.config_kF(0, 5);
     armMotorMaster.configMotionAcceleration(60);
     armMotorMaster.configMotionCruiseVelocity(200);
    

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
    armMotorMaster.setSelectedSensorPosition(0);
  
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
    //probably not going to do autunomous this year.
    //System.out.println(armMotorMaster.getSelectedSensorPosition(0));
    //armMotorMaster.set(ControlMode.MotionMagic, 1850);
    
    //armMotorMaster.set(ControlMode.MotionMagic,4250);
    //double stuff = armMotorMaster.getSelectedSensorPosition(0);
    //armMotorMaster.set(ControlMode.MotionMagic,1850+2400);
    //armMotorMaster.set(ControlMode.MotionMagic,1850+2400+2000);
  }
    
  @Override
  public void teleopInit() {
    armMotorMaster.setSelectedSensorPosition(0);
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
    System.out.println(armMotorMaster.getSelectedSensorPosition(0));
    //armMotorMaster.set(ControlMode.PercentOutput, m_oi.controller2.getY(Left));
    if (m_oi.controller2.getAButton()){
      armMotorMaster.set(ControlMode.MotionMagic,-1850);
    }
    else if(m_oi.controller2.getBButton()){
      armMotorMaster.set(ControlMode.MotionMagic,-4400);
    }
    else if (m_oi.controller2.getYButton()) {
      //make it move up 3 roatations
      armMotorMaster.set(ControlMode.MotionMagic,-5750);
    }
    else if (m_oi.controller2.getXButton()){
      armMotorMaster.set(ControlMode.MotionMagic,0);
    }
    else if (m_oi.controller2.getPOV()==180){
      armMotorMaster.set(ControlMode.MotionMagic,-1000);
    }
    else if(m_oi.controller2.getPOV()==270){
      armMotorMaster.set(ControlMode.MotionMagic,-3500);
    }
    else if (m_oi.controller2.getPOV()==0) {
      //make it move up 3 roatations
      armMotorMaster.set(ControlMode.MotionMagic,-5500);
    }
    else if (m_oi.controller2.getPOV()==90){
      armMotorMaster.set(ControlMode.MotionMagic,0);
    }
    else {
      armMotorMaster.set(ControlMode.PercentOutput,m_oi.controller2.getY(Left));
    }  //System.out.println("hi");  
  }

  /**
   * This function is called periodically during test mode.
   * Currently will be used as diagnostics on the full robot.
   */
  @Override
  public void testPeriodic() {
    //armMotorMaster.set(40);
    // double thing = 0;
    // double rightSpeed = m_oi.controller1.getY(Right);
    // double leftSpeed = m_oi.controller1.getY(Left);
    // double rightSpeed = m_oi.controller1.getY(Left);
    // double leftSpeed = m_oi.controller1.getX(Right);
    //System.out.println(m_oi.controller2.getPOV());
   
   //m_driveTrain.arcadeDrive(m_oi.controller1.getY(Left), m_oi.controller1.getX(Right)); //ensure the drive train is running (Controller 1)
    //armMotorMaster.set(ControlMode.PercentOutput, m_oi.controller2.getY(Left)); //ensure the lift is working (Controller 2 Right Joystick)
    // wristMotor.set(ControlMode.PercentOutput, m_oi.controller2.getY(Left)); //ensure the wrist works (Controller 2 Left Joystick)
    // //System.out.println(limitSwitch.get());
    //System.out.println(m_oi.controller2.getY(Left));
    //System.out.println(armMotorMaster.getSelectedSensorPosition(0)); //See if the encoder is printing out anything
    //see if the intake workes properly
   
    if (m_oi.controller2.getBumper(Left)){
         intakePower = .5;
      }
      else if(m_oi.controller2.getBumper(Right)){
        intakePower = -.5;
      }
      else{
        intakePower = 0;
      }

    intake2.set(-intakePower);
    intake1.set(intakePower);
    
    System.out.println(intakePower);
    //testing if the vision code will work...
    
     //double hi = Robot.VisionAlgorithm.findCenter();
  }
}


