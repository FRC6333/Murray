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

public class Wrist extends SubsystemBase {
    private SparkMax WristMotor =   new SparkMax(Constants.kWristMotor, MotorType.kBrushless);
    
    private DigitalInput WristLimit =   new DigitalInput(Constants.kWristLimitChannel);
    private RelativeEncoder WristEncoder = WristMotor.getEncoder();


    public Wrist(){
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake);
        
        WristMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public double getWristEncoder(){
        return WristEncoder.getPosition();
    }

    public boolean getWristLimit(){
        return !WristLimit.get();
    }

    public void wristHorizontal(double speed){
        if (!getWristLimit()){
            WristMotor.set(Math.abs(speed));
        }
        else{
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //System.out.println("Interuppted?!?");
            }
            WristMotor.set(Constants.kStop);
            WristEncoder.setPosition(0.0);
        }
    }

    public void wristVertical(double speed){
        if (getWristEncoder() >= Constants.kWristEncoderLimit){
            WristMotor.set(Math.abs(speed)*-1);
        }
        else WristMotor.set(Constants.kStop);
    }
}
