package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.StandardDrive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.MechDrive;

public class RobotContainer {
    // Controls
    public static XboxController DriveController = new XboxController(Constants.kDriveJoystick);

    // Subsys
    private final MechDrive m_MechDrive = new MechDrive();
    private final Intake m_intake = new Intake();

    private final StandardDrive m_StandardDrive = new StandardDrive(
        m_MechDrive, 
        ()->DriveController.getLeftY(),
        ()-> DriveController.getRightX()*-1,
        ()-> DriveController.getLeftX()
    );
    
    // Container for robot, defines hardware, subsystems, and commands robot can use.
    public RobotContainer(){
        m_MechDrive.setDefaultCommand(m_StandardDrive);
    }
}
