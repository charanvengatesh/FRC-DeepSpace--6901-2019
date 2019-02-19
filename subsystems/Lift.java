package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Constants;
import frc.robot.OI;

public class Lift{
    public static WPI_TalonSRX armMaster = new WPI_TalonSRX(Constants.arm1);
    public static VictorSPX armSlave = new VictorSPX(Constants.arm2);
    public static WPI_TalonSRX wristMotor = new WPI_TalonSRX(Constants.wrist);
    public static DigitalInput limitSwitch1 = new DigitalInput(Constants.limitSwitch1);
    public static OI m_oi = new OI();
    public static LiftPosition position = LiftPosition.RESET;
    public static void initializeLift(){
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
    }
    public static enum LiftPosition{
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
    public static void setPosition(){
        
        if(OI.controller2.getAButton()){
            position = LiftPosition.BALL1;
        }
        else if(OI.controller2.getBButton()){
            position = LiftPosition.BALL2;
        }
        else if(OI.controller2.getYButton()){
            position = LiftPosition.BALL3;
        }
        else if(OI.controller2.getXButton()){
            position = LiftPosition.RESET;
        }
        else if(OI.controller2.getPOV()==180){
            position =  LiftPosition.HATCH1;
        }
        else if(OI.controller2.getPOV()==270){
            position = LiftPosition.HATCH2;
        }
        else if(OI.controller2.getPOV()==0){
            position = LiftPosition.HATCH3;
        }
        else if(OI.controller2.getTriggerAxis(Hand.kRight)>0){
            position = LiftPosition.HATCHUP;
        }
        else if(OI.controller2.getTriggerAxis(Hand.kLeft)>0){
            position =  LiftPosition.HATCHDOWN;
        }
        else if(OI.controller2.getY(Hand.kLeft)>=.09){
            position = LiftPosition.MANUAL;
        }
    }
    public static void runArm(){
        
        
        switch(position){
            case MANUAL:
                moveManual();
                break;
            case RESET:
                resetLift();
                break;
            case BALL1:
                moveArm(Constants.ball1);
                break;
            case BALL2:
                moveArm(Constants.ball2);
                break;
            case BALL3:
                moveArm(Constants.ball3);
                moveWrist(Constants.wristUp);
                break;
            case HATCH1:
                moveArm(Constants.hatch1);
                break;
            case HATCH2:
                moveArm(Constants.hatch2);
                break;
            case HATCH3:
                moveArm(Constants.hatch3);
                break;
            case HATCHDOWN:
                hatchPickup(false);
                break;
            case HATCHUP:
                hatchPickup(true);
                break;

        }
    }
    /**Hold Button System */
    
    public static LiftPosition setPositionHold(){
        if(OI.controller2.getAButton()){
            return LiftPosition.BALL1;
        }
        else if(OI.controller2.getBButton()){
            return LiftPosition.BALL2;
        }
        else if(OI.controller2.getYButton()){
            return LiftPosition.BALL3;
        }
        else if(OI.controller2.getXButton()){
            return LiftPosition.RESET;
        }
        else if(OI.controller2.getPOV()==180){
            return  LiftPosition.HATCH1;
        }
        else if(OI.controller2.getPOV()==270){
            return LiftPosition.HATCH2;
        }
        else if(OI.controller2.getPOV()==0){
            return LiftPosition.HATCH3;
        }
        else if(OI.controller2.getTriggerAxis(Hand.kRight)>0){
            return LiftPosition.HATCHUP;
        }
        else if(OI.controller2.getTriggerAxis(Hand.kLeft)>0){
            return  LiftPosition.HATCHDOWN;
        }
        else {
            return LiftPosition.MANUAL;
        }
      
    }
    
    public static void runArmHold(LiftPosition position1){
        switch(position1){
            case MANUAL:
                moveManual();
            case RESET:
                resetLift();
            case BALL1:
                moveArm(Constants.ball1);
            case BALL2:
                moveArm(Constants.ball2);
            case BALL3:
                moveArm(Constants.ball3);
                moveWrist(Constants.wristUp);
            case HATCH1:
                moveArm(Constants.hatch1);
            case HATCH2:
                moveArm(Constants.hatch2);
            case HATCH3:
                moveArm(Constants.hatch3);
            case HATCHDOWN:
                hatchPickup(false);
            case HATCHUP:
                hatchPickup(true);

        }
    }
    
    
    
    
    
    public static void resetLift(){
        if (limitSwitch1.get()){
            armMaster.set(ControlMode.PercentOutput,.6);
          }
        else{
            armMaster.set(ControlMode.PercentOutput,0);
            armMaster.setSelectedSensorPosition(0);
            //System.out.println("stopped");
          }
    }
    public static void moveArm(int position){
        armMaster.set(ControlMode.MotionMagic,position);
    }
    public static void moveManual(){
        if (Math.abs(OI.controller2.getY(Hand.kLeft))>=.09){
            armMaster.set(ControlMode.PercentOutput,-OI.controller2.getY(Hand.kLeft));
        }
        else{
            armMaster.set(ControlMode.PercentOutput,0);
        }
        
    }
    public static void moveWrist(int encoderPosition){
        wristMotor.set(ControlMode.MotionMagic,wristMotor.getSelectedSensorPosition(0)+encoderPosition);

    }
    public static void hatchPickup(boolean up){
        int setPosition = 0;
        if (up){
            setPosition = armMaster.getSelectedSensorPosition(0) + Constants.hatchUp;
        }
        else{
            setPosition = armMaster.getSelectedSensorPosition(0) + Constants.hatchDown;
        }
        moveArm(setPosition);
    }
   
}