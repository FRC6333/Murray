package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class DebugHardwareValues extends Command {
    private Intake intake;   
    private Wrist wrist; 

    private boolean done =  false;
    
    public DebugHardwareValues(Intake intake, Wrist wrist){
        this.intake = intake;
        this.wrist = wrist;
    }

    @Override
    public void execute() {
        System.out.printf("%b    %b:    %b\n", intake.GetTopLeftLimit(), intake.GetTopRightLimit(), wrist.getWristLimit());
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }


    
}
