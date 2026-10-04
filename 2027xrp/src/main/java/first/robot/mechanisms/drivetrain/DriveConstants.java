package first.robot.mechanisms.drivetrain;

import org.wpilib.units.Units;
import org.wpilib.units.measure.Distance;

public final class DriveConstants {
    public static final double GEAR_RATIO = (30.0 / 14.0) * (28.0 / 16.0) * (36.0 / 9.0) * (26.0 / 8.0);
    public static final double ENCODER_COUNTS_PER_MOTOR_SHAFT_REVOLUTION = 12.0;
    public static final double ENCODER_COUNTS_PER_WHEEL_REVOLUTION = ENCODER_COUNTS_PER_MOTOR_SHAFT_REVOLUTION * GEAR_RATIO;
    public static final Distance WHEEL_DIAMETER = Units.Inches.of(2.3622);
    public static final Distance ENCODER_DISTANCE_PER_PULSE = WHEEL_DIAMETER.times(Math.PI).div(ENCODER_COUNTS_PER_WHEEL_REVOLUTION);

}
