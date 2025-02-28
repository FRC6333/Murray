package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class MoveArm extends Command {
    private double control = 0;
    private Arm arm;
    private Elevator elevator;
    private Intake intake;
    

    public MoveArm(Arm arm, Elevator elevator, Intake intake, double control){
        this.arm = arm;
        this.elevator = elevator;
        this.intake = intake;
        this.control = control;

        addRequirements(arm);
    }

    public double getControl(){
        return control;
    }

    public void setControl(double newControl){
        if (intake.GetBottomLimit() && elevator.getElevatorEncoder() < (Constants.kElevatorSafeLow+1)){
            control = newControl;
        }
        else if(!intake.GetBottomLimit() && elevator.getElevatorEncoder() < (Constants.kElevatorSafeHigh+1)){
            control = newControl;
        }
    }

    @Override
    public void execute(){
        if(intake.GetBottomLimit() && elevator.getElevatorEncoder() < (Constants.kElevatorSafeLow+1)){
            arm.setPosition(control);
        }
        else if(!intake.GetBottomLimit() && elevator.getElevatorEncoder() < (Constants.kElevatorSafeHigh+1)){
            arm.setPosition(control);
        }
        else{
            arm.MoveArm(0);
        }
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
