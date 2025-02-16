package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase{
    private SparkMax ElevatorMotor = new SparkMax(Constants.kElevatorMotor, MotorType.kBrushless);

    private DigitalInput BottomLimit = new DigitalInput(Constants.kElevatorLimitChannel);
    
    private RelativeEncoder ElevatorEncoder = ElevatorMotor.getEncoder();

    private static int EncoderTopLimit = 1000;
    

    public Elevator(){
        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.idleMode(IdleMode.kBrake);
        ElevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        ElevatorEncoder.setPosition(0);
    }

    public boolean getElevatorLimit(){
        return BottomLimit.get();
    }

    public double getElevatorEncoder(){
        return ElevatorEncoder.getPosition();
    }

    public void raiseElevator(double speed){
        if (ElevatorEncoder.getPosition() >= EncoderTopLimit){
            ElevatorMotor.set(0f);
        }
        else{
            ElevatorMotor.set(Math.abs(speed));
        }
    }

    public void lowerElevator(double speed){
        if (BottomLimit.get()){
            ElevatorEncoder.setPosition(0);
            ElevatorMotor.set(0f);
        }
        else{
            ElevatorMotor.set(Math.abs(speed) * -1);
        }
    }
}
