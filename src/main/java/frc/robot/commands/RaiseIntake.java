package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RaiseIntake extends Command {

    private Intake intake;
    private boolean done = false;

    public RaiseIntake(Intake i){
        intake = i;
        done = false;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        if (intake.GetTopLeftLimit() && intake.GetTopRightLimit()) done = true;
        else done = false;
        intake.PositionUp(Constants.kGentley*2);
    }

    @Override
    public boolean isFinished(){
        return done;
    }

}
