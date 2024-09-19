package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class drive extends OpMode
{   
    float lsx;
    float rt;
    float lt;
    
    float triggerThreshold;
    
    Object leftMotor;
    Object rightMotor;
    
    double motorPower;
    
    double turnSpeed;
    
    double lpwr;
    double rpwr;
    
    @ Override
    public void init()
    {
        triggerThreshold = 0;
        motorPower = 1;
        turnSpeed = 0.5;
    }

    @ Override
    public void loop()
    {
        // Update gamepad variables
        lsx = gamepad1.left_stick_x;
        rt = gamepad1.right_trigger;
        lt = gamepad1.left_trigger;
        
        lpwr = 0;
        rpwr = 0;
        
        if (rt > triggerThreshold) {
            lpwr -= motorPower;
            rpwr += motorPower;
        }
        
        if (lt > triggerThreshold) {
            lpwr += motorPower;
            rpwr -= motorPower;
        }
        
        if (lsx > 0) {
            rpwr -= turnSpeed;
            lpwr -= turnSpeed;
        } else if (lsx < 0) {
            lpwr += turnSpeed;
            rpwr += turnSpeed;
        }
        
        hardwareMap.get(DcMotor.class, "0").setPower(rpwr);
        hardwareMap.get(DcMotor.class, "1").setPower(lpwr);
        
        
        telemetry.addData("Left stick x", gamepad1.left_stick_x);
        telemetry.addData("Right trigger", gamepad1.right_trigger);
        telemetry.addData("Status", "Running");
        telemetry.update();
        
    }
}
