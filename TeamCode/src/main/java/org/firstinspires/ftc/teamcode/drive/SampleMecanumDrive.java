package org.firstinspires.ftc.teamcode.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.drive.DriveSignal;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.followers.HolonomicPIDVAFollower;
import com.acmerobotics.roadrunner.followers.TrajectoryFollower;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MeetCode.Hardware;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceRunner;
import org.firstinspires.ftc.teamcode.util.LynxModuleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MOTOR_VELO_PID;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.RUN_USING_ENCODER;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TRACK_WIDTH;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.encoderTicksToInches;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kA;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kStatic;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.kV;

/*
 * Simple mecanum drive hardware implementation for REV hardware.
 */
@Config
public class SampleMecanumDrive extends MecanumDrive {
    public DcMotor cascadeMotorRight, cascadeMotorLeft, arm;
    public TouchSensor touchRight, touchLeft;
    public ColorSensor colorSensorRight, colorSensorLeft, colorSensor;
    public Servo claw;
    public Servo wrist, dropper, launch;
    public CRServo launcherLeft, launcherRight;
    public DistanceSensor distanceLeft, distanceRight;

    HardwareMap hardwareMap;
    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(18, 0, 0.015);
    public static PIDCoefficients HEADING_PID = new PIDCoefficients(-16, 0, 0);

    public static double LATERAL_MULTIPLIER = 1.44;

    public static double VX_WEIGHT = 1;
    public static double VY_WEIGHT = 1;
    public static double OMEGA_WEIGHT = 1;

    private TrajectorySequenceRunner trajectorySequenceRunner;

    private static final TrajectoryVelocityConstraint VEL_CONSTRAINT = getVelocityConstraint(MAX_VEL, MAX_ANG_VEL, TRACK_WIDTH);
    private static final TrajectoryAccelerationConstraint ACCEL_CONSTRAINT = getAccelerationConstraint(MAX_ACCEL);

    private TrajectoryFollower follower;

    public DcMotorEx frontLeft;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;
    public DcMotorEx frontRight;
    private List<DcMotorEx> motors;

    private IMU imu;
    private VoltageSensor batteryVoltageSensor;

    private List<Integer> lastEncPositions = new ArrayList<>();
    private List<Integer> lastEncVels = new ArrayList<>();
    static final double COUNTS_PER_MOTOR_REV = 537.7;

    static final double COUNTS_PER_CASCADE_REV = 384.5;
    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    static final double PULLEY_DIAMETER = 1.503937;

    static final double COUNTS_PER_INCH_CASCADE = (COUNTS_PER_CASCADE_REV * DRIVE_GEAR_REDUCTION)/
            (PULLEY_DIAMETER * Math.PI);
    //81.3798392071

    public ElapsedTime timer = new ElapsedTime();

    public SampleMecanumDrive(HardwareMap hardwareMap) {
        super(kV, kA, kStatic, TRACK_WIDTH, TRACK_WIDTH, LATERAL_MULTIPLIER);
        this.hardwareMap = hardwareMap;
        follower = new HolonomicPIDVAFollower(TRANSLATIONAL_PID, TRANSLATIONAL_PID, HEADING_PID,
                new Pose2d(0.5, 0.5, Math.toRadians(5.0)), 0.5);

        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap);

        batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();



        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // TODO: adjust the names of the following hardware devices to match your configuration




        frontLeft = hardwareMap.get(DcMotorEx.class, "lf");
        backLeft = hardwareMap.get(DcMotorEx.class, "lb");
        backRight = hardwareMap.get(DcMotorEx.class, "rb");
        frontRight = hardwareMap.get(DcMotorEx.class, "rf");
        cascadeMotorRight = hardwareMap.dcMotor.get("cascadeRight");
        cascadeMotorLeft = hardwareMap.dcMotor.get("cascadeLeft");
        arm = hardwareMap.dcMotor.get("arm");
        //servos

        wrist = hardwareMap.servo.get("wrist");
        claw = hardwareMap.servo.get("claw");
        dropper = hardwareMap.servo.get("dropper");
        launch = hardwareMap.servo.get("launch");
//            launcherLeft = hwMap.crservo.get("launcherLeft");
//            launcherRight = hwMap.crservo.get("launcherRight");


        //sensors
        distanceLeft = (DistanceSensor)(hardwareMap.get("distanceLeft"));
        distanceRight = (DistanceSensor)(hardwareMap.get("distanceRight"));
        colorSensorRight = hardwareMap.colorSensor.get("colorSensorRight");
        colorSensorLeft = hardwareMap.colorSensor.get("colorSensorLeft");
        //colorSensor = hwMap.colorSensor.get("colorSensor");

        //reversals
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        cascadeMotorRight.setDirection(DcMotor.Direction.REVERSE);

        //braking

        cascadeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cascadeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = Arrays.asList(frontLeft, backLeft, backRight, frontRight);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }

        if (RUN_USING_ENCODER) {
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (RUN_USING_ENCODER && MOTOR_VELO_PID != null) {
            setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, MOTOR_VELO_PID);
        }

        // TODO: reverse any motors using DcMotor.setDirection()

        List<Integer> lastTrackingEncPositions = new ArrayList<>();
        List<Integer> lastTrackingEncVels = new ArrayList<>();

        // TODO: if desired, use setLocalizer() to change the localization method
        setLocalizer(new StandardTrackingWheelLocalizer(hardwareMap, lastTrackingEncPositions, lastTrackingEncVels));



        trajectorySequenceRunner = new TrajectorySequenceRunner(
                follower, HEADING_PID, batteryVoltageSensor,
                lastEncPositions, lastEncVels, lastTrackingEncPositions, lastTrackingEncVels
        );
    }




    public void setPowerOfAllMotorsTo(double power) {
        backLeft.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        frontRight.setPower(power);
    }
    public void turnOnEncoders(){
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    public void turnOffEncoders(){
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void encoderDrive(double inches){

        resetEncoders();

        turnOnEncoders();

        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        frontRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));


        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(backLeft.isBusy()) {
            setPowerOfAllMotorsTo(.8);
        }
        setPowerOfAllMotorsTo(0);
        resetEncoders();


    }
    //    public void encoderStrafe(double inches){
    //        resetEncoders();
    //
    //        turnOnEncoders();
    //
    //        backLeft.setTargetPosition((int)(-inches * COUNTS_PER_INCH));
    //        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
    //        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
    //        frontRight.setTargetPosition((int)(-inches * COUNTS_PER_INCH));
    //
    //
    //        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //
    //        setPowerOfAllMotorsTo(.8);
    //
    //    }
    //    public void encoderTurn(double inches){
    //        resetEncoders();
    //
    //        turnOnEncoders();
    //
    //        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
    //        backRight.setTargetPosition((int)(-inches * COUNTS_PER_INCH));
    //        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
    //        frontRight.setTargetPosition((int)(-inches * COUNTS_PER_INCH));
    //
    //
    //        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //
    //        setPowerOfAllMotorsTo(.8);
    //    }
    public void resetEncoders(){
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    public void resetEncodersCascade(){
        cascadeMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cascadeMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void turnOnEncodersCascade() {

        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void turnOffEncodersCascade() {

        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void cascadeDrive (double position){

        turnOnEncodersCascade();

        cascadeMotorRight.setTargetPosition((int)(position));
        cascadeMotorLeft.setTargetPosition((int)(position));


        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (cascadeMotorLeft.isBusy() || cascadeMotorRight.isBusy()) {
            //            telemetry.addData("CascadeLeft: ", cascadeMotorLeft.getCurrentPosition());
            //            telemetry.addData("CascadeRight: ", cascadeMotorRight.getCurrentPosition());
            //            telemetry.update();
            cascadeMotorLeft.setPower(.6);
            cascadeMotorRight.setPower(.6);

        }
        cascadeMotorLeft.setPower(0);
        cascadeMotorRight.setPower(0);

        cascadeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cascadeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void cascadeLock (double position) {

        turnOnEncodersCascade();



        cascadeMotorRight.setTargetPosition((int)(position));
        cascadeMotorLeft.setTargetPosition((int)(position));


        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        //            telemetry.addData("CascadeLeft: ", cascadeMotorLeft.getCurrentPosition());
        //            telemetry.addData("CascadeRight: ", cascadeMotorRight.getCurrentPosition());
        //            telemetry.update();
        cascadeMotorRight.setPower(1);
        cascadeMotorLeft.setPower(1);





        cascadeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        cascadeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void squareUp(){
        if(distanceLeft.getDistance(DistanceUnit.MM) > distanceRight.getDistance(DistanceUnit.MM)){
            while(distanceLeft.getDistance(DistanceUnit.MM) > distanceRight.getDistance(DistanceUnit.MM)){
                frontRight.setPower(.15);
                backRight.setPower(.15);
            }
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        else if(distanceRight.getDistance(DistanceUnit.MM) > distanceLeft.getDistance(DistanceUnit.MM)){
            while(distanceRight.getDistance(DistanceUnit.MM) > distanceLeft.getDistance(DistanceUnit.MM)){
                frontLeft.setPower(.15);
                backLeft.setPower(.15);
            }
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }

            /*setPowerOfAllMotorsTo(0);
            if (distanceLeft.getDistance(DistanceUnit.INCH) > distance - .1 && distanceLeft.getDistance(DistanceUnit.INCH) < distance+.1){
                frontRight.setPower(0);
                backRight.setPower(0);
                while (distanceRight.getDistance(DistanceUnit.INCH) > distance){
                    backLeft.setPower(.2);
                    frontLeft.setPower(.2);
                }
                backLeft.setPower(0);
                frontLeft.setPower(0);
            }
            if (distanceRight.getDistance(DistanceUnit.INCH) > distance - .1 && distanceRight.getDistance(DistanceUnit.INCH) < distance+.1){
                frontLeft.setPower(0);
                backLeft.setPower(0);
                while (distanceLeft.getDistance(DistanceUnit.INCH) > distance){
                    backRight.setPower(.2);
                    frontRight.setPower(.2);
                }
                backRight.setPower(0);
                frontRight.setPower(0);
            }*/

    }
    public void strafeRightForTime(double power, double time) {
        timer.reset();
        while (timer.seconds() < time) {
            backLeft.setPower(-power);
            backRight.setPower(power);
            frontLeft.setPower(power);
            frontRight.setPower(-power);
        }
    }

    public void squareUpColor(String color){
        if (color.equals("Blue")) {
            while(colorSensorLeft.blue() < (colorSensorLeft.red() + colorSensorLeft.green()) || colorSensorRight.blue() < (colorSensorRight.red() + colorSensorRight.green())) {
                setPowerOfAllMotorsTo(.2);
                if (colorSensorLeft.blue() > (colorSensorLeft.red() + colorSensorLeft.green())){
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    break;
                }
                else if(colorSensorRight.blue() > (colorSensorRight.red() + colorSensorRight.green())){
                    backLeft.setPower(0);
                    frontLeft.setPower(0);
                    break;
                }
            }

            setPowerOfAllMotorsTo(0);
        }
        if (color.equals("Red")) {
            while(colorSensorLeft.red() < (colorSensorLeft.blue() + colorSensorLeft.green())) {
                backRight.setPower(.2);
                frontRight.setPower(.2);
            }
            while (colorSensorRight.red() < (colorSensorRight.blue() + colorSensorRight.green())) {
                backLeft.setPower(.2);
                frontLeft.setPower(.2);
            }
        }
    }
    public void encoderStrafeRight(double inches){
        resetEncoders();
        turnOnEncoders();

        backLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        frontRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy()) {
            setPowerOfAllMotorsTo(.8);
        }
        setPowerOfAllMotorsTo(0);
        resetEncoders();

    }
    public void encoderStrafeLeft(double inches){
        resetEncoders();
        turnOnEncoders();

        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        backRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
        frontLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
        frontRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy()) {
            setPowerOfAllMotorsTo(.8);
        }
        setPowerOfAllMotorsTo(0);
        resetEncoders();

    }
    public void encoderTurnRight(double inches){
        turnOnEncoders();

        backLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        frontLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
        frontRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy()) {
            setPowerOfAllMotorsTo(.8);
        }
        setPowerOfAllMotorsTo(0);

        resetEncoders();
    }
    public void encoderTurnLeft(double inches){
        turnOnEncoders();

        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        backRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        frontRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy()) {
            setPowerOfAllMotorsTo(.8);
        }
        setPowerOfAllMotorsTo(0);

        resetEncoders();
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose) {
        return new TrajectoryBuilder(startPose, VEL_CONSTRAINT, ACCEL_CONSTRAINT);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, boolean reversed) {
        return new TrajectoryBuilder(startPose, reversed, VEL_CONSTRAINT, ACCEL_CONSTRAINT);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, double startHeading) {
        return new TrajectoryBuilder(startPose, startHeading, VEL_CONSTRAINT, ACCEL_CONSTRAINT);
    }

    public TrajectorySequenceBuilder trajectorySequenceBuilder(Pose2d startPose) {
        return new TrajectorySequenceBuilder(
                startPose,
                VEL_CONSTRAINT, ACCEL_CONSTRAINT,
                MAX_ANG_VEL, MAX_ANG_ACCEL
        );
    }

    public void turnAsync(double angle) {
        trajectorySequenceRunner.followTrajectorySequenceAsync(
                trajectorySequenceBuilder(getPoseEstimate())
                        .turn(angle)
                        .build()
        );
    }

    public void turn(double angle) {
        turnAsync(angle);
        waitForIdle();
    }

    public void followTrajectoryAsync(Trajectory trajectory) {
        trajectorySequenceRunner.followTrajectorySequenceAsync(
                trajectorySequenceBuilder(trajectory.start())
                        .addTrajectory(trajectory)
                        .build()
        );
    }

    public void followTrajectory(Trajectory trajectory) {
        followTrajectoryAsync(trajectory);
        waitForIdle();
    }

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence) {
        trajectorySequenceRunner.followTrajectorySequenceAsync(trajectorySequence);
    }

    public void followTrajectorySequence(TrajectorySequence trajectorySequence) {
        followTrajectorySequenceAsync(trajectorySequence);
        waitForIdle();
    }

    public Pose2d getLastError() {
        return trajectorySequenceRunner.getLastPoseError();
    }

    public void update() {
        updatePoseEstimate();
        DriveSignal signal = trajectorySequenceRunner.update(getPoseEstimate(), getPoseVelocity());
        if (signal != null) setDriveSignal(signal);
    }

    public void waitForIdle() {
        while (!Thread.currentThread().isInterrupted() && isBusy())
            update();
    }

    public boolean isBusy() {
        return trajectorySequenceRunner.isBusy();
    }

    public void setMode(DcMotor.RunMode runMode) {
        for (DcMotorEx motor : motors) {
            motor.setMode(runMode);
        }
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }

    public void setPIDFCoefficients(DcMotor.RunMode runMode, PIDFCoefficients coefficients) {
        PIDFCoefficients compensatedCoefficients = new PIDFCoefficients(
                coefficients.p, coefficients.i, coefficients.d,
                coefficients.f * 12 / batteryVoltageSensor.getVoltage()
        );

        for (DcMotorEx motor : motors) {
            motor.setPIDFCoefficients(runMode, compensatedCoefficients);
        }
    }

    public void setWeightedDrivePower(Pose2d drivePower) {
        Pose2d vel = drivePower;

        if (Math.abs(drivePower.getX()) + Math.abs(drivePower.getY())
                + Math.abs(drivePower.getHeading()) > 1) {
            // re-normalize the powers according to the weights
            double denom = VX_WEIGHT * Math.abs(drivePower.getX())
                    + VY_WEIGHT * Math.abs(drivePower.getY())
                    + OMEGA_WEIGHT * Math.abs(drivePower.getHeading());

            vel = new Pose2d(
                    VX_WEIGHT * drivePower.getX(),
                    VY_WEIGHT * drivePower.getY(),
                    OMEGA_WEIGHT * drivePower.getHeading()
            ).div(denom);
        }

        setDrivePower(vel);
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        lastEncPositions.clear();

        List<Double> wheelPositions = new ArrayList<>();
        for (DcMotorEx motor : motors) {
            int position = motor.getCurrentPosition();
            lastEncPositions.add(position);
            wheelPositions.add(encoderTicksToInches(position));
        }
        return wheelPositions;
    }

    @Override
    public List<Double> getWheelVelocities() {
        lastEncVels.clear();

        List<Double> wheelVelocities = new ArrayList<>();
        for (DcMotorEx motor : motors) {
            int vel = (int) motor.getVelocity();
            lastEncVels.add(vel);
            wheelVelocities.add(encoderTicksToInches(vel));
        }
        return wheelVelocities;
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        frontLeft.setPower(v);
        backLeft.setPower(v1);
        backRight.setPower(v2);
        frontRight.setPower(v3);
    }

    @Override
    public double getRawExternalHeading() {
        return 0;
    }

    @Override
    public Double getExternalHeadingVelocity() {
        return 0.0;
    }

    public static TrajectoryVelocityConstraint getVelocityConstraint(double maxVel, double maxAngularVel, double trackWidth) {
        return new MinVelocityConstraint(Arrays.asList(
                new AngularVelocityConstraint(maxAngularVel),
                new MecanumVelocityConstraint(maxVel, trackWidth)
        ));
    }

    public static TrajectoryAccelerationConstraint getAccelerationConstraint(double maxAccel) {
        return new ProfileAccelerationConstraint(maxAccel);
    }
}