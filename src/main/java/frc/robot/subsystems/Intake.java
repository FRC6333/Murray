package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.RelativeEncoder;
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

    private DigitalInput TopLeftLimit = new DigitalInput(Constants.kInkakeLeftLimitChannel);
    private DigitalInput TopRightLimit = new DigitalInput(Constants.kIntakeRightLimitChannel);

    private RelativeEncoder LeftEncoder = PostitionMotorL.getEncoder();
    private RelativeEncoder RightEncoder = PostitionMotorR.getEncoder();


    public Intake(){
        SparkMaxConfig intakePosConfig = new SparkMaxConfig();
        SparkMaxConfig intakePullConfig = new SparkMaxConfig();
        SparkMaxConfig intakePosConfigInverted = new SparkMaxConfig();

        intakePosConfig.idleMode(IdleMode.kCoast);
        intakePullConfig.idleMode(IdleMode.kBrake);
        intakePosConfigInverted.idleMode(IdleMode.kCoast);
        intakePosConfigInverted.inverted(true);
        
        PostitionMotorL.configure(intakePosConfigInverted, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        PostitionMotorR.configure(intakePosConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        IntakeMotor.configure(intakePullConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        LeftEncoder.setPosition(0.0);
        RightEncoder.setPosition(0.0);
    }

    public boolean GetTopLeftLimit(){
        return !TopLeftLimit.get();  // True means not being tripped
    }

    public boolean GetTopRightLimit(){
        return !TopRightLimit.get(); // True means not being tripped
    }

    public double GetLeftEncoder(){
        return LeftEncoder.getPosition();
    }

    public double GetRightEncoder(){
        return RightEncoder.getPosition();
    }

    public boolean GetBottomLimit(){
        return (GetLeftEncoder() < Constants.kIntakePositionDownLimit) && (GetLeftEncoder() < Constants.kIntakePositionDownLimit);
    }

    public void PositionUp(double speed){
        System.out.printf("\n\n!!!STARTING!!!\n%.3f    %.3f: %b %b\n\n\n", GetLeftEncoder(), GetRightEncoder(), GetTopLeftLimit(), GetTopRightLimit());
        while (!GetTopLeftLimit() && !GetTopRightLimit()) {
            PostitionMotorL.set(Math.abs(speed));  // Assure the intake is only moving UP.
            PostitionMotorR.set(Math.abs(speed));
        }
        System.out.printf("\n\n!!!Stopping!!!\n%.3f    %.3f: %b %b\n\n\n", GetLeftEncoder(), GetRightEncoder(), GetTopLeftLimit(), GetTopRightLimit());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Interuppted?!?");
        }

        LeftEncoder.setPosition(0);
        RightEncoder.setPosition(0);
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
