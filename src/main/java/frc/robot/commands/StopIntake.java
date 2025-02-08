package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class StopIntake extends Command {
    private Intake intake;
    private XboxController driveController;
    private boolean done = false;

    public StopIntake(Intake i, XboxController d){
        intake = i;
        driveController = d;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        // Stop the intake if no intake controls are given
        if (!driveController.getAButton() && !driveController.getBButton()) intake.PullPush(0);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
