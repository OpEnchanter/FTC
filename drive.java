package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class drive extends OpMode
{   
    boolean drivingAllowed;
    
    TouchSensor touch;
    
    float lsx;
    float rsx;
    float rt;
    float lt;
    boolean dpu;
    boolean dpd;
    boolean gpx;
    
    float triggerThreshold;
    
    Object leftMotor;
    Object rightMotor;
    
    double motorPower;
    
    double turnPower;
    double slowTurnPower;
    
    double tickTurnPower;
    
    double lpwr;
    double rpwr;
    
    double aspd; // Arm speed
    
    double apwr;
    
    boolean flipped;
    
    @ Override
    public void init()
    {
        triggerThreshold = 0;
        motorPower = 1;
        turnPower = 0.5;
        slowTurnPower = 0.1;
        aspd = 1;
        drivingAllowed = true;
        flipped = false;
    }

    @ Override
    public void loop()
    {
        if (hardwareMap.get(TouchSensor.class, "3").isPressed()) {
            drivingAllowed = true;
        }
        
        // Update gamepad variables
        lsx = gamepad1.left_stick_x;
        rsx = gamepad1.right_stick_x;
        rt = gamepad1.right_trigger;
        lt = gamepad1.left_trigger;
        
        dpu = gamepad1.y;
        dpd = gamepad1.a;
        
        gpx = gamepad1.x;
        
        if (gpx) {
            flipped = !flipped;
        }
        
        
        // Drive motors
        lpwr = 0;
        rpwr = 0;
        
        if (rt > triggerThreshold) {
            lpwr -= motorPower * rt;
            rpwr += motorPower * rt;
        }
        
        if (lt > triggerThreshold) {
            lpwr += motorPower * lt;
            rpwr -= motorPower * lt;
        }
        
        if (flipped) {
            rpwr *= -1;
            lpwr *= -1;
        }
        
        if (lsx > 0) {
            rpwr -= turnPower;
            lpwr -= turnPower;
        } else if (lsx < 0) {
            lpwr += turnPower;
            rpwr += turnPower;
        }
        
        if (rsx > 0) {
            rpwr -= slowTurnPower;
            lpwr -= slowTurnPower;
        } else if (rsx < 0) {
            lpwr += slowTurnPower;
            rpwr += slowTurnPower;
        }
        
        // Arm motors
        
        apwr = 0;
        
        if (dpu) {
            apwr -= aspd;
        } else if (dpd) {
            apwr += aspd;
        }
        
        if (drivingAllowed){
            hardwareMap.get(DcMotor.class, "0").setPower(rpwr);
            hardwareMap.get(DcMotor.class, "1").setPower(lpwr);
            hardwareMap.get(DcMotor.class, "2").setPower(apwr);
        }
        
        telemetry.addData("Flipped", flipped);
        telemetry.addData("", "");
        telemetry.addData("Input Data", "");
        telemetry.addData("Left stick x", gamepad1.left_stick_x);
        telemetry.addData("Right trigger", gamepad1.right_trigger);
        telemetry.addData("Dpad Up", dpu);
        telemetry.addData("Dpad Down", dpd);
        telemetry.addData("", "");
        telemetry.addData("Motor Data", "");
        telemetry.addData("LPWR", lpwr);
        telemetry.addData("RPWR", rpwr);
        telemetry.addData("APWR", apwr);
        telemetry.update();
        
    }
}
