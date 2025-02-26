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
        //if(intake.GetBottomLimit()){
            //elevator.moveElevator(control.getAsDouble());
            elevator.setPosition(-80.0);
        //}
        //else{
          //elevator.moveElevator(0);
          //(new LowerIntake(intake)).schedule();
        //}
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
