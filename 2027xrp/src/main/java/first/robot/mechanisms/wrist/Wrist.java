package first.robot.mechanisms.wrist;

import static org.wpilib.units.Units.Degrees;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.math.util.MathUtil;
import org.wpilib.units.measure.Angle;
import org.wpilib.xrp.XRPServo;

public class Wrist implements Mechanism {
    private final XRPServo armServo;

    /** Creates a new Pivot. */
    public Wrist() {
        // Device number 4 maps to the physical Servo 1 port on the XRP
        armServo = new XRPServo(WristConstants.SERVO_PORT);
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
        }).named("Set Arm Angle To " + angle.in(Degrees));
    }

    public Command goToScorePosition() {
        return setAngle(WristConstants.WRIST_SCORE_POSITION);
    }

    public Command goToHomePosition() {
        return setAngle(WristConstants.WRIST_HOME_POSITION);
    }


    public boolean atAngle(Angle angle) {
        return MathUtil.isNear(angle.in(Degrees), armServo.getAngle(),
                    WristConstants.WRIST_POSITION_TOLERANCE.in(Degrees));
    }
}
