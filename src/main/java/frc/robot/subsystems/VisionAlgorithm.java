/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.Inputs.GripPipeline;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
/**
 * Add your docs here.
 */
public class VisionAlgorithm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public UsbCamera camera;
  public Mat mat;
  public double focalLength;
  public double objectLength;
  public int IMG_WIDTH;
  public int IMG_HEIGHT;
  public static DifferentialDrive driveTrain;
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public void initCamera() {

    camera = CameraServer.getInstance().startAutomaticCapture();
    mat = new Mat();
    focalLength = 0;
    objectLength=0;
    IMG_WIDTH = 640;
    IMG_HEIGHT = 480;
  }
  public double findCenter(){
    System.out.println("test");
    camera = new UsbCamera("camera","cam0");
    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    CvSink cvSink = CameraServer.getInstance().getVideo();
    GripPipeline grip = new GripPipeline();
        	
    if (cvSink.grabFrame(mat,2) == 0){
    		System.out.println(cvSink.getError());
    }
    	else{
        grip.process(mat);
    }
    try{
      Rect r1 = Imgproc.boundingRect(grip.filterContoursOutput().get(0));			
      Rect r2= Imgproc.boundingRect(grip.filterContoursOutput().get(1));			
      double center1 = r1.x + r1.width/2;
      double center2 = r2.x + r2.width/2;
      double center = Math.abs(center1-center2)/2;
      return center;
    }
    catch (ArrayIndexOutOfBoundsException e){
      return 0;
    }
  }
  public void alignRobot(double threshold){
    initCamera();
    driveTrain = new DifferentialDrive(new Spark(RobotMap.sparkLeft), new Spark(RobotMap.sparkRight));  
    double error = IMG_WIDTH/2-findCenter();
    Timer timer = new Timer();
    timer.reset();
    timer.start();
    while(timer.get()<=10 && (error>threshold || error<-threshold)){ //just stops trying after 10 seconds
      error = Math.abs(IMG_WIDTH-findCenter());
      if (error < threshold && error > -threshold){     //if x is between threshold, drivetrain is set to zero speed
        driveTrain.tankDrive(0,0);
      }
      else if (error > threshold){ //if x is greater than threshold, then turn left
        driveTrain.tankDrive(-.3,.3);   
      }
      else if(error < -threshold){ //if x is less than -threshold, then turn right
        driveTrain.tankDrive(.3,-.3);
      }

    }
    timer.stop();
    System.out.println("Ran");
  }

}
  

