package org.firstinspires.ftc.teamcode.MeetCode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Cascasde and Arm Telemetry")

public class CascadeAndArmTelemetryTest extends LinearOpMode {

    Hardware robot = new Hardware();
    public void runOpMode(){
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("Arm", robot.arm.getCurrentPosition());
            telemetry.update();
         }

        //Cascade 8 : 1302
        //Arm 8 : 438
        //Cascade 9: 1390
        //Arm 9: 448
    }
}
