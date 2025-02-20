package frc.robot.subsystems;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private SparkMax ForearmMotor = new SparkMax(Constants.kForearmMotor, MotorType.kBrushless);
    private SparkMax WristMotor =   new SparkMax(Constants.kWristMotor, MotorType.kBrushless);
    private SparkMax ClawMotor =    new SparkMax(Constants.kClawMotor, MotorType.kBrushless);

    
    private DigitalInput ForearmLimit = new DigitalInput(Constants.kForearmLimitChannel);
    private DigitalInput WristLimit =   new DigitalInput(Constants.kWristLimitChannel);

    private RelativeEncoder ForearmEncoder = ForearmMotor.getEncoder();
    private RelativeEncoder WristEncoder = WristMotor.getEncoder();


    public Arm(){
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake);
        
        ForearmMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        WristMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        ClawMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public void forearmMove(double direction){
        if (direction > 0 && ForearmEncoder.getPosition() < Constants.kForearmEncoderLimit){
            ForearmMotor.set(direction);
        }
        else if (direction < 0 && !ForearmLimit.get()){
            ForearmMotor.set(direction);
        }
        else if(ForearmLimit.get()){
            ForearmEncoder.setPosition(0);
            ForearmMotor.set(Constants.kStop);
        } 
        else {
            ForearmMotor.set(Constants.kStop);
        }
    }

    public double getWristEncoder(){
        return WristEncoder.getPosition();
    }

    public boolean getWristLimit(){
        return WristLimit.get();
    }

    public void wristVertical(double speed){
        if (!WristLimit.get()){
            WristMotor.set(Math.abs(speed));
        }
        else{
            WristMotor.set(Constants.kStop);
            WristEncoder.setPosition(0.0);
        }
    }

    public void wristHorizonal(double speed){
        if (WristEncoder.getPosition() < Constants.kWristEncoderLimit){
            WristMotor.set(Math.abs(speed)*-1);
        }
        else{
            WristMotor.set(Constants.kStop);
        }
    }

    public void clawPull(double speed){
        ClawMotor.set(Math.abs(speed));
    }

    public void clawOpen(){
        ClawMotor.set(Constants.kGentley*-1);
    }

    public void clawClose(){
        ClawMotor.set(Constants.kStop);
    }

}
