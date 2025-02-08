package frc.robot.commands;

import java.util.HashMap;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AprilDetect;

public class DebugAprilTag extends Command {
    private AprilDetect detector;

    public DebugAprilTag(AprilDetect d){
        detector = d;
        addRequirements(detector);
    }

    @Override
    public void execute() {
        DriverStation.reportWarning("Debugging AprilTagDetction!\n", false);
        detector.attemptDetection();
        HashMap<Integer,Transform3d> lastPoses = detector.getPoses();
        DriverStation.reportWarning(String.format("Got %d poses.\n", lastPoses.size()), false);
        for ( Integer id : lastPoses.keySet()) {
            Transform3d pose = lastPoses.get(id);
            AprilTagDetection detection = detector.getDetection(id.intValue());

            DriverStation.reportWarning(String.format("Tag %d: WorldSpace (%f,%f,%f), CameraSpace (%d,%d)\n", 
                id.intValue(), 
                pose.getX(),
                pose.getY(),
                pose.getZ(),
                detection.getCenterX(),
                detection.getCenterY()
            ), false);
        }
    }
}
