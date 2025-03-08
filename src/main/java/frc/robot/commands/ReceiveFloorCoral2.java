package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;

public class ReceiveFloorCoral2 extends Command {
    private Arm arm;
    private Claw claw;
    private Elevator elevator;
    private boolean done = false;

    public ReceiveFloorCoral2(Claw c, Arm a, Elevator e){
        claw = c;
        arm = a;
        elevator=e;
    }

    @Override
    public void execute(){
        double speed = -1*0.5;
        double proximity = arm.ReadProximity();
        double position = elevator.getPIDPos();

        if (proximity < 600) {
            System.out.println("proximity < 600");
            System.out.printf("Elevator Position %f \n",position);
            elevator.setPosition(-5.0);
            claw.clawPull(speed); 
        }
        else {
            System.out.println("proximity greater 600");
            System.out.printf("Elevator Position %f \n",position);
            claw.clawPull(0);
            if (position<-7) done=true;
            //elevator.setPosition(-7.89);
        }
        System.out.printf("[%f Claw recieve] pos: %f, claw speed: %f, done: %b\n", Timer.getTimestamp(), position, claw.getSpeed(), done);
        

    }

    @Override
    public boolean isFinished(){
        return done;
    }
}