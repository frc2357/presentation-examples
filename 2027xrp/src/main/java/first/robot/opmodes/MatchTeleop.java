// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.opmodes;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.Value;

import org.wpilib.command3.Command;
import org.wpilib.command3.Trigger;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;
import org.wpilib.tunable.TunableDouble;
import org.wpilib.tunable.Tunables;

import first.robot.Robot;

@Teleop
public class MatchTeleop extends PeriodicOpMode {
  private Robot m_robot;

  private final TunableDouble m_tunablePivotAngle = Tunables.addDouble("pivotAngle", 100);

  /** The Robot instance is passed into the opmode via the constructor. */
  public MatchTeleop(Robot robot) {
    m_robot = robot;
    System.out.println("opmode constructed");
    ConfigureButtonBindings();
    m_robot.drive.setDefaultCommand(m_robot.drive.arcadeDrive(() -> Value.of(-m_robot.driverController.getLeftY()),
        () -> Value.of(-m_robot.driverController.getRightX())));

  }

  public void ConfigureButtonBindings() {
    Trigger userButton = new Trigger(m_robot.xrpIO::getUserButtonPressed);
    userButton
        .onTrue(Command.noRequirements(coro -> System.out.println("User button pressed")).named("u1"))
        .onFalse(Command.noRequirements(coro -> System.out.println("USER Button Released")).named("u2"));

    m_robot.driverController.a().whileTrue(m_robot.arm.setPivotAngle(Degrees.of(90)));
    m_robot.driverController.y().whileTrue(m_robot.arm.setPivotAngle(Degrees.of(130)));

    m_robot.driverController.rightTrigger().whileTrue(m_robot.arm.score());

    m_robot.driverController.x().whileTrue(m_robot.arm.setPivotAngle(() -> Degrees.of(m_tunablePivotAngle.get())));

  }

  @Override
  public void disabledPeriodic() {
    /* Called periodically (on every DS packet) while the robot is disabled. */
  }

  @Override
  public void start() {
    /* Called once when the robot is enabled. */
  }

  @Override
  public void periodic() {
  }

  @Override
  public void end() {
    /* Called when the robot is disabled (after previously being enabled). */
  }

  @Override
  public void close() {
    /*
     * Called when the opmode is de-selected / no additional methods will be called.
     */
  }
}
