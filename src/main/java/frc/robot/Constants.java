package frc.robot;

public final class Constants {
    // Universal stop speed.
    public static final double kStopSpeed = 0;

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
        public static final int kRightFrontMotor = 6;
        public static final int kRightRearMotor  = 9;
        public static final int kLeftFrontMotor  = 7;
        public static final int kLeftRearMotor   = 8;

        // Intake motors
        public static final int kIntakePositionMotorL = -1;
        public static final int kIntakePositionMotorR = -1;
        public static final int kIntakeMotor = -1;
    
        // Motor Power Constnats
        public static final double DrivePower = 2;
        public static final double StrafePower = 2;
        public static final double TurnPower  = 2;
    
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
        public static final int kBottomLimitChannel = 1;
        public static final int kTopLimitChannel    = 2;
        //Encoder
        //public static final int kEncoderChan1 = 5;
        //public static final int kEncoderChan2 = 6;

    // Reset Speeds
        public static final double kResetSpeedMax =  0.2;
        public static final double kResetSpeedMin = -0.2;
    
    // Simple Motor Speeds
        // Intake
        public static final double kIntakePositionSpeed = 0.2;
        public static final double kIntakePushPullSpeed = 0.2;
}
