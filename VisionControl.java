package frc.robot.subsystems;



import frc.robot.Constants;
import frc.robot.Inputs.Limelight;

public class VisionControl 
{
    public static double sumError =0;
    public static double error =0;
    public static double areaError =0;
    public static double outputh = 0;
    public static double outputy = 0;
    public static double area = 0;
    public static void startControl()
    {
        Limelight.lightOn();
        Limelight.visionCam();
    }
    public static void endControl()
    {
        Limelight.lightsOff();
        Limelight.driverCam();
    }
    public static void resetController()
    {
        sumError = 0;
        error = 0;
    }
    public static void center(double threshold1,double threshold2)
    {
        Limelight.updateValues();
        error = -(Limelight.x/27);
        areaError = 9-Limelight.area;
        if (error < threshold1 && error > - threshold1)
        {
            outputh = 0;
        }
        else
        {
            centerAlgorithm(error);
        }
        if (areaError<threshold2 && areaError>-threshold2)
        {
            outputy =0;
        }
        else
        {
            forward(areaError);
        }

    }
    
    public static void forward(double error)
    {
        outputy = Constants.visionAreaP*areaError;
        if (Math.abs(outputy) < Constants.minSpeedForward && outputy >0)
        {
            outputy = Constants.minSpeedForward;
        }
        else if (Math.abs(outputy) < Constants.minSpeedForward && outputy<0)
        {
            outputy = -Constants.minSpeedForward;
        }
        else if (outputy>Constants.maxSpeed)
        {
            outputy = Constants.maxSpeed;
        }
        else if (outputy<-Constants.maxSpeed)
        {
            outputy = -Constants.maxSpeed;
        }   

    }
    public static void centerAlgorithm(double error)
    {
        
        
        sumError += error;
        outputh = Constants.visionP*error;
        
        if (Math.abs(outputh) < Constants.minSpeedTurn && outputh >0)
        {
            outputh = Constants.minSpeedTurn;
        }
        else if (Math.abs(outputh)<Constants.minSpeedTurn && outputh<0)
        {
            outputh = -Constants.minSpeedTurn;
        }
        else if (outputh>Constants.maxSpeed)
        {
            outputh = Constants.maxSpeed;
        }
        else if (outputh<-Constants.maxSpeed)
        {
            outputh = -Constants.maxSpeed;
        }   
    }
}