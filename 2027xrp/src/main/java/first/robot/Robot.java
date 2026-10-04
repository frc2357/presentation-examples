// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.xrp.XRPOnBoardIO;

import first.robot.mechanisms.arm.Arm;
import first.robot.mechanisms.drivetrain.Drive;

/**
 * The methods in this class are called automatically as described in the
 * OpModeRobot documentation.
 * OpMode classes anywhere in the package (or sub-packages) where this class is
 * located are
 * automatically registered to display in the Driver Station. If you change the
 * name of this class
 * or the package after creating this project, you must also update the
 * Main.java file in the
 * project.
 */
public class Robot extends OpModeRobot {

  public final CommandXboxController driverController = new CommandXboxController(
      ControllerConstants.DRIVER_CONTROLLER_PORT);

  public final Drive drive = new Drive();
  public final Arm arm = new Arm();
  public final XRPOnBoardIO xrpIO = new XRPOnBoardIO();

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  public Robot() {
  }

  /** This function is called exactly once when the DS first connects. */
  @Override
  public void driverStationConnected() {
  }

  /**
   * This function is called periodically anytime when no opmode is selected,
   * including when the
   * Driver Station is disconnected.
   */
  @Override
  public void nonePeriodic() {
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    Scheduler.getDefault().run();
    drive.logSpeeds();
  }

}
