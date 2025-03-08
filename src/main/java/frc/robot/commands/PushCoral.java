package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class PushCoral extends Command {
    private Intake intake;
    private boolean done = false;

    public PushCoral(Intake i){
        intake = i;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        double speed = 0.7;
        intake.PullPush(speed);
        System.out.print("Push out Coral\n");
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
