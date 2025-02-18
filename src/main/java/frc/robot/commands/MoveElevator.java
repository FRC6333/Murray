package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class MoveElevator extends Command {
    private Elevator elevator;
    private Intake intake;
    private DoubleSupplier control;

    public MoveElevator(Elevator elevator, Intake intake, DoubleSupplier control){
        this.elevator = elevator;
        this.intake = intake;
        this.control = control;

        addRequirements(elevator);
    }

    @Override
    public void execute(){
        if(intake.GetBottomLimit()){
            elevator.moveElevator(control.getAsDouble());
        }
        else{
            elevator.moveElevator(0);
            System.out.printf("You need to lower the inake to move the elevator.");
        }
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
