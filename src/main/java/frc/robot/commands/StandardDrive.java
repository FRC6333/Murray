package frc.robot.commands;

import java.util.function.DoubleSupplier;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.MechDrive;

public class StandardDrive extends Command {
    private final MechDrive mechDrive;
    private DoubleSupplier forward;
    private DoubleSupplier side;
    private DoubleSupplier turn;

    public StandardDrive (MechDrive drive, DoubleSupplier f, DoubleSupplier s, DoubleSupplier t){
        this.mechDrive = drive;
        
        this.forward  = f;
        this.side     = s;
        this.turn     = t;

        addRequirements(mechDrive);
    }

    @Override
    public void execute(){
        double forwardSpeed = Math.pow(forward.getAsDouble(), Constants.DrivePower);
        
        
        double sideSpeed = Math.pow(side.getAsDouble(), Constants.DrivePower);
        double turnSpeed = Math.pow(Math.abs(turn.getAsDouble()), Constants.TurnPower) * Math.signum(turn.getAsDouble());

        mechDrive.translate(forwardSpeed, sideSpeed);
        mechDrive.rotate(turnSpeed);

    }

    // Never stop driving unless this command is de-scheduled.
    @Override
    public boolean isFinished(){
        return false;
    }
}
