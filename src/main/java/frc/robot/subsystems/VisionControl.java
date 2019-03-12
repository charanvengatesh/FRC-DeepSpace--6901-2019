package frc.robot.subsystems;



import frc.robot.Constants;
import frc.robot.Inputs.Limelight;

public class VisionControl 
{
    public static double turnError;
    public static double areaError;
    public static double outputh;
    public static double outputy;
    public static double sumError;
    public static double previousError;
    public static double differenceError;
    public static double timeoutCounter=0;
    
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
        
        turnError = 0;
        areaError = 0;
        sumError = 0;
        differenceError =0;
    }
    public static void center(double turnThreshold,double forwardThreshold)
    {
        Limelight.updateValues();
        turnError = -(Limelight.x/29); //Maximum degree error is +/- 29 so this puts it on a scale from -1 to 1
        areaError = Constants.targetArea-Limelight.area;
        if (Math.abs(turnError)< turnThreshold)
        {
            outputh = 0;
        }
        else
        {
            centerAlgorithm(turnError);
        }
        if (areaError<forwardThreshold)
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
    
       
        outputy = Constants.visionAreaP*error + Constants.minSpeedForward;
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
        double minSpeedTurn;
        sumError += error*.02;
        differenceError = (error-previousError)/.02;
        
        if (Math.abs(previousError) <= Math.abs(error) + .01)
        {
            timeoutCounter++;
        }
        else
        {
            timeoutCounter = 0;
        }
        if (timeoutCounter >= 40 && timeoutCounter<80)
        {
            minSpeedTurn = .45;
        }
        else if (timeoutCounter>=80&&timeoutCounter<120)
        {
            minSpeedTurn = .6;
        }
        else if (timeoutCounter>=120)
        {
            minSpeedTurn = .7;
        }
        else
        {
            minSpeedTurn = Constants.minSpeedTurn;
        }
        if (error<0)
        {
            outputh = Constants.visionAreaP*error + Constants.visionD*differenceError + Constants.visionI*sumError - minSpeedTurn;
        }
        else
        {
            outputh = Constants.visionAreaP*error + Constants.visionD*differenceError + Constants.visionI*sumError + minSpeedTurn;
        }
        
        if (outputh>Constants.maxSpeed)
        {
            outputh = Constants.maxSpeed;
        }
        else if (outputh<-Constants.maxSpeed)
        {
            outputh = -Constants.maxSpeed;
        }
        previousError = error;   
    }
}