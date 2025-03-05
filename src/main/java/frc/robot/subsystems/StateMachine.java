package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.LowerIntake;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.RaiseIntake;
import frc.robot.commands.WristHorizontal;
import frc.robot.commands.WristVertical;

public class StateMachine extends SubsystemBase {
    public enum RobotState {
        SS,    // starting configuration
        SINT,  // floor intake down lift down
        SINT2, // floor intake lift up a little
        SSF,   // intake down arm safe to move
        SSF2,  // intake down arm safe to move - arm up
        SHO,   // human handoff
        SL1D,  // L1 score intake down
        SL1,   // L1 score
        SL2D,  // L2 score intake down
        SL2,   // L2 score
        SL3D,  // L3 score intake down
        SL3,   // L3 score
        SL4D,  // L4 score intake down
        SL4,   // L4 score
        SA2D,  // L2 algae removal intake down
        SA2,   // L2 algae removal
        SA3D,  // L3 algae removal intake down
        SA3,   // L3 algae removal
        SB,    // barge
        SH     // ready to hang
    }

    public enum ActuatorPosition {
        LI (-7.958),    //lift floor intake handoff ready position
        LS (-30.836),   //lift safe lowest limit with intake down
        LSU(-70.625),  //lift safe lowest limit with intake up
        LHO(-71.978),  //lift human handoff 
        LC1(0.000),   //lift L1 coral scoring
        LC2(-20.730),  //lift L2 coral scoring
        LC3(-90.042),  //lift L3 coral scoring
        LC4(-205.031), //lift L4 coral scoring
        LA2(-53.317),  //lift L2 algae removal
        LA3(-116.462), //lift L3 algae removal
        LB (-192.219),  //lift barge position
        LH (-77.986),   //lift ready to hang position
        LT (-208.095),  //lift maximum safe limit
        AS (-42.593),   //arm up clear the intake
        AH (-50.000),   //arm human handoff
        AC1(-66.824),  //arm L1 coral scoring
        AC2(-71.722),  //arm L2 coral scoring
        AC3(-71.722),  //arm L3 coral scoring
        AC4(-64.463),  //arm L4 coral scoring
        AA2(-54.120),  //arm L2 algae removal
        AA3(-54.120),  //arm L3 algae removal
        AB (-152.259),  //arm barge position
        AT (-92.593);   //arm maximum safe limit

        private double position;
        
        ActuatorPosition(double position){
            this.position = position;
        }

        public double getPostion(){
            return position;
        }
    }

    private RobotState current = RobotState.SS;
    private RobotState goal = RobotState.SS;
    private RobotState[] plan = null;
    private int step = 0;
    private boolean transitioning = false;

    private Intake intake;
    private Elevator elevator;
    private Arm arm;
    private Wrist wrist;
    private MoveElevator mElevator; 
    private MoveArm mArm;

    private ArrayList<Command> oneShotCommands = new ArrayList<>();
    
    


    public StateMachine(Intake intake, Elevator elevator, Arm arm, Wrist wrist, MoveElevator mElevator, MoveArm mArm){
        this.intake = intake;
        this.elevator = elevator;
        this.arm = arm;
        this.wrist = wrist;
        this.mElevator = mElevator;
        this.mArm = mArm;
    }

    private void getPlan(){
        System.out.printf("\n\nGetting Plan [%s -> %s]\n\n", current.name(), goal.name());
        plan = null;
        step = 0;
        if (current == goal) return;
        // Theres a better way to encode this awfulness, but honestly this is braindead simple enough for me to write it.
        else if(current == RobotState.SS){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SSF2, RobotState.SL1D, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SSF2, RobotState.SL2D, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SL3D, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SL4D, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SA2D, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SA3D, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SSF, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SS, RobotState.SINT, RobotState.SH};
        }
        else if(current == RobotState.SINT2){
            if(goal == RobotState.SSF2) plan      = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SSF2, RobotState.SL1D, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SSF2, RobotState.SL2D, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SL3D, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SL4D, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SA2D, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SA3D, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SINT2, RobotState.SSF, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SINT2, RobotState.SH};
        }
        else if(current == RobotState.SSF2){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SSF2, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SSF2, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SSF2, RobotState.SL1D, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SSF2, RobotState.SL2D, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SSF2, RobotState.SL3D, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SSF2, RobotState.SL4D, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SSF2, RobotState.SA2D, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SSF2, RobotState.SA3D, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SSF2, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SSF2, RobotState.SH};
        }
        else if(current == RobotState.SHO){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SHO, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SHO, RobotState.SSF2};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SHO, RobotState.SL1D, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SHO, RobotState.SL2D, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SHO, RobotState.SL3D, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SHO, RobotState.SL4D, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SHO, RobotState.SA2D, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SHO, RobotState.SA3D, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SHO, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SHO, RobotState.SH};
        }
        else if(current == RobotState.SL1){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SL1, RobotState.SL1D, RobotState.SSF2, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SL1, RobotState.SL1D, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SL1, RobotState.SL1D, RobotState.SHO};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SL1, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SL1, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SL1, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SL1, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SL1, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SL1, RobotState.SL1D, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SL1, RobotState.SL1D, RobotState.SSF2, RobotState.SSF, RobotState.SH};
        }
        else if(current == RobotState.SL2){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SL2, RobotState.SL2D, RobotState.SSF2, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SL2, RobotState.SL2D, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SL2, RobotState.SL2D, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SL2, RobotState.SL1};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SL2, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SL2, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SL2, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SL2, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SL2, RobotState.SL2D, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SL2, RobotState.SL2D, RobotState.SSF2, RobotState.SSF, RobotState.SH};
        }
        else if(current == RobotState.SL3){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SL3, RobotState.SL3D, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SL3, RobotState.SL3D, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SL3, RobotState.SL3D, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SL3, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SL3, RobotState.SL2};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SL3, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SL3, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SL3, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SL3, RobotState.SL3D, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SL3, RobotState.SL3D, RobotState.SH};
        }
        else if(current == RobotState.SL4){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SL4, RobotState.SL4D, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SL4, RobotState.SL4D, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SL4, RobotState.SL4D, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SL4, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SL4, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SL4, RobotState.SL3};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SL4, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SL4, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SL4, RobotState.SL4D, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SL4, RobotState.SL4D, RobotState.SH};
        }
        else if(current == RobotState.SA2){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SA2, RobotState.SA2D, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SA2, RobotState.SA2D, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SA2, RobotState.SA2D, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SA2, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SA2, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SA2, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SA2, RobotState.SL4};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SA2, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SA2, RobotState.SA2D, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SA2, RobotState.SA2D, RobotState.SH};
        }
        else if(current == RobotState.SA3){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SA3, RobotState.SA3D, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SA3, RobotState.SA3D, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SA3, RobotState.SA3D, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SA3, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SA3, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SA3, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SA3, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SA3, RobotState.SA2};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SA3, RobotState.SA3D, RobotState.SB};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SA3, RobotState.SA3D, RobotState.SH};
        }
        else if(current == RobotState.SB){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SB, RobotState.SSF, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SB, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SB, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SB, RobotState.SL1D, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SB, RobotState.SL2D, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SB, RobotState.SL3D, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SB, RobotState.SL4D, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SB, RobotState.SA2D, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SB, RobotState.SA3D, RobotState.SA3};
            else if(goal == RobotState.SH) plan   = new RobotState[] {RobotState.SB, RobotState.SA3D, RobotState.SH};
        }
        else if(current == RobotState.SH){
            if(goal == RobotState.SINT2) plan     = new RobotState[] {RobotState.SH, RobotState.SINT2};
            else if(goal == RobotState.SSF2) plan = new RobotState[] {RobotState.SH, RobotState.SSF2};
            else if(goal == RobotState.SHO) plan  = new RobotState[] {RobotState.SH, RobotState.SHO};
            else if(goal == RobotState.SL1) plan  = new RobotState[] {RobotState.SH, RobotState.SSF, RobotState.SSF2, RobotState.SL1D, RobotState.SL1};
            else if(goal == RobotState.SL2) plan  = new RobotState[] {RobotState.SH, RobotState.SSF, RobotState.SSF2, RobotState.SL2D, RobotState.SL2};
            else if(goal == RobotState.SL3) plan  = new RobotState[] {RobotState.SH, RobotState.SSF, RobotState.SSF2, RobotState.SL3D, RobotState.SL3};
            else if(goal == RobotState.SL4) plan  = new RobotState[] {RobotState.SH, RobotState.SSF, RobotState.SSF2, RobotState.SL4D, RobotState.SL4};
            else if(goal == RobotState.SA2) plan  = new RobotState[] {RobotState.SH, RobotState.SSF, RobotState.SSF2, RobotState.SA2D, RobotState.SA2};
            else if(goal == RobotState.SA3) plan  = new RobotState[] {RobotState.SH, RobotState.SSF, RobotState.SSF2, RobotState.SA3D, RobotState.SA3};
            else if(goal == RobotState.SB) plan   = new RobotState[] {RobotState.SH, RobotState.SH};
        }        
    }

    private void executeTransition(){
        // Advance plan.
        step ++;
        if(step == plan.length){
            plan = null;
            transitioning = false;
            return;
        }
        System.out.printf("\n\nExecuting step %d [%s]\n",step, plan[step].name());
        
        // Prepare to transition.
        transitioning = true;
        
        oneShotCommands.clear();
        double armPos = 0.0;
        double elivatorPos = 0.0;

        // Setup values/commands for transition.
        if(plan[step] == RobotState.SINT){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
        }
        else if(plan[step] == RobotState.SINT2){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LI.getPostion();
        }
        else if(plan[step] == RobotState.SSF){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LS.getPostion();
        }
        else if(plan[step] == RobotState.SSF2){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LS.getPostion();
            armPos = ActuatorPosition.AS.getPostion();
        }
        else if(plan[step] == RobotState.SHO){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LHO.getPostion();
            armPos = ActuatorPosition.AH.getPostion();
        }
        else if(plan[step] == RobotState.SL1D){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC1.getPostion();
            armPos = ActuatorPosition.AC1.getPostion();
        }
        else if(plan[step] == RobotState.SL1){
            oneShotCommands.add(new RaiseIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC1.getPostion();
            armPos = ActuatorPosition.AC1.getPostion();
        }
        else if(plan[step] == RobotState.SL2D){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristVertical(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC2.getPostion();
            armPos = ActuatorPosition.AC2.getPostion();

        }
        else if(plan[step] == RobotState.SL2){
            oneShotCommands.add(new RaiseIntake(intake));
            oneShotCommands.add(new WristVertical(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC2.getPostion();
            armPos = ActuatorPosition.AC2.getPostion();

        }
        else if(plan[step] == RobotState.SL3D){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristVertical(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC3.getPostion();
            armPos = ActuatorPosition.AC3.getPostion();

        }
        else if(plan[step] == RobotState.SL3){
            oneShotCommands.add(new RaiseIntake(intake));
            oneShotCommands.add(new WristVertical(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC3.getPostion();
            armPos = ActuatorPosition.AC3.getPostion();
        }
        else if(plan[step] == RobotState.SL4D){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristVertical(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC4.getPostion();
            armPos = ActuatorPosition.AC4.getPostion();
        }
        else if(plan[step] == RobotState.SL4){
            oneShotCommands.add(new RaiseIntake(intake));
            oneShotCommands.add(new WristVertical(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LC4.getPostion();
            armPos = ActuatorPosition.AC4.getPostion();
        }
        else if(plan[step] == RobotState.SA2D){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LA2.getPostion();
            armPos = ActuatorPosition.AA2.getPostion();
        }
        else if(plan[step] == RobotState.SA2){
            oneShotCommands.add(new RaiseIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LA2.getPostion();
            armPos = ActuatorPosition.AA2.getPostion();
        }
        else if(plan[step] == RobotState.SA3D){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LA3.getPostion();
            armPos = ActuatorPosition.AA3.getPostion();
        }
        else if(plan[step] == RobotState.SA3){
            oneShotCommands.add(new RaiseIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LA3.getPostion();
            armPos = ActuatorPosition.AA3.getPostion();
        }
        else if(plan[step] == RobotState.SB){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LB.getPostion();
            armPos = ActuatorPosition.AB.getPostion();
        }
        else if(plan[step] == RobotState.SH){
            oneShotCommands.add(new LowerIntake(intake));
            oneShotCommands.add(new WristHorizontal(wrist, elevator, intake));
            elivatorPos = ActuatorPosition.LH.getPostion();
        }

        System.out.printf("Step sets eliv: %f and arm: %f\n\n", elivatorPos, armPos);
        // Set up commands to transition.
        for (Command command : oneShotCommands) {
            command.schedule();
        }
        mElevator.setControl(elivatorPos);
        mArm.setControl(armPos);

    }

    private void checkTransition(){
        boolean status = true;
        double buffer = 1; // How close to the setpoint is "close enough" in motor revolutions.
        
        for (Command command : oneShotCommands) {
            if(!command.isFinished()) status = false;
            System.out.printf("%s: %b | status: %b\n", command.getClass().getSimpleName(), command.isFinished(), status);
        }

        if(elevator.getElevatorEncoder() < (mElevator.getControl()-buffer) || elevator.getElevatorEncoder() > (mElevator.getControl()+buffer)) status = false;
        System.out.printf("elevator: %f-1 < %f < %f+1 | status: %b\n", mElevator.getControl(), elevator.getElevatorEncoder(), mElevator.getControl(), status);
        if(arm.getArmEncoder() < (mArm.getControl()-buffer) || arm.getArmEncoder() > (mArm.getControl()+buffer)) status = false;
        System.out.printf("arm: %f-1 < %f < %f+1 | status: %b\n", mArm.getControl(), arm.getArmEncoder(), mArm.getControl(), status);

        if(status){
            System.out.printf("\n\nTransition complete\n\n");
            current = plan[step];
            transitioning = false;
        }
    }

    public void setGoal(RobotState goal){
        this.goal = goal;
    }

    public void iterateStateMachine(){
        if(current == goal) return;
        
        if(plan == null) getPlan();
        else{
            if(transitioning) checkTransition();
            else executeTransition();
        }

    }


}
