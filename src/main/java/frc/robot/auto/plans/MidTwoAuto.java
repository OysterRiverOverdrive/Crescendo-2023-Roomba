package frc.robot.auto.plans;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
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

public class MidTwoAuto extends ParallelCommandGroup {

  public MidTwoAuto(
      DrivetrainSubsystem drivetrain,
      IntakeSubsystem intake,
      FeederSubsystem feeder,
      ShooterSubsystem shooter) {
    AutoCreationCmd autodrive = new AutoCreationCmd();

    // Auto Driving Commands

    Command showyDrive1 =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
                new Pose2d(.22, 0, AutoConstants.noRotation),
                new Pose2d(.5, 0, new Rotation2d(0))));
    Command showyDrive2 =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
                new Pose2d(.85, 0, AutoConstants.noRotation),
                new Pose2d(1.30, 0, new Rotation2d(0))));
    Command showyDrive3 =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
                new Pose2d(-.85, .2, AutoConstants.noRotation),
                new Pose2d(-1.25, 0, new Rotation2d(0))));

    Command taxi =
        autodrive.AutoDriveCmd(
            drivetrain,
            List.of(
                new Pose2d(.85, 0, AutoConstants.noRotation),
                new Pose2d(1.30, 0, new Rotation2d(0))));

    addCommands(

        // Driving groups
        new SequentialCommandGroup(
            showyDrive1, new AutoSleepCmd(1), showyDrive2, showyDrive3, new AutoSleepCmd(.5), taxi),

        // Intake group
        new SequentialCommandGroup(new AutoIntakeCmd(intake, 20)),

        // Feeder group
        new SequentialCommandGroup(
            new AutoSleepCmd(1.4), new AutoFeederCmd(feeder, true, .5),
            new AutoSleepCmd(2.9), new AutoFeederCmd(feeder, true, .5)),
        // Shooter group
        new SequentialCommandGroup(
            new AutoSleepCmd(.5), new AutoShooterCmd(shooter, 1, 1, 1.5),
            new AutoSleepCmd(2.3), new AutoShooterCmd(shooter, 1, 1, 1.5)));
  }
}
