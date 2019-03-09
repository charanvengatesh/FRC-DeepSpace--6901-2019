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
    public static driveModes mode = driveModes.FULLDRIVERCONTROL;
    public static enum driveModes
    {
        FULLDRIVERCONTROL,
        VISIONCONTROL,
        GYRODRIVERCONTROL
    }
    public static void updateDriveMode()
    {
        if (Math.abs(OI.controller1.getY(Hand.kLeft))>=0.09 && Math.abs(OI.controller1.getX(Hand.kRight))<=.09)
        {
            mode = driveModes.GYRODRIVERCONTROL;
        }
        else if (OI.controller1.getAButton())
        {
            mode = driveModes.VISIONCONTROL;
        }
        else 
        {
            mode = driveModes.FULLDRIVERCONTROL;
        }

    }
    public static void drive()
    {
        switch (mode)
        {
            case VISIONCONTROL:
            VisionControl.startControl();
                VisionControl.center(Constants.threshold,0);
                forwardMagnitude = 0;
                turnMagnitude = VisionControl.outputh;
                break;
            case GYRODRIVERCONTROL:
            case FULLDRIVERCONTROL:
                VisionControl.endControl();
                customArcadeDriver();
                break;
            
        }
        driveTrain.arcadeDrive(forwardMagnitude, turnMagnitude);
    }

    public static void customArcadeDriver()
    {
        turnMagnitude = OI.controller1.getX(Hand.kRight)*.7;
        forwardMagnitude = OI.controller1.getY(Hand.kLeft)*1;
        if(Math.abs(turnMagnitude)<=.07)
        {
            turnMagnitude = 0;
        }
        if(Math.abs(forwardMagnitude)<=.07)
        {
            forwardMagnitude = 0;
        }
        if (OI.controller1.getTriggerAxis(Hand.kLeft)>0)
        {
            turnMagnitude *= .7;
            forwardMagnitude *= .7;
        }
        else if (OI.controller1.getTriggerAxis(Hand.kRight)>0)
        {
            forwardMagnitude*=1;
            turnMagnitude*=1.3;
        }
        
 
    }
}