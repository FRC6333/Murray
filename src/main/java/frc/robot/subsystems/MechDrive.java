package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class MechDrive extends SubsystemBase {
    //Define parts of the DriveSubystem
  private WPI_VictorSPX  RightFrontDriveMotor = new WPI_VictorSPX(Constants.kRightFrontMotor);
  private WPI_VictorSPX  RightRearDriveMotor  = new WPI_VictorSPX(Constants.kRightRearMotor);
  private WPI_VictorSPX  LeftFrontDriveMotor  = new WPI_VictorSPX(Constants.kLeftFrontMotor);
  private WPI_VictorSPX  LeftRearDriveMotor   = new WPI_VictorSPX(Constants.kLeftRearMotor);

  private MecanumDrive mechDrive = new MecanumDrive(LeftFrontDriveMotor, LeftRearDriveMotor, RightFrontDriveMotor, RightRearDriveMotor);

  public MechDrive() {
    LeftFrontDriveMotor.setInverted(true);
    LeftRearDriveMotor.setInverted(true); 

    RightFrontDriveMotor.setNeutralMode(NeutralMode.Brake);
    RightRearDriveMotor.setNeutralMode(NeutralMode.Brake);
    LeftFrontDriveMotor.setNeutralMode(NeutralMode.Brake);
    LeftRearDriveMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void translate(double forward, double side){
    mechDrive.driveCartesian(forward, side, 0.0);
  }

  public void rotate(double rotation){
    mechDrive.driveCartesian(0.0, 0.0, rotation);
  }

  public void stop() {
    RightFrontDriveMotor.set(0);
    LeftFrontDriveMotor.set(0);
    RightRearDriveMotor.set(0);
    LeftRearDriveMotor.set(0);
  }

}
