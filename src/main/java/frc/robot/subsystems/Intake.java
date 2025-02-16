package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private SparkMax PostitionMotorL = new SparkMax(Constants.kIntakePositionMotorL, MotorType.kBrushless);
    private SparkMax PostitionMotorR = new SparkMax(Constants.kIntakePositionMotorR, MotorType.kBrushless);
    private SparkMax IntakeMotor = new SparkMax(Constants.kIntakeMotor, MotorType.kBrushless);

    private DigitalInput BottomLimit = new DigitalInput(Constants.kBottomLimitChannel);
    private DigitalInput TopLimit = new DigitalInput(Constants.kTopLimitChannel);


    public Intake(){
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        SparkMaxConfig intakeConfigInverted = new SparkMaxConfig();
        intakeConfig.idleMode(IdleMode.kBrake);
        intakeConfigInverted.idleMode(IdleMode.kBrake);
        intakeConfigInverted.inverted(true);
        
        PostitionMotorL.configure(intakeConfigInverted, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        PostitionMotorR.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        IntakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public boolean GetBottomLimit(){
        return BottomLimit.get();
    }

    public boolean GetTopLimit(){
        return TopLimit.get();
    }

    public void PositionUp(double speed){
        if (!GetTopLimit()) {
            PostitionMotorL.set(Math.abs(speed));  // Assure the intake is only moving UP.
            PostitionMotorR.set(Math.abs(speed));
        }
        else StopPosition();
    }
    public void PositionDown(double speed){
        if (!GetBottomLimit()) {
            PostitionMotorL.set(Math.abs(speed)*-1);  // Assure the intake is only moving DOWN.
            PostitionMotorR.set(Math.abs(speed)*-1);
        }
        else StopPosition();
    }

    public void StopPosition(){
        PostitionMotorL.set(0);
        PostitionMotorR.set(0);
    }

    public void PullPush(double speed){
        IntakeMotor.set(speed);
    }
}
