package first.robot.opmodes;

import org.wpilib.opmode.Autonomous;
import org.wpilib.opmode.PeriodicOpMode;
import first.robot.Robot;

/**
 * Example OpMode that uses Thread.sleep to halt the robot program
 * 
 * In the XRP simulation, this OpMode does nothing.
 * On actual robots, it will start the drive, and keep running it in a loop
 * This will cause other running commands / processes to halt, and delay the transition from 
 * auto to teleop as the program completes the "periodic" function call
 */
@Autonomous(name = "Halting Auto")
public class HaltingAuto extends PeriodicOpMode {
  private final Robot m_robot;

  /** The Robot instance is passed into the opmode via the constructor. */
  public HaltingAuto(Robot robot) {
    m_robot = robot;

  }

  @Override
  public void periodic() {
    m_robot.drive.arcadeDrive(0.5, 0);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
    }

    m_robot.drive.arcadeDrive(0, 0.25);

    try {
      Thread.sleep(15000);
    } catch (InterruptedException e) {
    }

    m_robot.drive.arcadeDrive(0, 0);

  }
}
