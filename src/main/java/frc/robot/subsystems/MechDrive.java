package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class MechDrive extends SubsystemBase {
    //Define parts of the DriveSubystem
  private SparkMax  RightFrontDriveMotor = new SparkMax(Constants.kRightFrontMotor, MotorType.kBrushless);
  private SparkMax  RightRearDriveMotor  = new SparkMax(Constants.kRightRearMotor, MotorType.kBrushless);
  private SparkMax  LeftFrontDriveMotor  = new SparkMax(Constants.kLeftFrontMotor, MotorType.kBrushless);
  private SparkMax  LeftRearDriveMotor   = new SparkMax(Constants.kLeftRearMotor, MotorType.kBrushless);

  private MecanumDrive mechDrive = new MecanumDrive(LeftFrontDriveMotor, LeftRearDriveMotor, RightFrontDriveMotor, RightRearDriveMotor);

  public MechDrive() {
    SparkMaxConfig driveConfig = new SparkMaxConfig();
    driveConfig.idleMode(IdleMode.kBrake);

    SparkMaxConfig driveConfigInverted = new SparkMaxConfig();
    driveConfigInverted.idleMode(IdleMode.kBrake);
    driveConfigInverted.inverted(true);
        
    RightFrontDriveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    RightRearDriveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    LeftFrontDriveMotor.configure(driveConfigInverted, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    LeftRearDriveMotor.configure(driveConfigInverted, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void translate(double forward, double side){
    mechDrive.driveCartesian(forward, side, 0.0);
  }

  public void rotate(double rotation){
    mechDrive.driveCartesian(0.0, 0.0, rotation);
  }

  public void drive(double forward, double side, double rotation){
    mechDrive.driveCartesian(forward, side, rotation);
  }

  public void stop() {
    RightFrontDriveMotor.set(0);
    LeftFrontDriveMotor.set(0);
    RightRearDriveMotor.set(0);
    LeftRearDriveMotor.set(0);
  }

}
