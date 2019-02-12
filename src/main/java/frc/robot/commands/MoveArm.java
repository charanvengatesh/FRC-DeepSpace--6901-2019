/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.subsystems.*;
import frc.robot.subsystems.Lift.Target;

/**
 * Add your docs here.
 */
public class MoveArm extends InstantCommand {
  private static Target position;
  /**
   * Add your docs here.
   */
  public Lift LiftSystem = new Lift();
  public MoveArm(Target position) {
    super();
    
    requires(LiftSystem);
  
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    LiftSystem.setLiftPosition(MoveArm.position);
  }

}
