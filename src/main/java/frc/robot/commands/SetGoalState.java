package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.StateMachine;

public class SetGoalState extends Command {
    
    private StateMachine stateMachine;
    private frc.robot.subsystems.StateMachine.RobotState goalState;
    private boolean done = false;

    public SetGoalState(StateMachine stateMachine, frc.robot.subsystems.StateMachine.RobotState goalState){
        this.stateMachine = stateMachine;
        this.goalState = goalState;
    }

    @Override
    public void execute() {
        stateMachine.setGoal(goalState);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }


    
}
