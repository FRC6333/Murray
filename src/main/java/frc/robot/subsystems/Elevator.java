package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase{
    private SparkMax ElevatorMotor = new SparkMax(Constants.kElevatorMotor, MotorType.kBrushless);

    private DigitalInput BottomLimit = new DigitalInput(Constants.kElevatorLimitChannel);
    
    private RelativeEncoder ElevatorEncoder = ElevatorMotor.getEncoder();

    private PIDController ElevatorPID = new PIDController(Constants.BkP, Constants.BkI, Constants.BkD);

    private static int EncoderTopLimit = 1000;

    

    public Elevator(){
        SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        elevatorConfig.idleMode(IdleMode.kBrake);
        ElevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        ElevatorEncoder.setPosition(0);
    }

    public boolean getElevatorLimit(){
        return !BottomLimit.get();
    }

    public double getElevatorEncoder(){
        return ElevatorEncoder.getPosition();
    }

    public void moveElevator(double speed){
        //System.out.printf("%f  %f  %b\n", speed, getElevatorEncoder(), getElevatorLimit());
        if (speed < 0 && ElevatorEncoder.getPosition() >= EncoderTopLimit){
            ElevatorMotor.set(0f);
        }
        else if(speed > 0 && getElevatorLimit()){
            ElevatorMotor.set(0f);
            ElevatorEncoder.setPosition(0);
        }
        else{
            // TODO: Reduce speed NEAR top/bottom limits
            ElevatorMotor.set(speed);
        }
    }

    public void setPosition(double pos){
        double tolerance = 0.0000001;
        System.out.printf("Start PID\n");
        while(getElevatorEncoder() < (pos-tolerance) || getElevatorEncoder() > (pos+tolerance)){
            ElevatorPID.setSetpoint(pos);
            double speed = ElevatorPID.calculate(getElevatorEncoder());
            System.out.printf("%f  %f  %f\n", getElevatorEncoder(), speed, ElevatorPID.getError());
            ElevatorMotor.set(speed);
        }
        ElevatorMotor.set(Constants.kStop);
        System.out.printf("End PID\n");
    }
}
