package first.robot.mechanisms.pivot;

import static org.wpilib.units.Units.Degrees;

import org.wpilib.units.measure.Angle;

public final class PivotConstants {
    public static final int SERVO_PORT = 4;
    public static final Angle PIVOT_POSITION_TOLERANCE = Degrees.of(1);


    public static final Angle PIVOT_SCORE_POSITION = Degrees.of(130);
    public static final Angle PIVOT_HOME_POSITION = Degrees.of(90);
}
