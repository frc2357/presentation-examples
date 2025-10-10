package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class AutoIntake extends ParallelCommandGroup {

  public AutoIntake() {
    super(new IntakeForward(), new IntakeReverse());
  }
}
