package frc.robot.subsystems;

import java.util.HashMap;
import org.opencv.core.Mat;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;


public class AprilDetect extends SubsystemBase {
    private AprilTagDetector m_tagDetector;  // Detects april tags and decodes tag ids
    private AprilTagPoseEstimator m_PoseEstimator;  // Attempts to determine the pose of an AprilTagDetection

    public AprilDetect(){
        // Setup tag detector.
        AprilTagDetector.Config detectionConfig = new AprilTagDetector.Config(); // Set this during testing.
        detectionConfig.debug = true;

        AprilTagDetector.QuadThresholdParameters thresholdParameters = new AprilTagDetector.QuadThresholdParameters(); // Set this during testing.
        thresholdParameters.deglitch = true;

        m_tagDetector = new AprilTagDetector();
        m_tagDetector.setConfig(detectionConfig);                       // Set image processing parameters
        m_tagDetector.setQuadThresholdParameters(thresholdParameters);  // Set quad detection parameters.
        m_tagDetector.addFamily("36h11");                           // Set tag type.
        
        // Setup pose estimator.
        AprilTagPoseEstimator.Config estimationConfig = new AprilTagPoseEstimator.Config(
            0.021,        // https://community.firstinspires.org/2025-apriltag-information 
            
            699.3778103158814, // https://www.chiefdelphi.com/t/microsoft-livecam-hd-3000-focal-measurements/447849/3
            677.7161226393544, // https://github.com/PhotonVision/photonvision/blob/7f09f9e4f5b4237ef4b9dde7fdcb747115315659/photon-server/src/main/resources/calibration/lifecam480p.json#L10
            345.6059345433618, 
            207.12741326228522
        );
        m_PoseEstimator = new AprilTagPoseEstimator(estimationConfig);
    }

    /**
     * Detect on a new frame from the camera and return the decoded id and estimated pose.
     * @return HashMap for all detected tag poses indexed by tag id.
     */
    public HashMap<Integer, Transform3d> attemptDetection(){
        // Get image from cameraserver
        Mat image = new Mat();
        CameraServer.getVideo().grabFrame(image);
        
        // preprocess (TODO?)
        
        // detect
        AprilTagDetection[] detections = m_tagDetector.detect(image);
        
        // Estimate poses
        HashMap<Integer, Transform3d> poses = new HashMap<>();
        for (int i=0; i < detections.length; i++) {
            poses.put(Integer.valueOf(detections[i].getId()), m_PoseEstimator.estimate(detections[i]));
        }
        
        return poses;
    }
}
