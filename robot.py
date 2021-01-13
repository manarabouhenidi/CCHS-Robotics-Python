import wpilib
import wpilib.drive
import wpilib.buttons

class MyRobot(wpilib.TimedRobot):

    def robotInit(self):
        """
        This function is called upon program startup and
        should be used for any initialization code.
        """
        self.frontLeft = wpilib.Talon(2)
        self.rearLeft = wpilib.Talon(1)
        self.left = wpilib.SpeedControllerGroup(self.frontLeft, self.rearLeft)

        self.frontRight = wpilib.Talon(4)
        self.rearRight = wpilib.Talon(3)
        self.right = wpilib.SpeedControllerGroup(self.frontRight, self.rearRight)

        self.drive = wpilib.drive.DifferentialDrive(self.left, self.right)
        self.drive.setSafetyEnabled(False)
        self.Assistant = wpilib.Joystick(0)
        self.Driver = wpilib.Joystick(1)
        self.timer = wpilib.Timer()
        self.grabberLeft = wpilib.Victor(7)
        self.grabberRight = wpilib.Victor(6)
        self.elevatorLeft = wpilib.Victor(9)
        self.elevatorRight = wpilib.Victor(8)
        self.myCompressor = wpilib.Compressor(0)
        #self.toggle = toggle(self.Assistant, 6)

    def autonomousInit(self):
        """This function is run once each time the robot enters autonomous mode."""
        self.timer.reset()
        self.timer.start()

    def autonomousPeriodic(self):
        """This function is called periodically during autonomous."""

        # Drive for two seconds
        if self.timer.get() < 10.0:
            self.drive.arcadeDrive(-0.5, 0)  # Drive forwards at half speed
        else:
            self.drive.arcadeDrive(0, 0)  # Stop robot

    def teleopPeriodic(self):
        """This function is called periodically during operator control."""
        self.drive.arcadeDrive(self.Driver.getY(), self.Driver.getX())#Robot Wheels
        if self.Assistant.getRawButton(6): #Button press number 6 on assistant controller
            self.grabberRight.set(0.4) #Right grabber motor set to .4% power



if __name__ == "__main__":
    wpilib.run(MyRobot)
