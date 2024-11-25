// Basic battery information. Power displays taken from
// edu.wpi.first.wpilibj.examples.canpdp

// The subsystem will handle power-related diagnostics, as well
// as identifying the current battery in the logs.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PowerSubsystem extends SubsystemBase {

  // Battery ID for logging
  private final SendableChooser<String> battery_chooser = new SendableChooser<>();
  private final PowerDistribution m_pdp = new PowerDistribution();
  private String batteryID;

  public PowerSubsystem() {

    // Enumerate all batteries here with a unique name. When this option changes,
    // we'll log this so we can connect log diagnostics with specific batteries.
    battery_chooser.setDefaultOption("Unknown", "Unknown Battery");
    battery_chooser.addOption("2024-1", "2024-1");
    battery_chooser.addOption("2024-2", "2024-2");
    SmartDashboard.putData("Battery Selector", battery_chooser);
  }

  public void periodic() {

    String oldBatteryID = batteryID;
    batteryID = battery_chooser.getSelected();
    if (batteryID != null && !batteryID.equals(oldBatteryID)) {
      DataLogManager.log("Battery selected: " + batteryID);
    }

    // Remaining code is from edu.wpi.first.wpilibj.examples.canpdp.
    // Get the voltage going into the PDP, in Volts.
    // The PDP returns the voltage in increments of 0.05 Volts.
    double voltage = m_pdp.getVoltage();
    SmartDashboard.putNumber("Voltage", voltage);

    // Retrieves the temperature of the PDP, in degrees Celsius.
    double temperatureCelsius = m_pdp.getTemperature();
    SmartDashboard.putNumber("Temperature", temperatureCelsius);

    // Get the total current of all channels.
    double totalCurrent = m_pdp.getTotalCurrent();
    SmartDashboard.putNumber("Total Current", totalCurrent);

    // Get the total power of all channels.
    // Power is the bus voltage multiplied by the current with the units Watts.
    double totalPower = m_pdp.getTotalPower();
    SmartDashboard.putNumber("Total Power", totalPower);

    // Get the total energy of all channels.
    // Energy is the power summed over time with units Joules.
    double totalEnergy = m_pdp.getTotalEnergy();
    SmartDashboard.putNumber("Total Energy", totalEnergy);
  }

  public String getBatteryID() {
    return batteryID;
  }
}
