package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class DebugLimitSwitches extends Command {

    private DigitalInput intakeL = new DigitalInput(Constants.kInkakeLeftLimitChannel); // 0
    private DigitalInput intakeR = new DigitalInput(Constants.kIntakeRightLimitChannel); // Not connected
    private DigitalInput wrist = new DigitalInput(Constants.kWristLimitChannel); // 3
    private DigitalInput arm = new DigitalInput(Constants.kArmLimitChannel); // 2
    private DigitalInput elevator = new DigitalInput(Constants.kElevatorLimitChannel); // 1

    private boolean done =  false;
    
    public DebugLimitSwitches(){

    }

    @Override
    public void execute() {
        System.out.printf("Limit Channel %d (intakeL): %b\n",Constants.kInkakeLeftLimitChannel,intakeL.get());
        //System.out.printf("Limit Channel %d (intakeR): %b\n",Constants.kInkakeRightLimitChannel,intakeL.get());
        System.out.printf("Limit Channel %d (wrist): %b\n",Constants.kWristLimitChannel,wrist.get());
        System.out.printf("Limit Channel %d (arm): %b\n",Constants.kArmLimitChannel,arm.get());
        System.out.printf("Limit Channel %d (elevator): %b\n\n",Constants.kElevatorLimitChannel,elevator.get());

        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }


    
}
