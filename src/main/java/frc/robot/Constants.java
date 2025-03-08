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
    //public static final int kArmJoystick = 1;
    public static final int kBox1 = 1;
    public static final int kBox2 = 2;

    // Drive flipping
    public static final double kInvertForward = 0.8;
    public static final double kInvertStrafe = -1*kInvertForward;
    public static final double kInvertTurn = -0.5;

    //motor assignement constants
        //Drive Motors
        public static final int kRightFrontMotor = 3;
        public static final int kRightRearMotor  = 1;
        public static final int kLeftFrontMotor  = 2;
        public static final int kLeftRearMotor   = 4;

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
        public static final double ElekP         =  0.2; 
        public static final double ElekI         =  0.002;
        public static final double ElekD         =  0.0; 

        //Top PID Contants
        //PID coefficients
        public static final double ArmkP         =  0.06; 
        public static final double ArmkI         =  0.0006;
        public static final double ArmkD         =  0.0; 

    // Digital Inputs
        // Intake Limits
        public static final int kInkakeLeftLimitChannel = 1;
        public static final int kIntakeRightLimitChannel = 4;
        
        // Elevator Limit
        public static final int kElevatorLimitChannel = 3; //0 -> 3
        
        // Arm Limits
        public static final int kArmLimitChannel = 2; //1 -> 2
        public static final int kWristLimitChannel = 0;


    // Encoder Limit Values
        // Intake
        public static final double kIntakePositionDownLimit = -5; // Correct ans is -7.4
        // Arm Limits
        public static final double kArmSafeLimit = -42.593;
        public static final double kArmMaxLimit  = -92.593;
        public static final double kWristEncoderLimit = -20.5;
        // Elevator Limit
        public static final double kElevatorSafeHigh = -70.625;
        public static final double kElevatorSafeLow = -30.836;
        public static final double kElevatorMaxLimit = -208.095;
        

    // Reset Speeds
        public static final double kResetSpeedMax =  0.2;
        public static final double kResetSpeedMin = -0.2;
        
        
}
