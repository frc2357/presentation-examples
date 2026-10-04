package first.robot.mechanisms.wrist;

import static org.wpilib.units.Units.Degrees;

import org.wpilib.units.measure.Angle;

public final class WristConstants {
    public static final int SERVO_PORT = 5;
    public static final Angle WRIST_POSITION_TOLERANCE = Degrees.of(1);

    public static final Angle WRIST_SCORE_POSITION = Degrees.of(130);
    public static final Angle WRIST_HOME_POSITION = Degrees.of(90);
}
