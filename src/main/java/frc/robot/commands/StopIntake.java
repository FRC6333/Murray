package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;
    private boolean done = false;

    public StopIntake(Intake i){
        intake = i;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        // Stop the intake if no intake controls are given
        intake.PullPush(0);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
