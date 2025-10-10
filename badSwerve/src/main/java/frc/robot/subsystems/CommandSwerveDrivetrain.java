package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveRequest;

import choreo.trajectory.SwerveSample;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.CHOREO;
import frc.robot.TunerConstants.TunerSwerveDrivetrain;

import java.util.function.Supplier;

/**
 * Class that extends the Phoenix 6 SwerveDrivetrain class and implements
 * Subsystem so it can easily be used in command-based projects.
 */
public class CommandSwerveDrivetrain
    extends TunerSwerveDrivetrain
    implements Subsystem {

  private final SwerveRequest.FieldCentric m_fieldRelative = new SwerveRequest.FieldCentric()
      .withDriveRequestType(DriveRequestType.Velocity); // Use open-loop control for drive motors;

  private final SwerveRequest.ApplyFieldSpeeds m_fieldSpeedsRequest = new SwerveRequest.ApplyFieldSpeeds()
      .withDriveRequestType(DriveRequestType.Velocity); // Use open-loop control for drive motors;

  /**
   * Constructs a CTRE SwerveDrivetrain using the specified constants.
   * <p>
   * This constructs the underlying hardware devices, so users should not
   * construct
   * the devices themselves. If they need the devices, they can access them
   * through
   * getters in the classes.
   *
   * @param drivetrainConstants Drivetrain-wide constants for the swerve drive
   * @param modules             Constants for each specific module
   */
  public CommandSwerveDrivetrain(
      SwerveDrivetrainConstants drivetrainConstants,
      SwerveModuleConstants<?, ?, ?>... modules) {
    super(drivetrainConstants, modules);
  }

  /**
   * Constructs a CTRE SwerveDrivetrain using the specified constants.
   * <p>
   * This constructs the underlying hardware devices, so users should not
   * construct
   * the devices themselves. If they need the devices, they can access them
   * through
   * getters in the classes.
   *
   * @param drivetrainConstants     Drivetrain-wide constants for the swerve drive
   * @param odometryUpdateFrequency The frequency to run the odometry loop. If
   *                                unspecified or set to 0 Hz, this is 250 Hz on
   *                                CAN FD, and 100 Hz on CAN 2.0.
   * @param modules                 Constants for each specific module
   */
  public CommandSwerveDrivetrain(
      SwerveDrivetrainConstants drivetrainConstants,
      double odometryUpdateFrequency,
      SwerveModuleConstants<?, ?, ?>... modules) {
    super(drivetrainConstants, odometryUpdateFrequency, modules);
  }

  /**
   * Constructs a CTRE SwerveDrivetrain using the specified constants.
   * <p>
   * This constructs the underlying hardware devices, so users should not
   * construct
   * the devices themselves. If they need the devices, they can access them
   * through
   * getters in the classes.
   *
   * @param drivetrainConstants       Drivetrain-wide constants for the swerve
   *                                  drive
   * @param odometryUpdateFrequency   The frequency to run the odometry loop. If
   *                                  unspecified or set to 0 Hz, this is 250 Hz
   *                                  on
   *                                  CAN FD, and 100 Hz on CAN 2.0.
   * @param odometryStandardDeviation The standard deviation for odometry
   *                                  calculation
   *                                  in the form [x, y, theta]ᵀ, with units in
   *                                  meters
   *                                  and radians
   * @param visionStandardDeviation   The standard deviation for vision
   *                                  calculation
   *                                  in the form [x, y, theta]ᵀ, with units in
   *                                  meters
   *                                  and radians
   * @param modules                   Constants for each specific module
   */
  public CommandSwerveDrivetrain(
      SwerveDrivetrainConstants drivetrainConstants,
      double odometryUpdateFrequency,
      Matrix<N3, N1> odometryStandardDeviation,
      Matrix<N3, N1> visionStandardDeviation,
      SwerveModuleConstants<?, ?, ?>... modules) {
    super(
        drivetrainConstants,
        odometryUpdateFrequency,
        odometryStandardDeviation,
        visionStandardDeviation,
        modules);
  }

  /**
   * Returns a command that applies the specified control request to this swerve
   * drivetrain.
   *
   * @param request Function returning the request to apply
   * @return Command to run
   */
  public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
    return run(() -> this.setControl(requestSupplier.get()));
  }

  /**
   * The method to use for field relative driving.
   *
   * @param velocityXMetersPerSecond     The desired speed on the X axis in meters
   *                                     per second.
   * @param velocityYMetersPerSecond     The desired speed on the Y axis in meters
   *                                     per second.
   * @param rotationRateRadiansPerSecond The desired rotation rate in radians per
   *                                     second.
   */
  public void driveFieldRelative(
      double velocityXMetersPerSecond,
      double velocityYMetersPerSecond,
      double rotationRateRadiansPerSecond) {
    setControl(
        m_fieldRelative
            .withVelocityX(velocityXMetersPerSecond)
            .withVelocityY(velocityYMetersPerSecond)
            .withRotationalRate(rotationRateRadiansPerSecond));
  }

  public void stopMotors() {
    driveFieldRelative(0, 0, 0);
    for (SwerveModule<TalonFX, TalonFX, CANcoder> module : super.getModules()) {
      module.getDriveMotor().stopMotor(); // anti-jingle
      module.getSteerMotor().stopMotor(); // remove to bring back the jingle (dont do it)
    }
  }

  public void setPoseAndGyro(Pose2d poseToSet) {
    super.resetPose(poseToSet);
    super.getPigeon2().setYaw(poseToSet.getRotation().getDegrees());
  }

  /**
   * Sets the pose straight as you input it, with no flipping to compensate for
   * alliance.
   * 
   * @param poseToSet The pose it will set.
   */
  public void setFieldRelativePose2d(Pose2d poseToSet) {
    super.resetPose(poseToSet);
  }

  /**
   * Gets the pose, with no flipping to compensate for alliance.
   * 
   * @return The field relative pose.
   */
  public Pose2d getFieldRelativePose2d() {
    return super.getState().Pose;
  }

  public void followChoreoPath(SwerveSample sample) {
    Pose2d pose = getFieldRelativePose2d();
    CHOREO.ROTATION_CONTROLLER.enableContinuousInput(-Math.PI, Math.PI);

    var targetSpeeds = sample.getChassisSpeeds();
    targetSpeeds.vxMetersPerSecond += CHOREO.X_CONTROLLER.calculate(
        pose.getX(),
        sample.x);
    targetSpeeds.vyMetersPerSecond += CHOREO.Y_CONTROLLER.calculate(
        pose.getY(),
        sample.y);
    targetSpeeds.omegaRadiansPerSecond += CHOREO.ROTATION_CONTROLLER.calculate(
        pose.getRotation().getRadians(),
        sample.heading);
    setControl(
        m_fieldSpeedsRequest
            .withSpeeds(targetSpeeds)
            .withWheelForceFeedforwardsX(sample.moduleForcesX())
            .withWheelForceFeedforwardsY(sample.moduleForcesY()));
  }
}
