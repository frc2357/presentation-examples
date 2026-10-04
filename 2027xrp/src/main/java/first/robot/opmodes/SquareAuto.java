// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.opmodes;

import static org.wpilib.units.Units.Milliseconds;
import static org.wpilib.units.Units.Value;

import org.wpilib.command3.Command;
import org.wpilib.command3.Trigger;
import org.wpilib.driverstation.RobotState;
import org.wpilib.opmode.Autonomous;
import org.wpilib.opmode.PeriodicOpMode;
import first.robot.Robot;

@Autonomous(name = "Square Auto", group = "Group 1")
public class SquareAuto extends PeriodicOpMode {
  private final Trigger enabled = new Trigger(RobotState::isEnabled);
  private final Robot m_robot;

  /** The Robot instance is passed into the opmode via the constructor. */
  public SquareAuto(Robot robot) {
    m_robot = robot;

    enabled.onTrue(squareAuto());
  }

  public Command squareAuto() {
    return Command.noRequirements((coro) -> {
    coro.await(m_robot.drive.autoDrive(Milliseconds.of(500), Value.of(1), Value.of(0)));

    coro.await(m_robot.drive.autoDrive(Milliseconds.of(250), Value.of(0), Value.of(1)));

    coro.await(m_robot.drive.autoDrive(Milliseconds.of(500), Value.of(1), Value.of(0)));

    coro.await(m_robot.drive.autoDrive(Milliseconds.of(250), Value.of(0), Value.of(1)));

    coro.await(m_robot.drive.autoDrive(Milliseconds.of(500), Value.of(1), Value.of(0)));

    coro.await(m_robot.drive.autoDrive(Milliseconds.of(250), Value.of(0.), Value.of(1)));
    
    coro.await(m_robot.drive.autoDrive(Milliseconds.of(500), Value.of(1), Value.of(0)));
    }).named("Square Auto");
  }
}
