package frc.robot.commands;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;

public class MoveElevator extends Command {
    private double control = 0;
    private Elevator elevator;
    private Arm arm;
    private Intake intake;
    

    public MoveElevator(Elevator elevator, Intake intake, Arm arm, double control){
        this.elevator = elevator;
        this.intake = intake;
        this.control = control;

        addRequirements(elevator);
    }
    
    public double getControl(){
        return control;
    }

    public void setControl(double newControl){
        if(intake.GetBottomLimit() && (arm.getArmLimit()|| arm.getArmEncoder() < (Constants.kArmSafeLimit+1))){
            control= newControl;
        }
        else if(!intake.GetBottomLimit() && arm.getArmEncoder() < (Constants.kArmSafeLimit+1)){
            control = newControl;
        }
    }

    @Override
    public void execute(){
        if(intake.GetBottomLimit() && (arm.getArmLimit()|| arm.getArmEncoder() < (Constants.kArmSafeLimit+1))){
            elevator.setPosition(control);
        }
        else if(!intake.GetBottomLimit() && arm.getArmEncoder() < (Constants.kArmSafeLimit+1)){
            elevator.setPosition(control);
        }

    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
