package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
    private SparkMax ClawMotor = new SparkMax(Constants.kClawMotor, MotorType.kBrushless);

    public Claw(){
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake);
        
        ClawMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void clawPull(double speed){
        ClawMotor.set(Math.abs(speed));
        //ClawMotor.set(Constants.kStop);
    }

    public void clawOpen(){
        ClawMotor.set(Constants.kGentley*-1);
    }
}
