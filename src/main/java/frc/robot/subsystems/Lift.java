package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;
import frc.robot.OI;

public class Lift
{
    public static WPI_TalonSRX armMaster = new WPI_TalonSRX(Constants.arm1);
    public static VictorSPX armSlave = new VictorSPX(Constants.arm2);
    public static WPI_TalonSRX wristMotor = new WPI_TalonSRX(Constants.wrist);
    public static DigitalInput limitSwitch1 = new DigitalInput(Constants.limitSwitch1);
    public static OI m_oi = new OI();
    public static LiftPosition currentPosition = null;
    public static LiftPosition aimedPosition = LiftPosition.RESET;
    public static int encoderPos = 0;
    public static DigitalInput limitSwitch2 = new DigitalInput(Constants.limitSwitch2);
    public static void initializeLift()
    {
        aimedPosition = LiftPosition.RESET;
        armMaster.configFactoryDefault();
        armSlave.configFactoryDefault();
        wristMotor.configFactoryDefault();
        armSlave.follow(armMaster);
        armMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        armMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
        armMaster.config_kP(0, Constants.armKp);
        armMaster.config_kI(0, Constants.armKi);
        armMaster.config_kD(0, Constants.armKd);
        armMaster.config_kF(0, Constants.armKf);
        armMaster.configMotionAcceleration(Constants.acceleration);
        armMaster.configMotionCruiseVelocity(Constants.velocity);
        armMaster.setSelectedSensorPosition(0);

        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        wristMotor.config_kP(0,Constants.armKp);
        wristMotor.config_kI(0,Constants.armKi);
        wristMotor.config_kD(0,Constants.armKd);
        wristMotor.config_kF(0,Constants.armKf);
        wristMotor.setSelectedSensorPosition(0);
        wristMotor.configMotionAcceleration(50);
        wristMotor.configMotionCruiseVelocity(100);
    }
    public static enum LiftPosition
    {
        BALL1,
        BALL2,
        BALL3,
        HATCH1,
        HATCH2,
        HATCH3,
        RESET,
        HATCHUP,
        HATCHDOWN,
        MANUAL
    }
    /** Press button System */
    public static void setPosition()
    {
        
        if(OI.controller2.getAButton())
        {
            aimedPosition = LiftPosition.BALL1;
        }
        else if(OI.controller2.getBButton())
        {
            aimedPosition = LiftPosition.BALL2;
        }
        else if(OI.controller2.getYButton())
        {
            aimedPosition = LiftPosition.BALL3;
        }
        else if(OI.controller2.getXButton())
        {
            aimedPosition = LiftPosition.RESET;
        }
        else if(OI.controller2.getPOV()==180)
        {
            aimedPosition =  LiftPosition.HATCH1;
        }
        else if(OI.controller2.getPOV()==270)
        {
            aimedPosition = LiftPosition.HATCH2;
        }
        else if(OI.controller2.getPOV()==0)
        {
            aimedPosition = LiftPosition.HATCH3;
        }
        else if(OI.controller2.getTriggerAxis(Hand.kRight)>0)
        {
            aimedPosition = LiftPosition.HATCHUP;
            encoderPos = armMaster.getSelectedSensorPosition(0);
        }
        else if(OI.controller2.getTriggerAxis(Hand.kLeft)>0)
        {
            aimedPosition =  LiftPosition.HATCHDOWN;
            encoderPos = armMaster.getSelectedSensorPosition(0);
        }
        else if(Math.abs(OI.controller2.getY(Hand.kLeft))>=.09)
        {
            aimedPosition = LiftPosition.MANUAL;
            encoderPos = armMaster.getSelectedSensorPosition(0);
        }
    }
    
    public static boolean positionRecognizer(LiftPosition aimedState)
    {
        switch(aimedState)
        {
            case BALL1:
            return (Math.abs(armMaster.getSelectedSensorPosition(0)-Constants.ball1)<100);
            case BALL2:
            return (Math.abs(armMaster.getSelectedSensorPosition(0)-Constants.ball2)<100);
            case BALL3:
            return (Math.abs(armMaster.getSelectedSensorPosition(0)-Constants.ball3)<100);
            case HATCH1:
            return (Math.abs(armMaster.getSelectedSensorPosition(0)-Constants.hatch1)<100);
            case HATCH2:
            return (Math.abs(armMaster.getSelectedSensorPosition(0)-Constants.hatch2)<100);
            case HATCH3:
            return (Math.abs(armMaster.getSelectedSensorPosition(0)-Constants.hatch3)<100);
            default:
            return true;
        }
        

        
    }
        
    public static void runArm()
    {
        
        switch(aimedPosition)
        {
            case MANUAL:
                
                if(Math.abs(OI.controller2.getY(Hand.kLeft))>=.09)
                {
                 moveManual();
                }
                else
                {
                 armMaster.set(ControlMode.PercentOutput,0);
                }
                manualWrist();
                break;
            case RESET:
                if(limitSwitch1.get())
                {
                    resetLift();
                }
                else
                {
                    armMaster.set(ControlMode.PercentOutput,0);
                    armMaster.setSelectedSensorPosition(0);
                }
                manualWrist();
                break;
            case BALL1:
            if(positionRecognizer(aimedPosition))
            {
                currentPosition = aimedPosition;
            }
            else
            {
                moveArm(Constants.ball1);
                currentPosition = aimedPosition;
            }
            manualWrist();
                break;
            case BALL2:
            if(positionRecognizer(aimedPosition))
            {
                currentPosition = aimedPosition;
            }
            else
            {
                moveArm(Constants.ball2);
                currentPosition = aimedPosition;
            }
            manualWrist();
                break;
            case BALL3:
            if(limitSwitch2.get())
            {
                moveArm(Constants.ball3);
                currentPosition = aimedPosition;
             
            }
            else
            {
                armMaster.set(ControlMode.PercentOutput,0);
                currentPosition = aimedPosition;
            }
            manualWrist();
            
                break;
            case HATCH1:
            if(positionRecognizer(aimedPosition))
            {
                currentPosition = aimedPosition;
            }
            else
            {
                moveArm(Constants.hatch1);
                currentPosition = aimedPosition;
            }
            manualWrist();
                break;
            case HATCH2:
            if(positionRecognizer(aimedPosition))
            {
                currentPosition = aimedPosition;
            }
            else
            {
                moveArm(Constants.hatch2);
                currentPosition = aimedPosition;
            }
            manualWrist();
                break;
            case HATCH3:
<<<<<<< HEAD
            if(limitSwitch2.get()){
                moveArm(Constants.hatch3);
=======
            if(positionRecognizer(aimedPosition))
            {
>>>>>>> WOrking on limelight code
                currentPosition = aimedPosition;
             
            }
<<<<<<< HEAD
            else{
                armMaster.set(ControlMode.PercentOutput,0);
=======
            else
            {
                moveArm(Constants.hatch3);
>>>>>>> WOrking on limelight code
                currentPosition = aimedPosition;
            }
            manualWrist();
             break;
            case HATCHDOWN:
                moveArm(encoderPos + Constants.hatchMovement);
                break;
            case HATCHUP:
                moveArm(encoderPos - Constants.hatchMovement);
                break;

        }
    }
    
    public static void resetLift()
    {
        if (limitSwitch1.get() && armMaster.getSelectedSensorPosition(0)<=-500)
        {
            armMaster.set(ControlMode.PercentOutput,.6);
        }
        else if (limitSwitch1.get() && armMaster.getSelectedSensorPosition(0)>=-500)
        {
            armMaster.set(ControlMode.PercentOutput,.3);
        }
        else
        {
            armMaster.set(ControlMode.PercentOutput,0);
            armMaster.setSelectedSensorPosition(0);
            //System.out.println("stopped");
        }
    }
    public static void moveArm(int position)
    {
        armMaster.set(ControlMode.MotionMagic,position);
    }
    public static void moveManual()
    {
        double controllerValue = OI.controller2.getY(Hand.kLeft);
        if (Math.abs(controllerValue)>=.09)
        {
            armMaster.set(ControlMode.PercentOutput,controllerValue);
        }
        else
        {
            armMaster.set(ControlMode.PercentOutput,0);
        }
        
    }
    public static void moveWrist(int targetEncoderPosition)
    {
        int movingWrist = targetEncoderPosition + encoderPos;
        wristMotor.set(ControlMode.MotionMagic,movingWrist);

    }
    public static void manualWrist()
    {
        if (Math.abs(OI.controller2.getY(Hand.kRight))>=.05)
        {
            wristMotor.set(ControlMode.PercentOutput,-OI.controller2.getY(Hand.kRight));
        }
        else
        {
            wristMotor.set(ControlMode.PercentOutput,0);
        }
    }
    public static void hatchPickup(boolean up){
        int setPosition = 0;
        if (up){
            setPosition = armMaster.getSelectedSensorPosition(0) - Constants.hatchMovement;
        }
        else{
            setPosition = armMaster.getSelectedSensorPosition(0) + Constants.hatchMovement;
        }
        moveArm(setPosition);
    }
   
}
