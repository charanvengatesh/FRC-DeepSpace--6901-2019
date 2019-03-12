package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.OI;

public class DriveTrain{
    public static DifferentialDrive driveTrain = new DifferentialDrive(new Spark(Constants.sparkLeft),new Spark(Constants.sparkRight));
    public static double turnMagnitude = 0;
    public static double forwardMagnitude = 0;
    public static PigeonIMU gyro = new PigeonIMU(0);
    public static double[] ypr = new double[3];
    public static double error = 0;
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
            VisionControl.resetController();
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
                
                VisionControl.center(.037,.1);
                forwardMagnitude =-VisionControl.outputy;
                turnMagnitude = -VisionControl.outputh;
                break;
            case GYRODRIVERCONTROL:
            gyro.setYaw(0); //resets gyro value
            driveStraight();
            break;
            case FULLDRIVERCONTROL:
                //VisionControl.endControl();
                customArcadeDrive();
                break;
            
        }
        driveTrain.arcadeDrive(forwardMagnitude, turnMagnitude);
    }
    public static void driveStraight()
    {
        gyro.getYawPitchRoll(ypr);
        turnMagnitude = -ypr[0]*Constants.driveAdjust;
        forwardMagnitude = OI.controller1.getY(Hand.kLeft);
        if (OI.controller1.getTriggerAxis(Hand.kLeft)>0)
        {
            forwardMagnitude *= .7;
        }
        else if (OI.controller1.getTriggerAxis(Hand.kRight)>0)
        {
            forwardMagnitude*=1;
         
        }

        if (Math.abs(turnMagnitude)>.7 && turnMagnitude<0)
        {
            turnMagnitude = -.7;
        }
        else if (Math.abs(turnMagnitude)>.7 && turnMagnitude>0)
        {
            turnMagnitude = .7;
        }
        else if (Math.abs(turnMagnitude)<.45 && turnMagnitude >0)
        {
            turnMagnitude = .45;
        }
        else if (Math.abs(turnMagnitude)<.45&& turnMagnitude<0)
        {
            turnMagnitude = -.45;
        }
        turnMagnitude*=-1;
    }
    public static void customArcadeDrive()
    {
        turnMagnitude = OI.controller1.getX(Hand.kRight)*Constants.turnDefault;
        forwardMagnitude = OI.controller1.getY(Hand.kLeft)*Constants.forwardDefault;
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
            turnMagnitude *= Constants.turnDampen;
            forwardMagnitude *= Constants.forwardDampen;
        }
        else if (OI.controller1.getTriggerAxis(Hand.kRight)>0)
        {
            forwardMagnitude*=Constants.forwardFullSpeed;
            turnMagnitude*=Constants.turnFullSpeed;
        }
        
 
    }
}