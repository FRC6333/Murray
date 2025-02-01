package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private WPI_TalonSRX PostitionMotor = new WPI_TalonSRX(Constants.kIntakePositionMotor);
    private WPI_TalonSRX IntakeMotor = new WPI_TalonSRX(Constants.kIntakeMotor);

    private boolean limitSwitch;


    public Intake(){
        PostitionMotor.setNeutralMode(NeutralMode.Brake);
        IntakeMotor.setNeutralMode(NeutralMode.Coast);

        limitSwitch = true;
    }

    public void PositionUp(){
        PostitionMotor.set(0.5);
    }
    public void PositionDown(){
        PostitionMotor.set(-0.5);
    }
    public void StopPosition(){
        PostitionMotor.set(0);
    }

    public boolean GetLimitState(){
        return limitSwitch;
    }
    public void PullPush(double speed){
        IntakeMotor.set(speed);
    }
}
