package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class JogElevatorDown extends Command {
    private MoveElevator elevatorControl;
    private boolean done = false;

    public JogElevatorDown(MoveElevator elevatorControl){
        this.elevatorControl = elevatorControl;
    }

    @Override
    public void execute(){
        elevatorControl.setControl(elevatorControl.getControl()+0.02);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
