package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Claw;


public class ClawStop extends Command {
    private Claw claw;
    private boolean done = false;

    public ClawStop(Claw c){
        claw = c;

        addRequirements(claw);
    }

    @Override
    public void execute(){
        // Stop the intake if no intake controls are given
        claw.clawPull(0);
        System.out.println("Clawstop");
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
