package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.StateMachine;

public class IterateStateMachine extends Command {
    
    private StateMachine stateMachine;

    public IterateStateMachine(StateMachine stateMachine){
        this.stateMachine = stateMachine;
        addRequirements(stateMachine);
    }

    @Override
    public void execute() {
        stateMachine.iterateStateMachine();
    }

    @Override
    public boolean isFinished(){
        return false; // Never stop.
    }


    
}
