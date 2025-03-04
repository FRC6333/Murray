package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class PushIntake extends Command {
    private Intake intake;
    private boolean done = false;

    public PushIntake(Intake i){
        intake = i;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        double speed = 0.7;
        intake.PullPush(speed);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
