package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Claw;

public class ClawPull extends Command {
    private Claw claw;
    private boolean done = false;

    public ClawPull(Claw c){
        claw = c;

        addRequirements(claw);
    }

    @Override
    public void execute(){
        double speed = -1*0.7;
        claw.clawPull(speed);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}