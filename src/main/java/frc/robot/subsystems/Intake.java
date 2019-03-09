package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;
import frc.robot.OI;

public class Intake
{
    public static double intakePower = 0;
    public static Victor intake1= new Victor(Constants.intake1);
    public static Victor intake2= new Victor(Constants.intake2);
    public static void setIntake()
    {
        if (OI.controller2.getBumper(Hand.kRight))
        {
            intakePower = -0.5;
        }
        else if (OI.controller2.getBumper(Hand.kLeft))
        {
            intakePower = 1;
        }
        else
        {
            intakePower = 0;
        }
        intake2.set(intakePower);
        intake1.set(-intakePower);
    }
    
}