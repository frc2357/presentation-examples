package frc.robot.commands;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Robot;

public class AutoBadPose {

  public static final AutoFactory AUTO_FACTORY = new AutoFactory(
      Robot.swerve::getFieldRelativePose2d,
      Robot.swerve::setFieldRelativePose2d,
      Robot.swerve::followChoreoPath,
      true,
      Robot.swerve);

  protected AutoRoutine m_routine;
  protected AutoTrajectory m_startTraj;

  public AutoBadPose() {

    m_routine = AUTO_FACTORY.newRoutine("Auto Bad Pose");

    AutoTrajectory startTraj = m_routine.trajectory("path1");
    AutoTrajectory secondTraj = m_routine.trajectory("path2");

    m_routine
        .active()
        .onTrue(
            Commands.sequence(
                startTraj.cmd(), secondTraj.cmd()));
  }

  public AutoRoutine getRoutine() {
    return m_routine;
  }
}
