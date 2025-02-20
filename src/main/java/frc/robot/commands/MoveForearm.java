package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class MoveForearm extends Command {
    private Arm arm;
    private Elevator elevator;
    private Intake intake;
    private DoubleSupplier control;

    public MoveForearm(Arm arm, Elevator elevator, Intake intake, DoubleSupplier control){
        this.arm = arm;
        this.elevator = elevator;
        this.intake = intake;
        this.control = control;

        addRequirements(arm);
    }

    @Override
    public void execute(){
        if(intake.GetBottomLimit() && elevator.getElevatorEncoder() > Constants.kElevatorUsableHeightLimit){
            arm.forearmMove(control.getAsDouble());
        }
        else{
            arm.forearmMove(0);
            System.out.printf("You need to lower the inake and raise the elevator a bit to move the forearm.");
        }
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
