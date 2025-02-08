// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private RobotContainer m_RobotContainer;

  @Override
  public void robotInit(){
    m_RobotContainer = new RobotContainer();
    m_RobotContainer.initalized();
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic(){
    CommandScheduler.getInstance().run();
  }

}
