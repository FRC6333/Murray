package frc.robot.commands;

import java.util.function.DoubleSupplier;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.MechDrive;

public class StandardDrive extends Command {
    private final MechDrive mechDrive;
    private DoubleSupplier[] layout;  // [forward, side, turn]
    

    public StandardDrive (MechDrive drive, DoubleSupplier[] layout){
        this.mechDrive = drive;
        this.layout = layout;

        addRequirements(mechDrive);
    }

    public void setLayout(DoubleSupplier[] layout){
        this.layout = layout;
    }

    @Override
    public void execute(){
        double forwardSpeed = Math.pow(Math.abs(layout[0].getAsDouble()), Constants.DrivePower)*Math.signum(layout[0].getAsDouble())*Constants.kInvertForward;
        double sideSpeed = Math.pow(Math.abs(layout[1].getAsDouble()),Constants.StrafePower)*Math.signum(layout[1].getAsDouble())*Constants.kInvertStrafe;
        double turnSpeed = Math.pow(Math.abs(layout[2].getAsDouble()), Constants.TurnPower)*Math.signum(layout[2].getAsDouble())*Constants.kInvertTurn;

        mechDrive.drive(forwardSpeed, sideSpeed, turnSpeed);

    }

    // Never stop driving unless this command is de-scheduled.
    @Override
    public boolean isFinished(){
        return false;
    }
}
