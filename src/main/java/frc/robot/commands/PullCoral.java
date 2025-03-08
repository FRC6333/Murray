package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class PullCoral extends Command {
    private Intake intake;
    private boolean done = false;

    public PullCoral(Intake i){
        intake = i;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        double speed = -1*0.5;
        intake.PullPush(speed);
        System.out.print("Suck in Coral\n");
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
