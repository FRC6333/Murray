package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Elevator;

public class DebugHardwareValues extends Command {
    private Wrist wrist;
    private Arm arm;
    private Elevator elevator;
    private Intake intake;

    private boolean done =  false;
    
    public DebugHardwareValues(Intake intake,Wrist wrist, Arm arm, Elevator elevator){
        this.wrist = wrist;
        this.arm = arm;
        this.elevator = elevator;
        this.intake = intake;
    }

    @Override
    public void execute() {
        System.out.printf("%f: %b\n", wrist.getWristEncoder(), wrist.getWristLimit());
        //System.out.printf("InL %b, InR %b, Wrist %b, Arm %b, ELevator %b\n", intake.GetTopLeftLimit(), intake.GetTopRightLimit(), wrist.getWristLimit(), arm.getArmLimit(), elevator.getElevatorLimit());
        //done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }


    
}
