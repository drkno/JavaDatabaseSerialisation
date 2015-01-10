package profiles;

import model.Activity;
import model.Gender;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import database.DatabaseEntity;

/**
 * A class for storing user profile data
 */
public class UserProfile implements java.io.Serializable, DatabaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
    private Date birthDate;
    private Gender gender;
    private float height;
    private float weight;

    private String picturePath;

    private Collection<Activity> activities;

    /**
     * Create uninitialised UserProfile
     */
    public UserProfile(){}
    
    /**
     * Creates a new UserProfile
     * @param profileName Name of the profile
     * @param birthDate Birth date of the user
     * @param height Height of the user
     * @param weight Weight of the user
     * @param activities Activities performed by the user
     */
    public UserProfile(String profileName, Date birthDate, float height,
			float weight, Gender gender, Collection<Activity> activities)
	{
		this.name = profileName;
		this.birthDate = birthDate;
		this.height = height;		// initialise variables
		this.weight = weight;
		this.activities = activities;
		this.gender = gender;
	}

	/**
     * Gets the name of the profile
     * @return The name of the profile
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the birth date of the user
     * @return The birth date of the user
     */
    public Date getBirthDate(){
        return birthDate;
    }

    /**
     * Gets the height of the user
     * @return the height of the user
     */
    public float getHeight(){
        return height;
    }

    /**
     * Gets the BMI of a user
     * @return The users BMI
     */
    public float getBMI() { return height != 0 ? weight/ height*height : 0; }

    /**
     * Gets the weight of the user
     * @return The weight of the user
     */
    public float getWeight(){
        return weight;
    }

    /**
     * Gets the path of the users profile picture
     * @return The path to the users profile picture
     */
    public String getPicturePath(){
        return picturePath;
    }

    /**
     * Gets a list of the users activities
     * @return The users activities
     */
    public Collection<Activity> getActivities(){
        return activities;
    }
    
    /**
     * Gets the gender of the user
     * @return Gender
     */
    public Gender getGender(){
    	return gender;
    }
    
    /**
     * Gets the users age in years
     * @return age in years
     */
    public int getAge()
    {
    	Date now = new Date();				// get dates
    	Date birthDate = getBirthDate();

    	// create calendars
    	Calendar a = Calendar.getInstance(Locale.ENGLISH);
    	a.setTime(birthDate);
        Calendar b = Calendar.getInstance(Locale.ENGLISH);
        b.setTime(now);
        
        // calculate difference
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
            (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) &&
            a.get(Calendar.DATE) > b.get(Calendar.DATE)))
        {
            diff--;
        }
        return diff;
    }
}
