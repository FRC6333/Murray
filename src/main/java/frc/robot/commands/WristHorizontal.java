package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class WristHorizontal extends Command {
    private Intake intake;
    private Elevator elevator;
    private Arm arm;
    private boolean done = false;

    public WristHorizontal(Arm arm, Elevator elevator,Intake intake){
        this.arm = arm;
        this.elevator = elevator;
        this.intake = intake;

        addRequirements(arm);
    }

    @Override
    public void execute(){
        while (intake.GetBottomLimit() && elevator.getElevatorEncoder() > Constants.kElevatorUsableHeightLimit && arm.getWristEncoder() < Constants.kWristEncoderLimit){
            arm.wristHorizonal(Constants.kGentley);
        }
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
