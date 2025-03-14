package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ClawPull;
import frc.robot.commands.ClawPush;
import frc.robot.commands.ClawSafety;
import frc.robot.commands.ClawStop;
import frc.robot.commands.DebugAprilTag;
import frc.robot.commands.DebugHardwareValues;
import frc.robot.commands.IntakeStop;
import frc.robot.commands.IterateStateMachine;
import frc.robot.commands.JogArmDown;
import frc.robot.commands.JogArmUp;
import frc.robot.commands.JogElevatorDown;
import frc.robot.commands.JogElevatorUp;
import frc.robot.commands.LowerIntake;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.PullCoral;
import frc.robot.commands.StandardDrive;
import frc.robot.commands.StopCoralPushPull;
import frc.robot.commands.PushCoral;
import frc.robot.commands.RaiseIntake;
import frc.robot.commands.ReceiveFloorCoral2;
import frc.robot.commands.SetGoalState;
import frc.robot.commands.WristHorizontal;
import frc.robot.commands.WristVertical;

import frc.robot.subsystems.AprilDetect;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.MechDrive;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.StateMachine.RobotState;

public class RobotContainer {

    // Controls
    public static XboxController DriveController = new XboxController(Constants.kDriveJoystick);
   // public static XboxController ArmController = new XboxController(Constants.kArmJoystick);
    public static Joystick Box1 = new Joystick(Constants.kBox1);
    public static Joystick Box2 = new Joystick(Constants.kBox2);

    // Subsys
    private final MechDrive m_MechDrive = new MechDrive();
    private final Intake m_Intake = new Intake();
    private final Elevator m_Elevator = new Elevator();
    private final Arm m_Arm = new Arm();
    private final Wrist m_Wrist = new Wrist();
    private final AprilDetect m_Detector = new AprilDetect();
    private final StateMachine m_StateMachine;
    private final Claw m_Claw = new Claw();

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

    private final MoveElevator m_MoveElevator;
    private final MoveArm m_MoveArm;

    private final DebugAprilTag m_DebugAprilTag = new DebugAprilTag(m_Detector);
    private final DebugHardwareValues m_DebugHardwareValues = new DebugHardwareValues(m_Intake, m_Wrist, m_Arm, m_Elevator);
    
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

        m_MoveElevator = new MoveElevator(m_Elevator, m_Intake, m_Arm, 0);
        m_Elevator.setDefaultCommand(m_MoveElevator);
        m_MoveArm = new MoveArm(m_Arm, m_Elevator, m_Intake, 0);
        m_Arm.setDefaultCommand(m_MoveArm);

        m_StateMachine = new StateMachine(m_Intake, m_Elevator, m_Arm, m_Wrist, m_MoveElevator, m_MoveArm);
        IterateStateMachine m_StateMachineIterator = new IterateStateMachine(m_StateMachine);
        m_StateMachine.setDefaultCommand(m_StateMachineIterator);

        ClawSafety m_ClawSafe = new ClawSafety(m_Claw, m_Arm);
        m_Claw.setDefaultCommand(m_ClawSafe);
        
        //m_DebugAprilTag.repeatedly().schedule();
        //System.out.printf("Scheduled AprilTag: %b\n", m_DebugAprilTag.isScheduled());
        
        StopCoralPushPull m_StopCoralPushPull = new StopCoralPushPull(m_Intake);
        ClawStop    m_clawstop = new ClawStop(m_Claw);
        IntakeStop m_stopintake = new IntakeStop(m_Intake); 

        Trigger triggerDebug = new JoystickButton(DriveController, XboxController.Button.kRightBumper.value);
        triggerDebug.onTrue(m_DebugHardwareValues);

        
        //Trigger exampleStateSwitch = new JoystickButton(DriveController, XboxController.Button.kX.value);
        //exampleStateSwitch.onFalse(new SetGoalState(m_StateMachine, RobotState.SL3));  // On false so the button has to be pressed AND RELEASED, not held.
        //Trigger exampleStateSwitch1 = new JoystickButton(DriveController, XboxController.Button.kY.value);
        //exampleStateSwitch1.onFalse(new SetGoalState(m_StateMachine, RobotState.SSF2));  // On false so the button has to be pressed AND RELEASED, not held.

        //
        //Trigger triggerLowerIntake = new JoystickButton(DriveController,XboxController.Button.kX.value);
        //triggerLowerIntake.onTrue(new LowerIntake(m_Intake));
        
        //Trigger triggerRaiseIntake = new JoystickButton(DriveController,XboxController.Button.kY.value);
        //triggerRaiseIntake.onTrue(new RaiseIntake(m_Intake));
        
        //Trigger triggerArmDown =  new JoystickButton(ArmController, XboxController.Button.kX.value);
        //Trigger triggerArmDown =  new JoystickButton(Box2, XboxController.Button.kX.value);
        //triggerArmDown.whileTrue(new JogArmDown(m_MoveArm).repeatedly());   
        
        
        Trigger triggerL1Coral = new JoystickButton(Box1, 1);
        triggerL1Coral.onFalse(new SetGoalState(m_StateMachine, RobotState.SL1));

        Trigger triggerL2Coral = new JoystickButton(Box1, 2);
        triggerL2Coral.onFalse(new SetGoalState(m_StateMachine, RobotState.SL2));

        Trigger triggerL3Coral = new JoystickButton(Box1, 3);
        triggerL3Coral.onFalse(new SetGoalState(m_StateMachine, RobotState.SL3));

        Trigger triggerL4Coral = new JoystickButton(Box1, 4);
        triggerL4Coral.onFalse(new SetGoalState(m_StateMachine, RobotState.SL4));

        Trigger triggerL2Algae = new JoystickButton(Box1, 5);
        triggerL2Algae.onFalse(new SetGoalState(m_StateMachine, RobotState.SA2));

        Trigger triggerL3Algae = new JoystickButton(Box1, 6);
        triggerL3Algae.onFalse(new SetGoalState(m_StateMachine, RobotState.SA3));

        Trigger triggerBarge = new JoystickButton(Box1, 7);
        triggerBarge.onFalse(new SetGoalState(m_StateMachine, RobotState.SB));

        Trigger triggerIntakeReady = new JoystickButton(Box1, 8);
        triggerIntakeReady.onFalse(new SetGoalState(m_StateMachine, RobotState.SINT2));

        Trigger triggerHuman = new JoystickButton(Box1, 9);
        triggerHuman.onFalse(new SetGoalState(m_StateMachine, RobotState.SHO));
 
        Trigger triggerHangReady = new JoystickButton(Box1, 10);
        triggerHangReady.onFalse(new SetGoalState(m_StateMachine, RobotState.SH));

        Trigger triggerGetCoral = new JoystickButton(Box1, 11);
        triggerGetCoral.onTrue(new ReceiveFloorCoral2(m_Claw, m_Arm,m_Elevator));

        //Trigger triggerPullIntake = new JoystickButton(DriveController, XboxController.Button.kA.value);  // Have intake pull on [A] and stop on release.
        Trigger triggerPullCoral = new JoystickButton(Box1,12);
        triggerPullCoral.onTrue(new PullCoral(m_Intake));
        triggerPullCoral.onFalse(m_StopCoralPushPull);

        //Trigger triggerElevatorUp =  new JoystickButton(ArmController, XboxController.Button.kB.value);
        Trigger triggerElevatorUp = new JoystickButton(Box2,1);
        triggerElevatorUp.whileTrue(new JogElevatorUp(m_MoveElevator).repeatedly());

        //Trigger triggerPushIntake = new JoystickButton(DriveController, XboxController.Button.kB.value); // Have intake push on [B] and stop on release.
        Trigger triggerPushCoral = new JoystickButton(Box2,2);
        triggerPushCoral.onTrue(new PushCoral(m_Intake));
        triggerPushCoral.onFalse(m_StopCoralPushPull);

        Trigger triggerclawin = new JoystickButton(Box2, 3);
        triggerclawin.onTrue(new ClawPull(m_Claw));
        triggerclawin.onFalse(m_clawstop);

        Trigger triggerclawout = new JoystickButton(Box2, 4);
        triggerclawout.whileTrue((new ClawPush(m_Claw)).repeatedly());
        triggerclawout.onFalse(m_clawstop);
        
        Trigger triggerSafety = new JoystickButton(Box2,5);
        triggerSafety.onFalse(new SetGoalState(m_StateMachine,RobotState.SSF2));

        //Trigger triggerWristVertical = new JoystickButton(ArmController, XboxController.Button.kRightBumper.value);
        Trigger triggerWristVertical = new JoystickButton(Box2, 6);
        triggerWristVertical.onTrue(new WristVertical(m_Wrist));

        //Trigger triggerArmUp =  new JoystickButton(ArmController, XboxController.Button.kY.value);
        Trigger triggerArmUp =  new JoystickButton(Box2, 7);
        triggerArmUp.whileTrue(new JogArmUp(m_MoveArm).repeatedly());
        
        //Trigger triggerElevatorDown =  new JoystickButton(ArmController, XboxController.Button.kA.value);
        Trigger triggerElevatorDown =  new JoystickButton(Box2, 8);
        triggerElevatorDown.whileTrue(new JogElevatorDown(m_MoveElevator).repeatedly());
        
        Trigger triggerArmDown = new Trigger(() -> Box2.getX()<-0.5);
        triggerArmDown.whileTrue(new JogArmDown(m_MoveArm).repeatedly());


        Trigger triggerLowerIntake = new JoystickButton(Box2, 11);
        triggerLowerIntake.onTrue(new LowerIntake(m_Intake));
        //triggerLowerIntake.onFalse(m_stopintake);

        Trigger triggerIntakeup = new JoystickButton(Box2, 10);
        triggerIntakeup.onTrue(new RaiseIntake(m_Intake));
        //triggerLowerIntake.onFalse(m_stopintake);
        
        //Trigger triggerWristHorizontal = new JoystickButton(ArmController, XboxController.Button.kLeftBumper.value);
        Trigger triggerWristHorizontal = new JoystickButton(Box2, 12);
        triggerWristHorizontal.onTrue(new WristHorizontal(m_Wrist));



    }

    public void initalized(){
        DriverStation.reportWarning("Robot Init!\n",false);
    }
}
