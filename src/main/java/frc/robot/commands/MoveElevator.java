package frc.robot.commands;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;

public class MoveElevator extends Command {
    private double control = 0;
    private Elevator elevator;
    private Arm arm;
    private Intake intake;
    private double printcount;
    

    public MoveElevator(Elevator elevator, Intake intake, Arm arm, double control){
        this.elevator = elevator;
        this.intake = intake;
        this.arm = arm;
        this.control = control;

        addRequirements(elevator);
    }
    
    public double getControl(){
        return control;
    }

    public void setControl(double newControl){
        if (newControl >= 0) control = 0;
        else if (newControl <= Constants.kElevatorMaxLimit) control = Constants.kElevatorMaxLimit;
        else {
            control = newControl;
        }
    }

    @Override
    public void execute(){
        if(intake.GetBottomLimit() && (arm.getArmLimit()|| arm.getArmEncoder() < (Constants.kArmSafeLimit-1))){
             elevator.setPosition(control);
         }
         else if(!intake.GetBottomLimit() && arm.getArmEncoder() < (Constants.kArmSafeLimit-1)){
             elevator.setPosition(control);
         }
         elevator.setPosition(control);
        if (printcount>25) {
            System.out.printf("Elevator movement here: %f\n", control);
            printcount=0;
        } else printcount++;
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
