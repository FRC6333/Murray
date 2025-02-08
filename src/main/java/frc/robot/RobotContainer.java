package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DebugAprilTag;
import frc.robot.commands.PullIntake;
import frc.robot.commands.PushIntake;
import frc.robot.commands.StopIntake;
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
    
    private SendableChooser<DoubleSupplier[]> m_layoutChooser = new SendableChooser<DoubleSupplier[]>();

    private final StandardDrive m_StandardDrive;

    

    private final DebugAprilTag m_DebugAprilTag = new DebugAprilTag(m_Detector);
    
    // Container for robot, defines hardware, subsystems, and commands robot can use.
    public RobotContainer(){
        m_layoutChooser.setDefaultOption("2D Control", twoDimLayout);
        m_layoutChooser.addOption("Car Control", carLayout);
        m_layoutChooser.addOption("Single Stick Control", stickLayout);
        m_layoutChooser.addOption("Tank Control", tankLayout);
        SmartDashboard.putData("Layout Chooser",m_layoutChooser);

        m_StandardDrive = new StandardDrive(m_MechDrive, m_layoutChooser.getSelected());
        m_MechDrive.setDefaultCommand(m_StandardDrive);
        m_layoutChooser.onChange((DoubleSupplier[] newLayout) -> m_StandardDrive.setLayout(newLayout));
        
        scheduler.schedule(m_DebugAprilTag);
        //scheduler.schedule((new StopIntake(m_intake, DriveController)).repeatedly());

        //Trigger triggerPullIntake = new JoystickButton(DriveController, 0);
        //triggerPullIntake.onTrue(new PullIntake(m_intake));
        //Trigger triggerPushIntake = new JoystickButton(DriveController, 1);
        //triggerPushIntake.onTrue(new PushIntake(m_intake));

    }

    public void initalized(){
        DriverStation.reportWarning("Robot Init!\n",false);
    }
}
