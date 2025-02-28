package frc.robot.subsystems;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private SparkMax ArmMotor = new SparkMax(Constants.kArmMotor, MotorType.kBrushless);

    private DigitalInput ArmLimit = new DigitalInput(Constants.kArmLimitChannel);
    private RelativeEncoder ArmEncoder = ArmMotor.getEncoder();
    private PIDController ArmPID = new PIDController(Constants.ArmkP, Constants.ArmkI, Constants.ArmkD);


    public Arm(){
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake);
        
        ArmMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public boolean getArmLimit(){
        return !ArmLimit.get();
    }

    public double getArmEncoder(){
        return ArmEncoder.getPosition();
    }

    public void MoveArm(double direction){
        if (direction > 0 && getArmEncoder() < Constants.kArmSafeLimit){
            ArmMotor.set(direction);
        }
        else if (direction < 0 && !getArmLimit()){
            ArmMotor.set(direction);
        }
        else if(ArmLimit.get()){
            ArmEncoder.setPosition(0);
            ArmMotor.set(Constants.kStop);
        } 
        else {
            ArmMotor.set(Constants.kStop);
        }
    }

    public void setPosition(double pos){
        if(getArmLimit()) ArmEncoder.setPosition(0);
        ArmPID.setSetpoint(pos);
        double speed = ArmPID.calculate(getArmEncoder());
        if (Math.abs(speed) > 0.6) speed = 0.6 * Math.signum(speed);
       // System.out.printf("%f %f %f\n", getArmEncoder(), speed, ArmPID.getError());
        ArmMotor.set(speed);
    }
    

}
