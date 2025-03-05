package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

public class MoveArm extends Command {
    private double control = 0;
    private Arm arm;
    private Elevator elevator;
    private Intake intake;
    

    public MoveArm(Arm arm, Elevator elevator, Intake intake, double control){
        this.arm = arm;
        this.elevator = elevator;
        this.intake = intake;
        this.control = control;

        addRequirements(arm);
    }

    public double getControl(){
        return control;
    }

    public void setControl(double newControl){
        if (newControl >= 5) control = 5;
        else if (newControl <= Constants.kArmMaxLimit) control = Constants.kArmMaxLimit;
        else{
            control = newControl;
            System.out.println("Set output of arm to user value.");
        }
        System.out.printf("%f = %f\n", newControl, control);
    }

    @Override
    public void execute(){
        arm.setPosition(control); // Trusting the state machine.
    }

    @Override
    public boolean isFinished(){
        return false;  // Dont stop.
    }
}
