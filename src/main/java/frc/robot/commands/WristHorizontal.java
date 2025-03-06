package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class WristHorizontal extends Command {
    private Intake intake;
    private Elevator elevator;
    private Wrist wrist;
    private boolean done = false;

    public WristHorizontal(Wrist wrist, Elevator elevator,Intake intake){
        this.wrist = wrist;
        this.elevator = elevator;
        this.intake = intake;

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
