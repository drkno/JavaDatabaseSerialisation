package analysis.calories;

import model.Activity;
import model.DataPoint;
import model.Gender;
import profiles.UserProfile;

import java.util.Collection;

/**
 * An interface different calorie analysers use to provide a consistent interface
 */
public class CalorieAnalyser {
    /**
     * Gets the calories a user burnt on a specific trip
     * @param activities A list of activities the analyser will run on
     * @param profile The user that this analysis is for. This is passed in because different
     *                weights/heights/ages might burn different amounts of calories for the same exercise
     * @return The number of calories burnt over these activities
     */
    public float getCalories(Collection<Activity> activities, UserProfile profile)
    {
    	float totalCalories = 0.0f;
    	for (Activity activity : activities)
    	{
    		DataPoint[] dataPoints = (DataPoint[])activity.getPoints().toArray();
    		for (int i = 0; i < dataPoints.length; i++)	// loop through each datapoint of each activity
    		{											// and calculate calories
    			totalCalories += calculateCaloriesAtDataPoint(dataPoints[i], i == 0 ? null : dataPoints[i-1], profile);
    		}
    	}
    	return totalCalories;
    }
    
    /**
     * Calculates the calories burnt between two points
     * @param point The point to calculate calories for.
     * @param previousPoint The previous point
     * @return The calories burnt
     */
    public static float calculateCaloriesAtDataPoint(DataPoint point, DataPoint previousPoint, UserProfile profile)
    {
    	if (previousPoint == null) return 0f;
        // Source for calculation: http://fitnowtraining.com/2012/01/formula-for-calories-burned/
        float caloriesBurned = 0;
        double timeInterval = (previousPoint.getDate().getTime() - point.getDate().getTime()) / 60000.0; // In minutes
        
        // If male
        if (profile.getGender() == Gender.Male) {
        	caloriesBurned = (float) (((profile.getAge() * 0.2017) - (profile.getWeight() * 0.09036) + (point.getHeartRate() * 0.6309) - 55.0969) * timeInterval / 4.184);
        }
        // If female
        else if (profile.getGender() == Gender.Female) {
        	caloriesBurned = (float) (((profile.getAge() * 0.074) - (profile.getWeight() * 0.05741) + (point.getHeartRate() * 0.4472) - 20.4022) * timeInterval / 4.184);
        }
        // Else unspecified...
        else {
        	// Average?
        	// Male?
        	// Female?
        	// Random?
        }
        return Math.abs(caloriesBurned);
    }
}
