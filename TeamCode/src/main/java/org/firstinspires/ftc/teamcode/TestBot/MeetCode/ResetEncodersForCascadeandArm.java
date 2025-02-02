//package org.firstinspires.ftc.teamcode.TestBot.MeetCode;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
///*
// * This OpMode illustrates the basics of TensorFlow Object Detection,
// * including Java Builder structures for specifying Vision parameters.
// *
// * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
// * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
// */
//@TeleOp(name = "RESET", group = "Concept")
//public class ResetEncodersForCascadeandArm extends LinearOpMode {
//    Hardware robot = new Hardware();
//    private int ticksForCascade = 810;
//    public void runOpMode() {
//
//        robot.init(hardwareMap);
//        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////        robot.arm.setMode(RUN_USING_ENCODER);
////        robot.arm.setTargetPosition(0);
////        robot.arm.setMode(RUN_TO_POSITION);
////        robot.arm.setPower(.3);
////        robot.wrist.setPosition(1);
////        telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition());
////        telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition());
////        telemetry.update();
//        robot.resetEncodersCascade();
//
////        telemetry.addData("arm: ", robot.arm.getCurrentPosition()); //490
////        telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition()); //800
////        telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition()); //800
////        telemetry.update();
//        waitForStart();
////        robot.encoderStrafeRight(60);
//////        robot.claw.setPosition(0.5);
//////        sleep(3000);
//////        robot.claw.setPosition(0);
//////        sleep(1000);
//        int i = 0;
//        while (opModeIsActive()) {
////            if (i == 0) {
////                telemetry.addData("Cascade power: ", robot.cascadeMotorLeft.getPower());
////                telemetry.update();
////                robot.cascadeLock(850);
////                while (robot.cascadeMotorLeft.getCurrentPosition() < 830)
////                    sleep(100);
////                robot.arm.setTargetPosition(585);
////                robot.arm.setMode(RUN_TO_POSITION);
////                robot.arm.setPower(0.3);
////                sleep(1000);
////                robot.claw.setPosition(0.6);
////                sleep(2000);
////                robot.claw.setPosition(0);
////                robot.arm.setTargetPosition(0);
//////                robot.arm.setMode(RUN_TO_POSITION);
//////                robot.arm.setPower(0.3);
////                i++;
////
//////                robot.turnOffEncodersCascade();
////            }
//            telemetry.addData("arm: ", robot.arm.getCurrentPosition()); //490
//            telemetry.addData("Wrist: ", robot.wrist.getPosition());
//            telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition()); //800
//            telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition()); //800
//            telemetry.update();
//
//        }
////        robot.resetEncodersCascade();
////        sleep(3000);
////        telemetry.addData("arm: ", robot.arm.getCurrentPosition()); //490
////        telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition()); //800
////        telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition()); //800
////        telemetry.update();
////        while(i < 1){
////            robot.timer.reset();
////            while (robot.timer.seconds() < 2) {
////                robot.cascadeDrive(ticksForCascade);
////            }
////            robot.arm.setTargetPosition(600);
////            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////            robot.timer.reset();
////            while (robot.arm.isBusy() && robot.timer.seconds() < 0.75) {
////                robot.arm.setPower(.4);
////                robot.cascadeDrive(ticksForCascade);
////            }
////            robot.claw.setPosition(0.5);
////            sleep(500);
//////            robot.encoderDrive(-4);
////            robot.claw.setPosition(0);
////            robot.wrist.setPosition(1);
////            robot.arm.setTargetPosition(0);
////            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////            robot.timer.reset();
////            while (robot.arm.isBusy() && robot.timer.seconds() < 0.75) {
////                robot.cascadeDrive(ticksForCascade);
////                robot.arm.setPower(.4);
////
////            }
////            sleep(250);
////            robot.cascadeDrive(0);
////            telemetry.addData("arm: ", robot.arm.getCurrentPosition()); //490
////            telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition()); //800
////        telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition()); //800
////            telemetry.update();
////            i++;
//    }
////        robot.resetEncodersCascade();
////        telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition());
////        telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition());
////        telemetry.update();
////        robot.cascadeDrive(500);
////        telemetry.addData("CascadeLeft: ", robot.cascadeMotorLeft.getCurrentPosition());
////        telemetry.addData("CascadeRight: ", robot.cascadeMotorRight.getCurrentPosition());
////        telemetry.update();
////        robot.cascadeMotorRight.setDirection(DcMotor.Direction.REVERSE);
////        robot.turnOnEncodersCascade();
////
////        robot.cascadeMotorRight.setTargetPosition((int)(500));
//////        robot.cascadeMotorLeft.setTargetPosition((int)(ticksForCascade));
////
////
////        robot.cascadeMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//////        robot.cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////        sleep(50);
////        while ( robot.cascadeMotorRight.isBusy()) {
//////            robot.cascadeMotorLeft.setPower(.6);
////            robot.cascadeMotorRight.setPower(.6);
////
////        }
//////        robot.cascadeMotorLeft.setPower(0);
////        robot.cascadeMotorRight.setPower(0);
////
////        robot.cascadeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
////        robot.cascadeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//}
////    }
//
///*
//package org.firstinspires.ftc.teamcode.MeetCode;
////Test
////Aman Sulaiman, 23-24 CenterStage
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.TouchSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//
//public class Hardware {
//    public DcMotor frontLeft, backLeft, frontRight, backRight, cascadeMotorRight, cascadeMotorLeft, arm;
//    public TouchSensor touchRight, touchLeft;
//    public ColorSensor colorSensorRight, colorSensorLeft, colorSensor;
//    public Servo claw;
//    public Servo wrist, dropper, launch;
//    public DistanceSensor distanceLeft, distanceRight;
//
//    HardwareMap hwMap;
//    static final double COUNTS_PER_MOTOR_REV = 537.7;
//
//    static final double COUNTS_PER_CASCADE_REV = 384.5;
//    // eg: TETRIX Motor Encoder
//    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
//    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
//    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
//            (WHEEL_DIAMETER_INCHES * 3.1415);
//
//    static final double PULLEY_DIAMETER = 1.503937;
//
//    static double COUNTS_PER_INCH_CASCADE = (COUNTS_PER_CASCADE_REV * DRIVE_GEAR_REDUCTION)/
//            (PULLEY_DIAMETER * 3.1415);
//    public ElapsedTime timer = new ElapsedTime();
//
//
//
//    public void init(HardwareMap hwMap) {
//        this.hwMap = hwMap;
//        timer.reset();
//        backLeft = hwMap.dcMotor.get("lb");
//        backRight = hwMap.dcMotor.get("rb");
//        frontLeft = hwMap.dcMotor.get("lf");
//        frontRight = hwMap.dcMotor.get("rf");
//        cascadeMotorRight = hwMap.dcMotor.get("cascadeRight");
//        cascadeMotorLeft = hwMap.dcMotor.get("cascadeLeft");
//        arm = hwMap.dcMotor.get("arm");
//
//        //servos
//
//        wrist = hwMap.servo.get("wrist");
//        claw = hwMap.servo.get("claw");
//        dropper = hwMap.servo.get("dropper");
//        launch = hwMap.servo.get("launch");
//
//        //sensors
//        distanceLeft = (DistanceSensor)(hwMap.get("distanceLeft"));
//        distanceRight = (DistanceSensor)(hwMap.get("distanceRight"));
//        colorSensorRight = hwMap.colorSensor.get("colorSensorRight");
//        colorSensorLeft = hwMap.colorSensor.get("colorSensorLeft");
//        //colorSensor = hwMap.colorSensor.get("colorSensor");
//
//        //reversals
//        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
//        cascadeMotorRight.setDirection(DcMotor.Direction.REVERSE);
//
//        //braking
//
//        cascadeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        cascadeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//    }
//
//
//    public void setPowerOfAllMotorsTo(double power) {
//        backLeft.setPower(power);
//        backRight.setPower(power);
//        frontLeft.setPower(power);
//        frontRight.setPower(power);
//    }
//    public void turnOnEncoders(){
//        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//
//    }
//    public void turnOffEncoders(){
//        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//    }
//    public void turnOffEncodersCascade(){
//        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER
//        );
//
//    }
//    public void encoderDrive(double inches){
//
//        resetEncoders();
//
//        turnOnEncoders();
//
//        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        frontRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//
//
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        while(backLeft.getTargetPosition() - backLeft.getCurrentPosition() != 0) {
//            setPowerOfAllMotorsTo(.8);
//        }
//        setPowerOfAllMotorsTo(0);
//        resetEncoders();
//
//
//    }
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
//    public void resetEncoders(){
//        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//    }
//    public void resetEncodersCascade(){
//        cascadeMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        cascadeMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//    }
//    public void turnOnEncodersCascade() {
//
//        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }
//    public void cascadeDrive (double position){
//
//        turnOnEncodersCascade();
//
//        cascadeMotorRight.setTargetPosition((int)(position));
//        cascadeMotorLeft.setTargetPosition((int)(position));
//
//
//        cascadeMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        cascadeMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        while (cascadeMotorLeft.getTargetPosition() - cascadeMotorLeft.getCurrentPosition() >= 20 || cascadeMotorLeft.getTargetPosition() - cascadeMotorLeft.getCurrentPosition() <= -20) {
//            cascadeMotorLeft.setPower(.6);
//            cascadeMotorRight.setPower(.6);
//
//        }
//        cascadeMotorLeft.setPower(0);
//        cascadeMotorRight.setPower(0);
//
//        cascadeMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        cascadeMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//    }
//    public void squareUp(){
//        if(distanceLeft.getDistance(DistanceUnit.MM) > distanceRight.getDistance(DistanceUnit.MM)){
//            while(distanceLeft.getDistance(DistanceUnit.MM) > distanceRight.getDistance(DistanceUnit.MM)){
//                frontRight.setPower(.15);
//                backRight.setPower(.15);
//            }
//            frontRight.setPower(0);
//            backRight.setPower(0);
//        }
//        else if(distanceRight.getDistance(DistanceUnit.MM) > distanceLeft.getDistance(DistanceUnit.MM)){
//            while(distanceRight.getDistance(DistanceUnit.MM) > distanceLeft.getDistance(DistanceUnit.MM)){
//                frontLeft.setPower(.15);
//                backLeft.setPower(.15);
//            }
//            frontLeft.setPower(0);
//            backLeft.setPower(0);
//        }
//
//        /*setPowerOfAllMotorsTo(0);
//        if (distanceLeft.getDistance(DistanceUnit.INCH) > distance - .1 && distanceLeft.getDistance(DistanceUnit.INCH) < distance+.1){
//            frontRight.setPower(0);
//            backRight.setPower(0);
//            while (distanceRight.getDistance(DistanceUnit.INCH) > distance){
//                backLeft.setPower(.2);
//                frontLeft.setPower(.2);
//            }
//            backLeft.setPower(0);
//            frontLeft.setPower(0);
//        }
//        if (distanceRight.getDistance(DistanceUnit.INCH) > distance - .1 && distanceRight.getDistance(DistanceUnit.INCH) < distance+.1){
//            frontLeft.setPower(0);
//            backLeft.setPower(0);
//            while (distanceLeft.getDistance(DistanceUnit.INCH) > distance){
//                backRight.setPower(.2);
//                frontRight.setPower(.2);
//            }
//            backRight.setPower(0);
//            frontRight.setPower(0);
//        }*/
///*
//
//    }
//public void strafeRightForTime(double power, double time) {
//        timer.reset();
//        while (timer.seconds() < time) {
//        backLeft.setPower(-power);
//        backRight.setPower(power);
//        frontLeft.setPower(power);
//        frontRight.setPower(-power);
//        }
//        }
//
//public void squareUpColor(String color){
//        if (color.equals("Blue")) {
//        while(colorSensorLeft.blue() < (colorSensorLeft.red() + colorSensorLeft.green()) || colorSensorRight.blue() < (colorSensorRight.red() + colorSensorRight.green())) {
//        setPowerOfAllMotorsTo(.2);
//        if (colorSensorLeft.blue() > (colorSensorLeft.red() + colorSensorLeft.green())){
//        frontRight.setPower(0);
//        backRight.setPower(0);
//        break;
//        }
//        else if(colorSensorRight.blue() > (colorSensorRight.red() + colorSensorRight.green())){
//        backLeft.setPower(0);
//        frontLeft.setPower(0);
//        break;
//        }
//        }
//
//        setPowerOfAllMotorsTo(0);
//        }
//        if (color.equals("Red")) {
//        while(colorSensorLeft.red() < (colorSensorLeft.blue() + colorSensorLeft.green())) {
//        backRight.setPower(.2);
//        frontRight.setPower(.2);
//        }
//        while (colorSensorRight.red() < (colorSensorRight.blue() + colorSensorRight.green())) {
//        backLeft.setPower(.2);
//        frontLeft.setPower(.2);
//        }
//        }
//        }
//public void encoderStrafeRight(double inches){
//        resetEncoders();
//        turnOnEncoders();
//
//        backLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        frontRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        while (backLeft.getTargetPosition() - backLeft.getCurrentPosition() != 0) {
//        setPowerOfAllMotorsTo(.8);
//        }
//        setPowerOfAllMotorsTo(0);
//        resetEncoders();
//
//        }
//public void encoderStrafeLeft(double inches){
//        resetEncoders();
//        turnOnEncoders();
//
//        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        backRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//        frontLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//        frontRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        while (backLeft.getTargetPosition() - backLeft.getCurrentPosition() != 0) {
//        setPowerOfAllMotorsTo(.8);
//        }
//        setPowerOfAllMotorsTo(0);
//        resetEncoders();
//
//        }
//public void encoderTurnRight(double inches){
//        turnOnEncoders();
//
//        backLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//        backRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        frontLeft.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//        frontRight.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        while (backLeft.getTargetPosition() - backLeft.getCurrentPosition() != 0) {
//        setPowerOfAllMotorsTo(.8);
//        }
//        setPowerOfAllMotorsTo(0);
//
//        resetEncoders();
//        }
//public void encoderTurnLeft(double inches){
//        turnOnEncoders();
//
//        backLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        backRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//        frontLeft.setTargetPosition((int)(inches * COUNTS_PER_INCH));
//        frontRight.setTargetPosition(-(int)(inches * COUNTS_PER_INCH));
//
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        while (backLeft.getTargetPosition() - backLeft.getCurrentPosition() != 0) {
//        setPowerOfAllMotorsTo(.8);
//        }
//        setPowerOfAllMotorsTo(0);
//
//        resetEncoders();
//        }
//        }
// */