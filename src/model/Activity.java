package model;

import java.util.ArrayList;
import java.util.Collection;

import database.DatabaseEntity;
import database.DatabaseEnum;

/**
 * A class for storing Data about an activity, such as the name
 * and a list of points
 */
public class Activity implements java.io.Serializable, DatabaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7727247509010657255L;

	/**
     * Specifies activity type
     */
    public enum ActivityType implements DatabaseEnum
    {
        Walking(0),
        Running(1),
        Cycling(2),
        Swimming(3),
        Workout(4),
        Aerobics(5),
        Volleyball(6),
        Baseball(7),
        Surfing(8),
        Skateboarding(9),
        Climbing(10),
        Soccer(11),
        Rugby(12),
        Diving(13),
        Boxing(14),
        Gymnastics(15),
        Ballet(16),
        Basketball(17),
        CheeseTasting(18),	// easter egg!
        Unknown(19);
        
        private final int value;
        private ActivityType(int value) {
            this.value = value;
        }
        
        @Override
        public int getIndex() {
            return value;
        }
        
        public static ActivityType forCode(int code) {
		    return ActivityType.values()[code];
        }

		@Override
		public String getName() {
			return this.toString();
		}
    }

    protected ActivityType activityType;

    protected String name;
    protected ArrayList<DataPoint> points;

    /**
     * A constructor
     * @param name The name of this activity
     * @param points The points associated with this activity
     */
    public Activity(String name, ArrayList<DataPoint> points){
        this.name = name;
        this.points = points;
    }

    /**
     * A constructor. Sets 'points' to an empty arraylist
     * @param name The name of this activity
     */
    public Activity(String name){
        this(name, new ArrayList<DataPoint>());
    }

    /**
     * Sets the name of this activity
     * @param name The new name for this activity
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the name of this activity
     * @return The name of this activity
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the type of this activity
     * @return The activity  type
     */
    public ActivityType getActivityType() {
        return this.activityType;
    }

    /**
     * Sets the type of the activity. Helps with analysis
     * @param type The type of the activity
     */
    public void setActivityType(ActivityType type){
        this.activityType = type;
    }

    /**
     * Sets the points associated with this activity
     * @param points The points associated with this activity
     */
    public void setPoints(ArrayList<DataPoint> points){
        this.points = points;
    }

    /**
     * Gets the list of DataPoints associated with this activity
     * @return The points.
     */
    public Collection<DataPoint> getPoints(){
        return points;
    }

    @Override
    public String toString(){
        return name;
    }

}
