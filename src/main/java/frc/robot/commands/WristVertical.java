package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Wrist;

public class WristVertical extends Command {
    private Wrist wrist;
    private boolean done = false;

    public WristVertical(Wrist wrist){
        this.wrist = wrist;

        addRequirements(wrist);
    }

    @Override
    public void execute(){
        if (wrist.getWristEncoder() < Constants.kWristEncoderLimit) done = true;
        wrist.wristVertical(Constants.kGentley);
        
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
