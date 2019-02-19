package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.OI;

public class DriveTrain{
    public static DifferentialDrive driveTrain = new DifferentialDrive(new Spark(Constants.sparkLeft),new Spark(Constants.sparkRight));
    public static double turnMagnitude = 0;
    public static double forwardMagnitude = 0;
    public static void customArcadeDriver(){
        turnMagnitude = OI.controller1.getX(Hand.kRight)*.9;
        forwardMagnitude = OI.controller1.getY(Hand.kLeft)*.8;
        if(Math.abs(turnMagnitude)<=.07){
            turnMagnitude = 0;
        }
        if(Math.abs(forwardMagnitude)<=.07){
            forwardMagnitude = 0;
        }
        if (OI.controller1.getTriggerAxis(Hand.kLeft)>0){
            turnMagnitude *= .6;
            forwardMagnitude *= .6;
        }
        else if (OI.controller1.getTriggerAxis(Hand.kRight)>0){
            forwardMagnitude*=1.1;
            turnMagnitude*=1.2;
        }
        driveTrain.arcadeDrive(forwardMagnitude, turnMagnitude);
        
        
    }
}