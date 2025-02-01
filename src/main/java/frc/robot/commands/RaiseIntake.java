package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class RaiseIntake extends Command {

    private Intake intake;
    private boolean done;

    public RaiseIntake(Intake i){
        intake = i;
        done=false;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        long start = System.currentTimeMillis();
        while (start+250 < System.currentTimeMillis()){
            intake.PositionUp();
        }
        intake.StopPosition();
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }

}
