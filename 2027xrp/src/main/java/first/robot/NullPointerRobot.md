## Null Pointer Exception Example

“drive” is not initialized, so the call to “logSpeeds()” causes a Null Pointer Exception


``` Java
package first.robot;

import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.xrp.XRPOnBoardIO;

import first.robot.mechanisms.arm.Arm;
import first.robot.mechanisms.drivetrain.Drive;

public class Robot extends OpModeRobot {

  public final CommandXboxController driverController = new CommandXboxController(
      ControllerConstants.DRIVER_CONTROLLER_PORT);

  public  Drive drive;
  public Arm arm = new Arm();
  public XRPOnBoardIO xrpIO = new XRPOnBoardIO();

  public Robot() {
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getDefault().run();
    drive.logSpeeds();
  }
}
```