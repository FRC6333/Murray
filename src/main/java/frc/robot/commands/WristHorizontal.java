package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Wrist;

public class WristHorizontal extends Command {
    private Wrist wrist;
    private boolean done = false;

    public WristHorizontal(Wrist wrist){
        this.wrist = wrist;

        addRequirements(this.wrist);
    }

    @Override
    public void execute(){
        if(wrist.getWristLimit()) done = true;
        wrist.wristHorizontal(Constants.kGentley);
        
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
