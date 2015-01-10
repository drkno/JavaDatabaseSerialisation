package model;

import java.util.Date;

import database.DatabaseEntity;
import database.Program;

/**
 * A class containing data recorded in the .CSV files.
 * Note: this has been created based off the sample data. I
 * noticed that it's different in the specifications that
 * they gave us (no speed or distance attribute).
 */
public class DataPoint implements java.io.Serializable, DatabaseEntity {
    /**
	 * Unique Serialization ID
	 */
	private static final long serialVersionUID = 3987674778905565265L;

	private Date date;

    private float heartRate;

    private float distance;

    private float speed;

    private float calories;

    private Location location;

    /**
     * The constructor
     * @param date The date at which the point was recorded
     * @param heartRate The heart rate when the point was recorded
     * @param location The location at which the point was recorded
     */
    public DataPoint(Date date, float heartRate, Location location, DataPoint previousPoint){
        this.date = date;
        this.location = location;
        this.heartRate = heartRate;
        this.distance = calculateDistance(previousPoint);
        this.speed = calculateSpeed(previousPoint);
        this.calories = analysis.calories.CalorieAnalyser.calculateCaloriesAtDataPoint(this, previousPoint, Program.profile);
    }

    /**
     * Gets the date a point was recorded on. This contains both the date and time
     * @return The date and time
     */
    public Date getDate(){
        return date;
    }

    /**
     * Gets the heart rate at this point
     * @return The heart rate
     */
    public float getHeartRate(){
        return heartRate;
    }

    /**
     * Gets the location of this point
     * @return The location
     */
    public Location getLocation(){
        return location;
    }
    
    /**
     * Gets the speed at this point.
     * @return the speed in meters per second
     */
    public float getSpeed()
    {
    	return speed;
    }

    /**
     * Gets the distance between this point and the previous
     * @return The distance
     */
    public float getDistance() {return distance; }
    
    /**
     * Gets the calories burnt between this point and the last one
     * @return The calories burnt
     */
    public float getCalories() {return calories; }

    /**
     * Gets the distance between this point and another
     * @param otherPoint The other point
     * @return The distance
     */
    private float calculateDistance(DataPoint otherPoint){
        if (otherPoint == null) return 0;
        return Location.haverSineDistance(otherPoint.getLocation(), getLocation());
    }
    
    /**
     * Calculates the average speed between two points
     * @param otherPoint The other point
     * @return The Average Speed in meters per sec
     */
    private float calculateSpeed(DataPoint otherPoint){
    	// assume 0 speed
    	if (otherPoint == null) return 0.0f;
    	// get time difference (milliseconds)
    	long timeInterval = Math.abs(otherPoint.getDate().getTime() - getDate().getTime());
    	timeInterval /= 1000; // convert to seconds
    	// get the distance travelled between points

    	// calculate speed
        if (timeInterval < 0.01) return 0;
    	float speed = distance / timeInterval;
    	return speed;
    }
}
