package first.robot.mechanisms.drivetrain;

import static org.wpilib.units.Units.Inches;
import static org.wpilib.units.Units.Value;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.drive.DifferentialDrive;
import org.wpilib.hardware.rotation.Encoder;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.units.measure.Dimensionless;
import org.wpilib.units.measure.Time;
import org.wpilib.xrp.XRPGyro;
import org.wpilib.xrp.XRPMotor;

public class Drive implements Mechanism {
    // The XRP has the left and right motors set to
    // channels 0 and 1 respectively
    private final XRPMotor m_leftMotor = new XRPMotor(0);
    private final XRPMotor m_rightMotor = new XRPMotor(1);

    // The XRP has onboard encoders that are hardcoded
    // to use DIO pins 4/5 and 6/7 for the left and right
    private final Encoder m_leftEncoder = new Encoder(4, 5);
    private final Encoder m_rightEncoder = new Encoder(6, 7);

    // Set up the differential drive controller
    private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_leftMotor::setThrottle,
            m_rightMotor::setThrottle);

    // Set up the XRPGyro
    private final XRPGyro m_gyro = new XRPGyro();

    public Drive() {
        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        m_rightMotor.setInverted(true);

        // Use inches as unit for encoder distances
        m_leftEncoder.setDistancePerPulse(DriveConstants.ENCODER_DISTANCE_PER_PULSE.in(Inches));
        m_rightEncoder.setDistancePerPulse(DriveConstants.ENCODER_DISTANCE_PER_PULSE.in(Inches));
        resetEncoders();
    }

    public void arcadeDrive(double velocity, double rotate) {
        m_diffDrive.arcadeDrive(velocity, rotate);
    }

    public Command arcadeDrive(Supplier<Dimensionless> velocity, Supplier<Dimensionless> rotate) {
        return this.runRepeatedly(
                () -> {
                    arcadeDrive(
                            velocity.get().in(Value), rotate.get().in(Value));
                })
                .withPriority(Command.LOWEST_PRIORITY)
                .named("Split-Stick Arcade Drive");
    }

    public Command autoDrive(Time time, Dimensionless velocity, Dimensionless rotate) {
        return this.run((coro) -> {
            coro.fork(
                    this.runRepeatedly(() -> arcadeDrive(velocity.in(Value), rotate.in(Value)))
                            .named("Run Drive"));

            coro.wait(time);

            arcadeDrive(0, 0);
        }).named("Auto Drive");
    }

    /**
     * This is an example of a coroutine command that can block robot code
     * and cause loop overruns by doing lots of looping
     * @return
     */
    public Command mathIntensive() {
        return this.run((coro) -> {
            for(long i = 0; i < Long.MAX_VALUE; i++) {
                // Looping long time doing fancy math
            }
        }).named("Math Intensive");
    }

    public void resetEncoders() {
        m_leftEncoder.reset();
        m_rightEncoder.reset();
    }

    public int getLeftEncoderCount() {
        return m_leftEncoder.get();
    }

    public int getRightEncoderCount() {
        return m_rightEncoder.get();
    }

    public double getLeftDistanceInch() {
        return m_leftEncoder.getDistance();
    }

    public double getRightDistanceInch() {
        return m_rightEncoder.getDistance();
    }

    public double getAverageDistanceInch() {
        return (getLeftDistanceInch() + getRightDistanceInch()) / 2.0;
    }

    /**
     * Current angle of the XRP around the X-axis.
     *
     * @return The current angle of the XRP in degrees
     */
    public double getGyroAngleX() {
        return m_gyro.getAngleX();
    }

    /**
     * Current angle of the XRP around the Y-axis.
     *
     * @return The current angle of the XRP in degrees
     */
    public double getGyroAngleY() {
        return m_gyro.getAngleY();
    }

    /**
     * Current angle of the XRP around the Z-axis.
     *
     * @return The current angle of the XRP in degrees
     */
    public double getGyroAngleZ() {
        return m_gyro.getAngleZ();
    }

    /** Reset the gyro. */
    public void resetGyro() {
        m_gyro.reset();
    }

    public void logSpeeds() {
        Telemetry.getTable("Drive")
                .log("right encoder distance inches", m_rightEncoder.getDistance());
        Telemetry.getTable("Drive")
                .log("left encoder distance inches", m_leftEncoder.getDistance());
    }
}
