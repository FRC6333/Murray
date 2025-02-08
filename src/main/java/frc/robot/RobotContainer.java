package frc.robot;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import frc.robot.commands.DebugAprilTag;
import frc.robot.commands.PullIntake;
import frc.robot.commands.StandardDrive;
import frc.robot.subsystems.AprilDetect;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.MechDrive;

public class RobotContainer {
    private static CommandScheduler scheduler = CommandScheduler.getInstance();

    // Controls
    public static XboxController DriveController = new XboxController(Constants.kDriveJoystick);
    public static XboxController ArmController = new XboxController(Constants.kArmJoystick);

    // Subsys
    private final MechDrive m_MechDrive = new MechDrive();
    private final Intake m_intake = new Intake();
    private final AprilDetect m_Detector = new AprilDetect();

    private final DoubleSupplier[] carLayout = {
        () -> (DriveController.getLeftTriggerAxis()*-1)+(DriveController.getRightTriggerAxis()),
        () -> DriveController.getLeftX(),
        () -> DriveController.getRightX()
    };
    private final DoubleSupplier[] stickLayout = {
        () -> DriveController.getLeftY(),
        () -> DriveController.getRightX(),
        () -> DriveController.getLeftX()
    };
    private final DoubleSupplier[] tankLayout = {
        () -> DriveController.getLeftY(),
        () -> DriveController.getRightX(),
        () -> (DriveController.getLeftTriggerAxis()*-1)+(DriveController.getRightTriggerAxis())
    };
    private final DoubleSupplier[] twoDimLayout = {
        () -> DriveController.getLeftY(),
        () -> DriveController.getLeftX(),
        () -> DriveController.getRightX()
    };
    private DoubleSupplier[] currentLayout = twoDimLayout;

    private final StandardDrive m_StandardDrive = new StandardDrive(
        m_MechDrive, 
        currentLayout[0],
        currentLayout[1],
        currentLayout[2]
    );

    private final DebugAprilTag m_DebugAprilTag = new DebugAprilTag(m_Detector);
    
    // Container for robot, defines hardware, subsystems, and commands robot can use.
    public RobotContainer(){
        m_MechDrive.setDefaultCommand(m_StandardDrive);
        scheduler.schedule(m_DebugAprilTag.withTimeout(2).repeatedly());
        scheduler.schedule(new ConditionalCommand(new PullIntake(m_intake), null, () -> DriveController.getAButton()).repeatedly());

    }
}
