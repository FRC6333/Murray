package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX PostitionMotor = new WPI_TalonSRX(Constants.kIntakePositionMotor);
    private WPI_TalonSRX IntakeMotor = new WPI_TalonSRX(Constants.kIntakeMotor);

    private DigitalInput bottomLimit = new DigitalInput(Constants.kBottomLimitChannel);
    private DigitalInput topLimit = new DigitalInput(Constants.kTopLimitChannel);


    public Intake(){
        PostitionMotor.setNeutralMode(NeutralMode.Brake);
        IntakeMotor.setNeutralMode(NeutralMode.Coast);
    }

    public boolean GetBottomLimit(){
        return bottomLimit.get();
    }

    public boolean GetTopLimit(){
        return topLimit.get();
    }

    public void PositionUp(double speed){
        while (!GetTopLimit()) {
            PostitionMotor.set(Math.abs(speed));  // Assure the intake is only moving UP.
        }
    }
    public void PositionDown(double speed){
        while (!GetBottomLimit()) {
            PostitionMotor.set(Math.abs(speed)*-1);  // Assure the intake is only moving DOWN.
        }
    }

    public void StopPosition(){
        PostitionMotor.set(0);
    }

    public void PullPush(double speed){
        IntakeMotor.set(speed);
    }
}
