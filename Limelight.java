package frc.robot.Inputs;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
public class Limelight
{
    public static NetworkTable table = NetworkTableInstance.getDefault().getTable(Constants.limeLightKey);
    public static NetworkTableEntry tx = table.getEntry(Constants.targetXKey);
    public static NetworkTableEntry ty = table.getEntry(Constants.targetYKey);
    public static NetworkTableEntry tarea = table.getEntry(Constants.targetAreaKey);
    public static double x;
    public static double y;
    public static double area;
 
    public static void updateValues()
    {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = tarea.getDouble(0.);       
    }
    public static void driverCam()
    {
        table.getEntry(Constants.camModeKey).setNumber(1);
    }
    public static void lightsOff()
    {
        table.getEntry(Constants.ledModeKey).setNumber(1);
    }
    public static void lightOn()
    {
        table.getEntry(Constants.camModeKey).setNumber(3);
    }
    public static void visionCam()
    {
        table.getEntry(Constants.camModeKey).setNumber(0);
    }
}