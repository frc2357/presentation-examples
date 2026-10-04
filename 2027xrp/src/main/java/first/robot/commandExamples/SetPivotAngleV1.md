## Example V1 Command

Replicates `setAngle` in the pivot mechanism

``` java
package first.robot.commands;

import org.wpilib.command2.Command;
import org.wpilib.units.measure.Angle;

import first.robot.mechanisms.pivot.Pivot;

public class SetPivotAngle extends Command {
    private Pivot m_pivot;
    private Angle m_angle;
    
    public SetPivotAngle(Pivot pivot, Angle angle) {
        m_pivot = pivot;
        m_angle = angle;

        addRequirements(m_pivot);
    }

    public void initialize() {
        m_pivot.setAngle(m_angle);
    }

    public boolean isFinished() {
        return m_pivot.atAngle(m_angle);
    }
}
```
