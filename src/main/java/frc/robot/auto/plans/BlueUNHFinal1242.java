// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto.plans;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.AutoCreationCmd;
import frc.robot.auto.AutoFeederCmd;
import frc.robot.auto.AutoIntakeCmd;
import frc.robot.auto.AutoShooterCmd;
import frc.robot.auto.AutoSleepCmd;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import java.util.List;

public class BlueUNHFinal1242 extends ParallelCommandGroup {
  public BlueUNHFinal1242(
      DrivetrainSubsystem drivetrain,
      IntakeSubsystem intake,
      FeederSubsystem feeder,
      ShooterSubsystem shooter) {
    AutoCreationCmd autodrive = new AutoCreationCmd();
    Rotation2d noRotation = new Rotation2d(0);

    // Auto Driving Commands
    Command RightShoot =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
            new Pose2d(0.3,0, noRotation),
            new Pose2d(0.76, -0.12, new Rotation2d(2 * Math.PI / 3)))
            );

    Command RightNote =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
            new Pose2d(0.156, -0.6, noRotation),
            new Pose2d(0.95, -1.53, noRotation))
            );

    Command ReturnNote =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
            new Pose2d(-.95 / 2, 1.53 / 2, noRotation),
            new Pose2d(-.95, 1.63, noRotation))
            );

    Command Taxi =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(new Pose2d(1, -1, noRotation),
            new Pose2d(1.2, -1.3, new Rotation2d(-2 * Math.PI / 6)))
            );

    Command race =
        autodrive.AutoDriveSpeedVar(
            5.6,
            drivetrain,
            List.of(new Pose2d(1, 0, noRotation),
            new Pose2d(2.3, 0, noRotation))
            );

    addCommands(
        // Drivetrain Sequential
        new SequentialCommandGroup(
            RightShoot,
            new AutoSleepCmd(.5),
            RightNote,
            new AutoSleepCmd(.5),
            ReturnNote,
            new AutoSleepCmd(.5),
            Taxi,
            race),

        // Intake Sequential
        new SequentialCommandGroup(new AutoSleepCmd(0), new AutoIntakeCmd(intake, 15)),

        // Feeder Sequential
        new SequentialCommandGroup(
            new AutoSleepCmd(1),
            new AutoFeederCmd(feeder, true, 0.5),
            new AutoSleepCmd(4.5),
            new AutoFeederCmd(feeder, true, .5)),

        // Shooter Sequential
        new SequentialCommandGroup(
            new AutoShooterCmd(shooter, 1, 0.85, 2),
            new AutoSleepCmd(3),
            new AutoShooterCmd(shooter, 1, 0.85, 2)));
  }
}
