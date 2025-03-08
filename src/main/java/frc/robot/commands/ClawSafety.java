package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;


public class ClawSafety extends Command {
    private Claw claw;
    private Arm arm;
    private boolean done = false;

    public ClawSafety(Claw c, Arm a){
        claw = c;
        arm = a;

        addRequirements(claw);
    }

    @Override
    public void execute(){
        // Stop the intake if no intake controls are given
        if (arm.ReadProximity() > 600 && claw.getSpeed() < 0){
            claw.clawPull(0);
            System.out.println("ClawSafety");
        }
        
    }

    @Override
    public boolean isFinished(){
        return false; // Never stop.
    }
}
