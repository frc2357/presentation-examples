## Example V1 Command

Replicates `setAngle` in the pivot mechanism

V2 commands are commonly found within the subsystem itself

``` java
public Command setAngle(Angle angleDeg) {
        return this.runOnce(() -> setServoAngle(angleDeg))
            .alongWith(new WaitUntilCommand(() -> atAngle(angleDeg)));
    }
```