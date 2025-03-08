package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class JogArmUp extends Command {
    private MoveArm armControl;
    private boolean done = false;

    public JogArmUp(MoveArm armControl){
        this.armControl = armControl;
    }

    @Override
    public void execute(){
        armControl.setControl(armControl.getControl()-0.3);
        done = true;
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
