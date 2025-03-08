package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class JogElevatorDown extends Command {
    private MoveElevator elevatorControl;
    private boolean done = false;

    public JogElevatorDown(MoveElevator elevatorControl){
        this.elevatorControl = elevatorControl;
    }

    @Override
    public void execute(){
        System.out.printf("[%f]:Jog Eliv Down\n", Timer.getTimestamp());
        elevatorControl.setControl(elevatorControl.getControl()+0.3);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
