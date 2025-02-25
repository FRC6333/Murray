package frc.robot;

public final class Constants {
    // Speeds.
    public static final double kStop = 0;
    public static final double kGentley = 0.1;
    public static final double kHalf    = 0.5;
    public static final double kFull    = 0.95;

    // Button Preset Settings
    public static final double kDoNothing = 100;

    // Joystick assignments.
    public static final int kDriveJoystick = 0;
    public static final int kArmJoystick = 1;

    // Drive flipping
    public static final double kInvertForward = -1;
    public static final double kInvertStrafe = -1*kInvertForward;
    public static final double kInvertTurn = 1;

    //motor assignement constants
        //Drive Motors
        public static final int kRightFrontMotor = 2;
        public static final int kRightRearMotor  = 4;
        public static final int kLeftFrontMotor  = 1;
        public static final int kLeftRearMotor   = 3;

        // Intake motors
        public static final int kIntakePositionMotorL = 6;
        public static final int kIntakePositionMotorR = 5;
        public static final int kIntakeMotor          = 7;

        // Elevator motor
        public static final int kElevatorMotor = 8;

        // Arm Motors
        public static final int kArmMotor = 11;
        public static final int kWristMotor   = 9;
        public static final int kClawMotor    = 10;
    
        // Motor Power Constants
        public static final double DrivePower  = 2;
        public static final double StrafePower = 2;
        public static final double TurnPower   = 2;
    
    // PID Constants
        //Bottom PID Constants
        // PID coefficients
        public static final double BkP         =  0.2; 
        public static final double BkI         =  0.0;
        public static final double BkD         =  0.0; 
        public static final double BkIz        =  0; 
        public static final double BkFF        =  0; 
        public static final double BkMaxOutput =  0.3; 
        public static final double BkMinOutput = -0.3;

        //Top PID Contants
        //PID coefficients
        public static final double TkP         =  0.2; 
        public static final double TkI         =  0.0;
        public static final double TkD         =  0.0; 
        public static final double TkIz        =  0; 
        public static final double TkFF        =  0; 
        public static final double TkMaxOutput =  0.3; 
        public static final double TkMinOutput = -0.3;

    // Digital Inputs
        // Intake Limits
        public static final int kInkakeLeftLimitChannel = 2;
        public static final int kIntakeRightLimitChannel = 4;
        
        // Elevator Limit
        public static final int kElevatorLimitChannel = 0;
        
        // Arm Limits
        public static final int kArmLimitChannel = 1;
        public static final int kWristLimitChannel = 3;


    // Encoder Limit Values
        // Intake
        public static final double kIntakePositionDownLimit = -5; // Correct ans is -7.4
        // Arm Limits
        public static final int kArmEncoderLimit = 1000;
        public static final int kWristEncoderLimit = 1000;
        public static final double kElevatorUsableHeightLimit = 1000;
        

    // Reset Speeds
        public static final double kResetSpeedMax =  0.2;
        public static final double kResetSpeedMin = -0.2;
        
        
}
