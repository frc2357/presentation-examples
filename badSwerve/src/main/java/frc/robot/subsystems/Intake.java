package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private SparkMax m_motor;

  public Intake() {
    m_motor = new SparkMax(Constants.CAN_ID.INTAKE, MotorType.kBrushless);
  }

  public void set(double percent) {
    m_motor.set(percent);
  }

  public void stop() {
    set(0);
  }

}
