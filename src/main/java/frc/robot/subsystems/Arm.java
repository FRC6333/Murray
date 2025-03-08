package frc.robot.subsystems;


import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private SparkMax ArmMotor = new SparkMax(Constants.kArmMotor, MotorType.kBrushless);

    private DigitalInput ArmLimit = new DigitalInput(Constants.kArmLimitChannel);
    private RelativeEncoder ArmEncoder = ArmMotor.getEncoder();
    private PIDController ArmPID = new PIDController(Constants.ArmkP, Constants.ArmkI, Constants.ArmkD);

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 ColorSensor = new ColorSensorV3(i2cPort);

    private final PowerDistribution powerpannel = new PowerDistribution(12, ModuleType.kRev);

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

    public double getPIDPos(){
        return ArmPID.getSetpoint();
    }

    public void setPosition(double pos){
        if(getArmLimit()){ 
            ArmEncoder.setPosition(0);
            if(pos > 0) ArmPID.setSetpoint(0);
            else if (pos <= Constants.kArmMaxLimit) ArmPID.setSetpoint(Constants.kArmMaxLimit);
            else ArmPID.setSetpoint(pos);
        }
        else{
            if(pos > 5) ArmPID.setSetpoint(5);
            else if (pos <= Constants.kArmMaxLimit) ArmPID.setSetpoint(Constants.kArmMaxLimit);
            else ArmPID.setSetpoint(pos);
        }
        
        double speed = ArmPID.calculate(getArmEncoder());
        if (Math.abs(speed) > 0.4) speed = 0.4 * Math.signum(speed);
        ArmMotor.set(speed);
    }
    public double ReadProximity () {
        return ColorSensor.getProximity();
      }

    public double getChannelCurrent (int channel) {
        return powerpannel.getCurrent(channel);
    }
}
