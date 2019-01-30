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
import edu.wpi.first.wpilibj.command.Subsystem;
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
    camera = new UsbCamera("camera","cam0");
    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
    CvSink cvSink = CameraServer.getInstance().getVideo();
    GripPipeline grip = new GripPipeline();
        	
    if (cvSink.grabFrame(mat,30) == 0)
    	{
    		System.out.println(cvSink.getError());
    	}
    	else
    	{
        grip.process(mat);
      }
    try{
      Rect r1 = Imgproc.boundingRect(grip.filterContoursOutput().get(0));			
      Rect r2= Imgproc.boundingRect(grip.filterContoursOutput().get(1));			
      double center1 = r1.x + r1.width/2;
      double center2 = r2.x + r2.width/2;
      double center = Math.abs(center1-center2)/2;
      double error = Math.abs(center-IMG_WIDTH/2);
      double distance = (objectLength*focalLength)/r1.width;
      
      return 0;
    }
    catch (ArrayIndexOutOfBoundsException e){
      return 0;
    }
  }
  
}
