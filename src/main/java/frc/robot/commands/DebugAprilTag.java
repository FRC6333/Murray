package frc.robot.commands;

import java.util.HashMap;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.AprilDetect;

public class DebugAprilTag extends Command {
    private AprilDetect detector;

    public DebugAprilTag(AprilDetect d){
        detector = d;
        addRequirements(detector);
    }

    @Override
    public void execute() {
        detector.attemptDetection();
        HashMap<Integer,Transform3d> lastPoses = detector.getPoses();
        for ( Integer id : lastPoses.keySet()) {
            Transform3d pose = lastPoses.get(id);
            AprilTagDetection detection = detector.getDetection(id.intValue());

            System.out.printf("Tag %d: WorldSpace (%f,%f,%f), CameraSpace (%d,%d)\n", 
                id.intValue(), 
                pose.getX(),
                pose.getY(),
                pose.getZ(),
                detection.getCenterX(),
                detection.getCenterY()
            );
        }
    }
}
