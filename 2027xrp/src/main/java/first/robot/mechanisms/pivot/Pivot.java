package first.robot.mechanisms.pivot;

import static org.wpilib.units.Units.Degrees;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.math.util.MathUtil;
import org.wpilib.units.measure.Angle;
import org.wpilib.xrp.XRPServo;

public class Pivot implements Mechanism {
    private final XRPServo armServo;

    /** Creates a new Pivot. */
    public Pivot() {
        // Device number 4 maps to the physical Servo 1 port on the XRP
        armServo = new XRPServo(PivotConstants.SERVO_PORT);
    }

    /**
     * Set the current angle of the arm (0 - 180 degrees).
     *
     * @param angleDeg Desired arm angle in degrees
     */
    private void setServoAngle(Angle angleDeg) {
        armServo.setAngle(angleDeg.in(Degrees));
    }

    public Command setAngle(Angle angle) {
        return this.run((coro) -> {
            setServoAngle(angle);

            coro.waitUntil(() -> atAngle(angle));
        }).named("Set Arm Angle " + angle.in(Degrees));
    }

    public Command setAngle(Supplier<Angle> angleGetter
    ) {
        return this.run((coro) -> {
            Angle angle = angleGetter.get();
            setServoAngle(angle);

            coro.waitUntil(() -> atAngle(angle));
        }).named("Set Arm Angle Variable Degrees");
    }

    public Command goToScorePosition() {
        return setAngle(PivotConstants.PIVOT_SCORE_POSITION);
    }

    public Command goToHomePosition() {
        return setAngle(PivotConstants.PIVOT_HOME_POSITION);
    }

    public boolean atAngle(Angle angle) {
        return MathUtil.isNear(angle.in(Degrees), armServo.getAngle(),
                PivotConstants.PIVOT_POSITION_TOLERANCE.in(Degrees));
    }
}
