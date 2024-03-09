// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveConstants.joysticks;
import frc.robot.auto.*;
import frc.robot.auto.plans.*;
import frc.robot.commands.Feeder.*;
import frc.robot.commands.Hanger.*;
import frc.robot.commands.Intake.*;
import frc.robot.commands.Shooter.*;
import frc.robot.commands.TeleopCmd;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.HangerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.utils.ControllerUtils;

public class RobotContainer {
  // Controller Utils Instance
  private final ControllerUtils cutil = new ControllerUtils();

  // Auto Dropdown - Make dropdown variable and variables to be selected
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final String auto1 = "1";
  private final String auto2 = "2";
  private final String auto3 = "3";
  private final String auto4 = "4";

  // Subsystems
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final LimelightSubsystem limelight = new LimelightSubsystem();
  private final HangerSubsystem hanger = new HangerSubsystem();
  private final FeederSubsystem feeder = new FeederSubsystem();

  // Commands
  private final TeleopCmd teleopCmd =
      new TeleopCmd(
          drivetrain,
          () -> cutil.Boolsupplier(Controllers.ps4_LB, DriveConstants.joysticks.DRIVER));

  // Auto Commands
  private final MidSpeakerAuto backAndShootAuto =
      new MidSpeakerAuto(drivetrain, intake, feeder, shooter);

  public RobotContainer() {
    // Declare default command during Teleop Period as TeleopCmd(Driving Command)
    drivetrain.setDefaultCommand(teleopCmd);
    // Shooter Controls
    shooter.setDefaultCommand(new ShooterForwardCmd(shooter));

    // Add Auto options to dropdown and push to dashboard
    m_chooser.setDefaultOption("Back And Shoot", auto1);
    m_chooser.addOption("Null1", auto2);
    m_chooser.addOption("Null2", auto3);
    m_chooser.addOption("Null3", auto4);
    SmartDashboard.putData("Auto Selector", m_chooser);
    SmartDashboard.putNumber("Auto Wait Time (Sec)", 0);

    // Configure Buttons Methods
    configureBindings();
  }

  private void configureBindings() {
    // Configure buttons
    // Prior Reference:
    // https://github.com/OysterRiverOverdrive/Charged-Up-2023-Atlas_Chainsaw/blob/main/src/main/java/frc/robot/RobotContainer.java

    // Hangers Up
    cutil.POVsupplier(0, joysticks.OPERATOR).onTrue(new HangerUpCmd(hanger));

    // Hangers Down
    cutil.POVsupplier(180, joysticks.OPERATOR).onTrue(new HangerDownCmd(hanger));

    // Zero Heading
    cutil
        .supplier(Controllers.ps4_RB, DriveConstants.joysticks.DRIVER)
        .onTrue(new InstantCommand(() -> drivetrain.zeroHeading()));

    // Feeder to Shooter
    cutil
        .supplier(Controllers.ps4_RB, DriveConstants.joysticks.OPERATOR)
        .onTrue(new ToShooterCmd(feeder))
        .onFalse(new StopFeederCmd(feeder));

    // Feeder Out
    cutil
        .supplier(Controllers.ps4_share, DriveConstants.joysticks.OPERATOR)
        .onTrue(new OutFeederCmd(feeder))
        .onFalse(new StopFeederCmd(feeder));

    // Intaking - Feeder in and Intake in
    cutil
        .supplier(Controllers.ps4_LB, DriveConstants.joysticks.OPERATOR)
        .onTrue(new ParallelCommandGroup(new InFeederCmd(feeder), new IntakeCmd(intake)))
        .onFalse(new ParallelCommandGroup(new StopFeederCmd(feeder), new IntakeStopCmd(intake)));

    // Intake out
    cutil
        .supplier(Controllers.ps4_options, DriveConstants.joysticks.OPERATOR)
        .onTrue(new OuttakeCmd(intake))
        .onFalse(new IntakeStopCmd(intake));
  }

  public Command getAutonomousCommand() {

    // Prior Reference:
    // https://github.com/OysterRiverOverdrive/Charged-Up-2023-Atlas_Chainsaw/blob/main/src/main/java/frc/robot/RobotContainer.java
    // Get auto dropdown to run
    Command auto;
    switch (m_chooser.getSelected()) {
      default:
      case auto1:
        auto = backAndShootAuto;
        break;
      case auto2:
        auto = null;
        break;
      case auto3:
        auto = null;
        break;
      case auto4:
        auto = null;
        break;
    }
    // Create sequential command with the wait command first then run selected auto
    auto =
        new SequentialCommandGroup(
            new BeginSleepCmd(drivetrain, SmartDashboard.getNumber("Auto Wait Time (Sec)", 0)),
            auto);
    return auto;
  }
}
