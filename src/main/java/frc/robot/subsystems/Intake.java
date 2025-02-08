package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX PostitionMotorL = new WPI_TalonSRX(Constants.kIntakePositionMotorL);
    private WPI_TalonSRX PostitionMotorR = new WPI_TalonSRX(Constants.kIntakePositionMotorR);
    private WPI_TalonSRX IntakeMotor = new WPI_TalonSRX(Constants.kIntakeMotor);

    private DigitalInput bottomLimit = new DigitalInput(Constants.kBottomLimitChannel);
    private DigitalInput topLimit = new DigitalInput(Constants.kTopLimitChannel);


    public Intake(){
        PostitionMotorL.setNeutralMode(NeutralMode.Brake);
        PostitionMotorL.setInverted(true);
        
        PostitionMotorR.setNeutralMode(NeutralMode.Brake);
        PostitionMotorR.setInverted(false);
        
        IntakeMotor.setNeutralMode(NeutralMode.Brake);
    }

    public boolean GetBottomLimit(){
        return bottomLimit.get();
    }

    public boolean GetTopLimit(){
        return topLimit.get();
    }

    public void PositionUp(double speed){
        while (!GetTopLimit()) {
            PostitionMotorL.set(Math.abs(speed));  // Assure the intake is only moving UP.
            PostitionMotorR.set(Math.abs(speed));
        }
        StopPosition();
    }
    public void PositionDown(double speed){
        while (!GetBottomLimit()) {
            PostitionMotorL.set(Math.abs(speed)*-1);  // Assure the intake is only moving DOWN.
            PostitionMotorR.set(Math.abs(speed)*-1);
        }
        StopPosition();
    }

    public void StopPosition(){
        PostitionMotorL.set(0);
        PostitionMotorR.set(0);
    }

    public void PullPush(double speed){
        IntakeMotor.set(speed);
    }
}
