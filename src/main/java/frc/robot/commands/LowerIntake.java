package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class LowerIntake extends Command {

    private Intake intake;
    private boolean done = false;

    public LowerIntake(Intake i){
        intake = i;
        done = false;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        if(intake.GetBottomLimit()) done = true;
        else done = false;
        intake.PositionDown(Constants.kGentley);
        
    }

    @Override
    public boolean isFinished(){
        return done;
    }

}
