package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class WristVertical extends Command {
    private Intake intake;
    private Elevator elevator;
    private Wrist wrist;
    private boolean done = false;

    public WristVertical(Wrist wrist, Elevator elevator,Intake intake){
        this.wrist = wrist;
        this.elevator = elevator;
        this.intake = intake;

        addRequirements(wrist);
    }

    @Override
    public void execute(){
        System.out.printf("%b && %b; %f < %f\n", intake.GetBottomLimit(), elevator.getElevatorEncoder() < Constants.kElevatorSafeLow, elevator.getElevatorEncoder(), Constants.kElevatorSafeLow);
        if (intake.GetBottomLimit() && elevator.getElevatorEncoder() < (Constants.kElevatorSafeLow+1)){
            wrist.wristVertical(Constants.kGentley);
        }
        else if(!intake.GetBottomLimit() && elevator.getElevatorEncoder() < (Constants.kElevatorSafeHigh+1)){
            wrist.wristVertical(Constants.kGentley);
        }
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
