/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;


/**
 * Add your docs here.
 */
public class Lift extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public TalonSRX armMotorMaster = Robot.armMotorMaster;
  public VictorSPX armMotorSlave = Robot.armMotorSlave;
  //TODO: add limit switch to reset lift
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public enum Target{
    ballLevel1,
    ballLevel2,
    ballLevel3,
    hatchLevel1,
    hatchLevel2,
    hatchLevel3,
    hatchUp,
    hatchDown,
    defaultPosition
  }
  public Lift(){
    armMotorMaster.config_kP(0,Constants.armKp);
    armMotorMaster.config_kI(0,Constants.armKi);
    armMotorMaster.config_kD(0, Constants.armKd);
    armMotorMaster.config_kF(0,Constants.armKf);
    armMotorSlave.follow(armMotorMaster);
    armMotorMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    armMotorMaster.setSelectedSensorPosition(0);
    armMotorMaster.configMotionAcceleration(60);
    armMotorMaster.configMotionCruiseVelocity(200);

  }
  public void setLiftPosition(Target position){
    switch (position){
      case ballLevel1:
        setMotionMagicPosition(Constants.ball1);
      case ballLevel2:
        setMotionMagicPosition(Constants.ball2);
      case ballLevel3:
      setMotionMagicPosition(Constants.ball3);
      case hatchLevel1:
      setMotionMagicPosition(Constants.hatch1);
      case hatchLevel2:
      setMotionMagicPosition(Constants.hatch2);
      case hatchLevel3:
      setMotionMagicPosition(Constants.hatch3);
      case hatchDown:
      setMotionMagicPosition(
        armMotorMaster.getSelectedSensorPosition(0) + Constants.hatchDown
        );
      case hatchUp:
      setMotionMagicPosition(
        armMotorMaster.getSelectedSensorPosition(0) + Constants.hatchUp
        );
      case defaultPosition:
      setMotionMagicPosition(Constants.defaultPosition);
      
    }
  }
  public void setMotionMagicPosition(int encoderValue){
      armMotorMaster.set(ControlMode.MotionMagic,encoderValue);
  }
  
}
  

