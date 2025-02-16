package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class PushIntake extends Command {
    private Intake intake;
    private boolean done = false;

    public PushIntake(Intake i){
        intake = i;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        if (intake.GetBottomLimit()){
            double speed = Constants.kGentley;
            intake.PullPush(speed);
        }
        else{
            CommandScheduler.getInstance().schedule(new LowerIntake(intake));
        }
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
