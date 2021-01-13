
package org.usfirst.frc.team5986.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5986.robot.commands.ExampleCommand;
import org.usfirst.frc.team5986.robot.subsystems.ExampleSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//double intakeSpeed = .9;
	double elevatorDeadZone = .075;
	double intakeDeadZone = .05;
	double intakeMax = .7;

	public static Subsystem kExampleSubsystem;
	final String defaultAuto = "Default";
    	final String customAuto = "My Auto";
   	WPI_TalonSRX FrontLeftMotor = new WPI_TalonSRX(2);
	WPI_TalonSRX FrontRightMotor = new WPI_TalonSRX(4);
	WPI_TalonSRX BackLeftMotor = new WPI_TalonSRX(1);
	WPI_TalonSRX BackRightMotor = new WPI_TalonSRX(3);
	WPI_VictorSPX grabberLeft = new WPI_VictorSPX(7);
	WPI_VictorSPX grabberRight = new WPI_VictorSPX(6);
RobotDrive myRobot = new RobotDrive(FrontLeftMotor, BackLeftMotor, FrontRightMotor, BackRightMotor);
	RobotDrive intakeDrive = new RobotDrive(grabberLeft, grabberRight);
	VictorSPX elevatorLeft = new VictorSPX(9);
	VictorSPX elevatorRight = new VictorSPX(8);


	double elevatorSpeed;
	double intakeSpeedL;
	double intakeSpeedR;
	//Joystick  = new Joystick(1);
	Joystick Assistant = new Joystick(0);
	Joystick Driver = new Joystick(1);
	double change;
	double change2;
	double change3; /** new limit **/
	double limitedJoystick = 0;
	double limitedJoystick2 = 0;
	double limitedJoystick3 = 0;
	double limit = .05;
	double joyY;
	Compressor myCompressor = new Compressor(0);
	PlotThread plotThread;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		myCompressor.start();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//if (autonomousCommand != null)
			//autonomousCommand.cancel();
		BackLeftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		BackLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		FrontRightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		FrontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		plotThread = new PlotThread(this);
		new Thread(plotThread).start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		myRobot.arcadeDrive(Driver);
		change3 = Driver.getY() - limitedJoystick3;
		if (change3 > limit) { // Pushing the stick cause robot to go
    		change3 = limit;
    	} else if (change3 < -limit) {
    		change3 = -limit;
    	}

    	limitedJoystick3 += change3;

    	/** Elevator Up and Down*/
    	/*if (Assistant.getRawButton(1)) { // elevator down
    		elevatorLeft.set(ControlMode.PercentOutput, 0.4);
    		elevatorRight.set(ControlMode.PercentOutput, -0.4);
    	}else if (Assistant.getRawButton(4)) { // elevator down
        		elevatorLeft.set(ControlMode.PercentOutput, -0.4);
        		elevatorRight.set(ControlMode.PercentOutput, 0.4);
    	} else {
    		elevatorLeft.set(0);
    		elevatorRight.set(0);
    	}*/


    	/*if(Assistant.getRawButton(6)) {
    		//intake
    		grabberLeft.set(ControlMode.PercentOutput, intakeSpeed);
    		grabberRight.set(ControlMode.PercentOutput, intakeSpeed*-1);
    	} else if (Assistant.getRawButton(5)) {
    		//outtake
    		grabberLeft.set(ControlMode.PercentOutput, intakeSpeed*-1);
    		grabberRight.set(ControlMode.PercentOutput, intakeSpeed);
    	} else {
    		grabberLeft.set(ControlMode.PercentOutput, 0);
    		grabberRight.set(ControlMode.PercentOutput, 0);
    	}*/
    	/*if (Math.abs(Driver.getRawAxis(5)) < intakeDeadZone) {
    		intakeSpeed = 0;
    	} else if (Math.abs(Driver.getRawAxis(4)) < intakeDeadZone) {
    		intakeSpeed = 0;
    	} else if (Driver.getRawAxis(5) > intakeMax){
    		intakeSpeed = intakeMax;
    	} else if (Driver.getRawAxis(4) > intakeMax){
    		intakeSpeed = intakeMax;
    	} else {
    		intakeSpeed = Driver.getRawAxis(4);
    		intakeSpeed = Driver.getRawAxis(5);
    	}*/
    	if (Math.abs(Driver.getRawAxis(5)) < intakeDeadZone && Math.abs(Driver.getRawAxis(4)) < intakeDeadZone) {
    		intakeSpeedL = 0;
    		intakeSpeedR = 0;
    	} else if(Math.abs(Driver.getRawAxis(5)) > Math.abs(Driver.getRawAxis(4))) {
    		intakeSpeedL = Driver.getRawAxis(5)*-1;
    		intakeSpeedR = Driver.getRawAxis(5);
    	} else if (Math.abs(Driver.getRawAxis(5)) < Math.abs(Driver.getRawAxis(4))) {
    		intakeSpeedL = Driver.getRawAxis(4);
    		intakeSpeedR = Driver.getRawAxis(4);
    	}
    	if (Math.abs(intakeSpeedL) > intakeMax) {
    		if (intakeSpeedL < 0) {
    			intakeSpeedL = intakeMax*-1;
    		} else {
    			intakeSpeedL = intakeMax;
    		}
    	}
    	if (Math.abs(intakeSpeedR) > intakeMax) {
    		if (intakeSpeedR < 0) {
    			intakeSpeedR = intakeMax*-1;
    		} else {
    			intakeSpeedR = intakeMax;
    		}
    	}
    	grabberLeft.set(ControlMode.PercentOutput, intakeSpeedL);
    	grabberRight.set(ControlMode.PercentOutput, intakeSpeedR);
    	//intakeDrive.arcadeDrive(Driver);
    	if (Math.abs(Assistant.getY()) < elevatorDeadZone) {
    		elevatorSpeed = 0;
    	} else {
    		elevatorSpeed = Assistant.getY();
    	}
    	//System.out.println(Assistant.getY());
    	elevatorLeft.set(ControlMode.PercentOutput, elevatorSpeed);
    	elevatorRight.set(ControlMode.PercentOutput, elevatorSpeed*-1);

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	class PlotThread implements Runnable {
		 Robot robot;
		 public PlotThread(Robot robot) { this.robot = robot; }

		 public void run() {
			//NetworkTable.setUpdateRate(0.010);
			 while (true) {
				try { Thread.sleep(1); } catch (Exception e) { }
				double speedl = this.robot.BackLeftMotor.getSelectedSensorVelocity(0);
				double speedr = this.robot.FrontRightMotor.getSelectedSensorVelocity(0);
				SmartDashboard.putNumber("Left", speedl);
				SmartDashboard.putNumber("Right", speedl);
			 }
		}
	}
}
