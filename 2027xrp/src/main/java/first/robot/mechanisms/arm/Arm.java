package first.robot.mechanisms.arm;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.units.measure.Angle;

import first.robot.mechanisms.pivot.Pivot;
import first.robot.mechanisms.wrist.Wrist;

public class Arm implements Mechanism {
    private final Wrist m_wrist = new Wrist();
    private final Pivot m_pivot = new Pivot();
    

    public Command setPivotAngle(Angle angle) {
        return m_pivot.setAngle(angle);
    }

    public Command setPivotAngle(Supplier<Angle> angle) {
        return m_pivot.setAngle(angle);
    }

    public Command score() {
        return this.run((coro) -> {
            coro.awaitAll(m_wrist.goToScorePosition(), m_pivot.goToScorePosition());
            
            coro.wait(ArmConstants.TIME_TO_SCORE);

            coro.awaitAll(m_wrist.goToHomePosition(), m_pivot.goToHomePosition());
        }).named("Score");
    }
}
