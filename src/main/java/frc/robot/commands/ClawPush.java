package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Claw;

public class ClawPush extends Command {
    private Claw claw;
    private boolean done = false;

    public ClawPush(Claw c){
        claw = c;

        addRequirements(claw);
    }

    @Override
    public void execute(){
        claw.clawOpen();
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}