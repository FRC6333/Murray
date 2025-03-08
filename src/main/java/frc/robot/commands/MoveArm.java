package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class MoveArm extends Command {
    private double control = 0;
    private Arm arm;
    private Intake intake;
  
    

    public MoveArm(Arm arm, Elevator elevator, Intake intake, double control){
        this.arm = arm;
        this.intake = intake;
        
        this.control = control;

        addRequirements(arm);
    }

    public double getControl(){
        return control;
    }

    public void setControl(double newControl){
        if (newControl >= 5) control = 5;
        else if (newControl <= Constants.kArmMaxLimit) control = Constants.kArmMaxLimit;
        else{
            control = newControl;
        }
    }

    @Override
    public void execute(){
        arm.setPosition(control); // Trusting the state machine.
        SmartDashboard.putNumber("Arm Position", arm.getArmEncoder());
        SmartDashboard.putNumber("Coral Belts", arm.getChannelCurrent(0));
        SmartDashboard.putNumber("Arm Motor", arm.getChannelCurrent(10));
        SmartDashboard.putNumber("Claw Motor", arm.getChannelCurrent(11));
        SmartDashboard.putNumber("Wrist Motor", arm.getChannelCurrent(12));
        SmartDashboard.putNumber("Front Right", arm.getChannelCurrent(13));
        SmartDashboard.putNumber("Rear Left", arm.getChannelCurrent(14));
        SmartDashboard.putNumber("Intake Left", arm.getChannelCurrent(15));
        SmartDashboard.putNumber("Intake Right", arm.getChannelCurrent(16));
        SmartDashboard.putBoolean("Is Intake Down?", intake.GetBottomLimit());
        SmartDashboard.putNumber("Front Left", arm.getChannelCurrent(17));
        SmartDashboard.putNumber("Elevator", arm.getChannelCurrent(18));
        SmartDashboard.putNumber("Rear Right", arm.getChannelCurrent(19));
        SmartDashboard.putNumber("RoboRio", arm.getChannelCurrent(21));
        SmartDashboard.putNumber("Radio", arm.getChannelCurrent(22));
        
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
