// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import java.util.function.Supplier;

public class TeleopCmd extends Command {
  /** Creates a new TeleopCmd. */
  private final DrivetrainSubsystem driveSub;
  // Create a controller object
  private final Joystick controller = new Joystick(DriveConstants.kDrveControllerPort);
  private Supplier<Boolean> fieldOrient;

  public TeleopCmd(DrivetrainSubsystem drives, Supplier<Boolean> fieldOrient) {
    driveSub = drives;
    this.fieldOrient = fieldOrient;
    addRequirements(driveSub);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double ContX =
        MathUtil.applyDeadband(
            -controller.getRawAxis(DriveConstants.kDriveX), DriveConstants.deadzoneDriver);
    double ContY =
        MathUtil.applyDeadband(
            -controller.getRawAxis(DriveConstants.kDriveY), DriveConstants.deadzoneDriver);
    double ContRotate =
        MathUtil.applyDeadband(
            -controller.getRawAxis(DriveConstants.kDriveRotate), DriveConstants.deadzoneDriver);
    if (driveSub.checkDemoMode()) {
      ContX = ContX*DriveConstants.kDemoModeRatio;
      ContY = ContY*DriveConstants.kDemoModeRatio;
      ContRotate = ContRotate*DriveConstants.kDemoModeRatio;
    }
    if (!fieldOrient.get()) {
      driveSub.fieldDrive(ContY, ContX, ContRotate);
    } else {
      driveSub.robotDrive(ContY, ContX, ContRotate);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
