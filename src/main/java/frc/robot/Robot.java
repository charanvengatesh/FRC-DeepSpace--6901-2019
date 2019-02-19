/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//TODO organize inputs and variables & make sure the vision stuff works
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand; //ignore any green squiggly underlines
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
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
  
  
//   //Sensors and variables:
     public double intakePower; 
//   public double thing;
//   public double[] controllerValues;
//  // public double speedFactor;
//   public VisionThread visionThread;
//   public NetworkTable table;
//   NetworkTableEntry xEntry;
//   public UsbCamera camera;
   public DigitalInput limitSwitch1;

  
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
    m_oi = new OI(); //initializing the Operator Interface (OI) class
    //drive train initialization:
    m_driveTrain = new DifferentialDrive(new Spark(Constants.sparkLeft), new Spark(Constants.sparkRight));  //Creating a differential drive with the spark motors
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    //UsbCamera camera01 = CameraServer.getInstance().startAutomaticCapture();
    
   // m_chooser.addOption("Hi", hello);
    
    //intake initialization:
    intake1 = new Spark(Constants.intake1); //Intake motor 1
    intake2 = new Spark(Constants.intake2); //Intake motor 2
    // // //ultrasonic initialization:
    // //ultra = new Ultrasonic(1,1); 
    //ultra.setAutomaticMode(true);

    armMotorSlave = new VictorSPX(Constants.arm2);		// Follower MC, Could be a victor
    armMotorMaster = new WPI_TalonSRX(Constants.arm1);		// Master MC, Talon SRX for Mag Encoder
    wristMotor = new TalonSRX(Constants.wrist); //creates an SRX for the robot wrist
    limitSwitch1 = new DigitalInput(Constants.limitSwitch1);
    //limitSwitch2 = new DigitalInput(Constants.limitSwitch2);
    
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
   //resets arm motors
    // timer = new Timer();
    armMotorMaster.configFactoryDefault();
    armMotorSlave.configFactoryDefault();
    wristMotor.configFactoryDefault();
    armMotorSlave.follow(armMotorMaster);
    armMotorMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
    armMotorMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    armMotorMaster.setSelectedSensorPosition(0);
    wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    armMotorMaster.config_kP(0,9);
    armMotorMaster.config_kI(0,.006);
    armMotorMaster.config_kD(0, 80);
    armMotorMaster.config_kF(0,5);
     armMotorMaster.configMotionAcceleration(750);
     armMotorMaster.configMotionCruiseVelocity(220);
    

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
 
  }
    
  @Override
  public void teleopInit() {
    //Lift.initializeLift();
    armMotorMaster.setSelectedSensorPosition(0);
    armPosition = 0;
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
  
 m_driveTrain.arcadeDrive(m_oi.controller1.getY(Left),m_oi.controller1.getX(Right));
 
    if (m_oi.controller2.getAButton()){
      armMotorMaster.set(ControlMode.MotionMagic,Constants.ball1);
    }
    else if(m_oi.controller2.getBButton()){
      armMotorMaster.set(ControlMode.MotionMagic,Constants.ball2);
    }
    else if(m_oi.controller2.getYButton()){
      armMotorMaster.set(ControlMode.MotionMagic,Constants.ball3);
    }
    else if(m_oi.controller2.getXButton()){
      if (limitSwitch1.get()){
        armMotorMaster.set(ControlMode.PercentOutput,.6);
      }
      else{
        armMotorMaster.set(ControlMode.PercentOutput,0);
        armMotorMaster.setSelectedSensorPosition(0);
       // System.out.println("stopped");
      } 
    }
    else if (m_oi.controller2.getPOV()==180){
      armMotorMaster.set(ControlMode.MotionMagic,Constants.hatch1);
    }
    else if (m_oi.controller2.getPOV()==270){
      armMotorMaster.set(ControlMode.MotionMagic,Constants.hatch2);
    }
    else if (m_oi.controller2.getPOV()==0){
      armMotorMaster.set(ControlMode.MotionMagic,Constants.hatch3);
    }
    else if (m_oi.controller2.getTriggerAxis(Right)>0){
      armMotorMaster.set(ControlMode.MotionMagic,armMotorMaster.getSelectedSensorPosition(0)-Constants.hatchUp);
    }
    else if (m_oi.controller2.getTriggerAxis(Left)>0){
      armMotorMaster.set(ControlMode.MotionMagic,armMotorMaster.getSelectedSensorPosition(0)+ Constants.hatchUp);
    }
    else if (Math.abs(m_oi.controller2.getY(Left))>=.09){
      armMotorMaster.set(ControlMode.PercentOutput,m_oi.controller2.getY(Left));
    }
    else{
      armMotorMaster.set(ControlMode.PercentOutput,0);
    }
    System.out.println(armMotorMaster.getSelectedSensorPosition(0));
    if (Math.abs(m_oi.controller2.getY(Right))>=.09){
      wristMotor.set(ControlMode.PercentOutput, -m_oi.controller2.getY(Right)*.5);
    }
    System.out.println(m_oi.controller2.getY(Left));

    if (m_oi.controller2.getBumper(Right)){
      intakePower = -0.5;
    }
    else if (m_oi.controller2.getBumper(Left)){
      intakePower = 1;
    }
    else{
      intakePower = 0;
    }
    intake2.set(intakePower);
    intake1.set(-intakePower);
 
  
  }

  /**
   * This function is called periodically during test mode.
   * Currently will be used as diagnostics on the full robot.
   */
  @Override
  public void testPeriodic() {
    // if (m_oi.controller2.getBumper(Right)){
    //   intakePower = -0.5;
    // }
    // else if (m_oi.controller2.getBumper(Left)){
    //   intakePower = 1;
    // }
    // else{
    //   intakePower = 0;
    // }
    // intake2.set(intakePower);
    // intake1.set(-intakePower);
  wristMotor.set(ControlMode.PercentOutput,-m_oi.controller2.getY(Right)*.5);
  System.out.println(wristMotor.getSelectedSensorPosition(0));
  //  armMotorMaster.set(ControlMode.PercentOutput,m_oi.controller2.getY(Left)*.5);
  }
}


