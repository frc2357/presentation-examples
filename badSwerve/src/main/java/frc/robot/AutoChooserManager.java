package frc.robot;

import choreo.auto.AutoChooser;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutoBadPose;
import frc.robot.commands.AutoBadZero;
import frc.robot.commands.AutoIntake;
import frc.robot.commands.GoodAuto;

import java.util.Map;

public class AutoChooserManager {

  // The map of named commands we use in choreo
  private Map<String, Command> m_autoCommandsToBind = Map.of(
      "Auto Intake", new AutoIntake());

  private AutoChooser m_autoChooser = new AutoChooser();

  public AutoChooserManager() {
    SendableBuilderImpl autoChooserBuilder = new SendableBuilderImpl();
    autoChooserBuilder.setTable(
        NetworkTableInstance.getDefault().getTable("SmartDashboard/Auto chooser"));

    m_autoChooser.initSendable(autoChooserBuilder);

    m_autoCommandsToBind.forEach((String name, Command command) -> {
      m_autoChooser.addCmd(name, () -> command);
    });

    m_autoChooser.addRoutine("Good Auto", new GoodAuto()::getRoutine);
    m_autoChooser.addRoutine("Auto with Bad Pose", new AutoBadPose()::getRoutine);
    m_autoChooser.addRoutine("Auto with Bad Zero", new AutoBadZero()::getRoutine);

    SmartDashboard.putData("Auto chooser", m_autoChooser);
    SmartDashboard.putNumber("wait seconds", 0.0);
  }

  public Command getSelectedCommandScheduler() {
    return m_autoChooser
        .selectedCommandScheduler()
        .finallyDo(() -> Robot.swerve.stopMotors()); // no touchy.
  }

  public Command getSelectedCommand() {
    return m_autoChooser
        .selectedCommand()
        .finallyDo(() -> Robot.swerve.stopMotors()); // also no touchy.
  }

  public String selectAuto(String autoToSelect) {
    if (autoToSelect == null) {
      return "Null string was given";
    }
    return m_autoChooser.select(autoToSelect);
  }
}
