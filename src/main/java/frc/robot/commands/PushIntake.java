package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
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
            double speed = Constants.kIntakePushPullSpeed;
            intake.PullPush(speed);
        }
        else{
            DriverStation.reportWarning("Cannot use intake unless it's down.\nLower the intake first.", false);
        }
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
