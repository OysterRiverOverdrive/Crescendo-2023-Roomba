// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final int BumperMotor = 9;
  public static final int RollerMotor = 10;

  public static final class AutoConstants {

    public static final double kMaxSpeedMetersPerSecond = 4.8 / 2;
    public static final double kMaxAngularSpeedRadiansPerSecond = (2 * Math.PI) / 4;
    public static final double kMaxAccelerationMetersPerSecondSquared = 6;
    public static final double kMaxAngularAccelerationRadiansPerSecondSquared = Math.PI / 2;
    public static final double kPXController = 8;
    public static final double kPYController = 8;
    public static final double kPThetaController = 5;

    public static final Rotation2d noRotation = new Rotation2d(0);

    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
        new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularAccelerationRadiansPerSecondSquared);
  }

  // Constants specifically for Driving & Operation
  public static class DriveConstants {
    // Controller Ports ---
    // Determined here but assigned in the driver station to determine and organize physical ports
    // the controllers are plug into
    public static final int kDrveControllerPort = 0;
    public static final int kOperControllerPort = 1;

    // Driver Controller Joystick ---
    public static final int kDriveX = 0;
    public static final int kDriveY = 1;
    public static final int kDriveRotate = 4;
    public static final double deadzoneDriver = 0.12;

    // Speed Mode Strings
    // Moved from DrivetrainSubsystem
    public static final String low = "speed1";
    public static final String medium = "speed2";
    public static final String high = "speed3";

    public enum joysticks {
      DRIVER,
      OPERATOR
    }

    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds

    public static final double kMaxSpeedMetersPerSecond = 6.8;
    public static final double kMaxAngularSpeed = 1.25 * 2 * Math.PI; // radians per second

    // Drive Mode Speeds
    // High
    public static final double kSpeedHighDrive = 6.0;
    public static final double kSpeedHighTurn = kMaxAngularSpeed;

    // Medium is Default Speeds

    // Slow
    public static final double kSpeedSlowDrive = 2.1;
    public static final double kSpeedSlowTurn = 1.8;

    public static final double kDirectionSlewRate = 4; // radians per second
    public static final double kMagnitudeSlewRate = 2; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 5; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(26.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(26.5);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics =
        new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));
  }

  // Constants specifically for the physical robot
  public static final class RobotConstants {
    // SPARK MAX CAN IDs
    public static final int kFrontRightDrivingCanId = 1;
    public static final int kFrontRightTurningCanId = 2;

    public static final int kFrontLeftDrivingCanId = 3;
    public static final int kFrontLeftTurningCanId = 4;

    public static final int kRearRightDrivingCanId = 5;
    public static final int kRearRightTurningCanId = 6;

    public static final int kRearLeftDrivingCanId = 7;
    public static final int kRearLeftTurningCanId = 8;

    public static final int kShooterLeftCanId = 13;
    public static final int kShooterRightCanId = 14;

    public static final int kAmpArmCanId = 15;
    public static final double kAmpArmGearRatio =
        27; // 27:1, Motor needs to spin 27 times for the arm to spin once
    public static final double kAmpArmDegreesOut = 121.5;
    public static final double kAmpArmTrigActivate = 0.10;
    public static final double kAmpArmP = 1.1;
    public static final double kAmpArmI = 0.03;
    public static final double kAmpArmD = 0.0;

    public static final int FeederLeftCanId = 11;
    public static final int FeederRightCanId = 12;

    public static final double FeederInSpeed = 0.3;
    public static final double FeederOutSpeed = -0.18;
    public static final double FeederToShooterSpeed = 0.45;

    public static final int kPneumaticHubCanId = 50;

    // PCM Ports
    public static final int kHangerRightFwd = 0;
    public static final int kHangerRightBck = 1;
    public static final int kHangerLeftFwd = 2;
    public static final int kHangerLeftBck = 3;

    // Minimum PSI for two actuations
    public static final double kMinActuationPSI = 80;

    // Used to declare Navx as upside down
    public static final boolean kGyroReversed = true;

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    public static final int LimitSwtichActivation = 193;
    // intake motor speeds
    public static final double intakeMotorForward = 0.9;
    public static final double intakeMotorBackward = -0.9;
  }

  // Constants specifically for Swerve Module
  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth will result in a
    // robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 13;

    // Invert the turning encoder, since the output shaft rotates in the opposite direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean kTurningEncoderInverted = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the
    // bevel pinion
    public static final double kDrivingMotorReduction =
        (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps =
        (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters) / kDrivingMotorReduction;
    public static final double kDrivingEncoderPositionFactor =
        (kWheelDiameterMeters * Math.PI) / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor =
        ((kWheelDiameterMeters * Math.PI) / kDrivingMotorReduction) / 60.0; // meters per second
    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor =
        (2 * Math.PI) / 60.0; // radians per second
    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput =
        kTurningEncoderPositionFactor; // radians

    // PID Driving Values ---
    // Most likely used to act as a form of slew
    // See:
    // https://docs.wpilib.org/en/stable/docs/software/advanced-controls/filters/slew-rate-limiter.html
    public static final double kDrivingP = 0.04;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMaxOutput = 1;
    public static final double kDrivingMinOutput = kDrivingMaxOutput * -1;
    public static final double kTurningP = 1;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMaxOutput = 1;
    public static final double kTurningMinOutput = kTurningMaxOutput * -1;

    // Swerve Module Idle Modes ---
    // These determine how the module behavior when there is a lack of input from the driver
    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    // Current limits ---
    // Meant for the electrical side of the drivetrain to make sure that the drivetrain isn't
    // drawing too much power
    public static final int kDrivingMotorCurrentLimit = 40; // amps
    public static final int kTurningMotorCurrentLimit = 15; // amps
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  // timerValues
  public static final class TimerConstants {
    // from timer from when autonomous starts to autonmous ends
    public static final int AutoTimerLength = 15; // seconds
    // timer that starts when autonomous ends until Halftime
    public static final int TeleOpStartTimerLength = 75; // seconds
    // timer that starts when Halftime untill match ends
    public static final int TeleOpEndTimerLength = 75; // seconds
  }
}
