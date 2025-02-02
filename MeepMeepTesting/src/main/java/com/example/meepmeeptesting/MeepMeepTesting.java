package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(49.395, 49.395, Math.toRadians(48), Math.toRadians(48), 19.075)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(14, -60, Math.toRadians(90)))
                                .forward(30.5)
                                //drop pixel
//                                .splineTo(new Vector2d(40, 40), Math.toRadians(0))
                                .lineToLinearHeading(new Pose2d(14,-35, Math.toRadians(0)))
                                //                                .splineToLinearHeading(new Pose2d(50, -35, Math.toRadians(0)), Math.toRadians(90))
                                .forward(36)
                                .lineTo(new Vector2d(35,-35))
//                                .addTemporalMarker(12, () -> {
////                                    robot.claw.setPosition(0);
////                                    robot.wrist.setPosition(1);
//                                })
                                .addDisplacementMarker(0.1, 0, () -> {
//                                    robot.claw.setPosition(0);
//                                    robot.wrist.setPosition(1);
                                })
                                .lineTo(new Vector2d(23.1, -10))
                                .lineTo(new Vector2d(-59,-10))
                                .lineTo(new Vector2d(23.1, -10))
                                .lineTo(new Vector2d(48, -29))
//                                .splineToConstantHeading(new Vector2d(-60, -11.5), Math.toRadians(0))
                                .lineTo(new Vector2d(31.5, -10))
                                .lineTo(new Vector2d(59, -10))
//                                .splineToConstantHeading(new Vector2d(58.5, -11), Math.toRadians(0))
//                                .turn(Math.toRadians(90))
//                                .forward(30)
//                                .turn(Math.toRadians(90))
//                                .forward(30)
//                                .turn(Math.toRadians(90))
//                                .forward(30)
//                                .turn(Math.toRadians(90))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}