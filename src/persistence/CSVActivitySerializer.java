package persistence;

import model.Activity;
import model.DataPoint;
import model.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import analysis.calories.CalorieAnalyser;

/**
 * Provides methods for loading points from CSV files
 */
public class CSVActivitySerializer {
    private String path;
    public CSVActivitySerializer(String path){
        this.path = path;
    }

    /**
     * Loads a list of activities from a CSV file
     * @return The activities
     */
    public ArrayList<Activity> loadData() {
        //This is the format of our dateTime string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");

        try {
            //Make a new arraylist of activities. This is what we'll return
            ArrayList<Activity> activities = new ArrayList<Activity>();

            //Create the reader, so we can read input from the file
            BufferedReader reader = new BufferedReader(new FileReader(path));
            //Set up a new activity
            Activity current = null;
            //Set up a string variable to hold out current line in the file
            String line;

            DataPoint lastPoint = null;

            //TODO catch any parsing errors with dates, floats ect.

            //While we still have lines
            while ((line = reader.readLine()) != null) {
                //If the line starts with a '#' this is a new activity
                if (line.startsWith("#")) {
                    //If we haven't made an activity yet, don't add 'null.' Bad things will happen
                    if (current != null)
                        //Otherwise, add this one
                        activities.add(current);
                    //And make a new one. The name of the activity is the rest of the line minus the #
                    //TODO clean up all the commas and the '#start'
                    // Make sure that the activity name is of appropriate length
                    // to prevent a IndexOutOfBoundsException
                    if (line.length() >= 2) {
                        String[] data = line.split(",");
                        current = new Activity(data[1]);
                        lastPoint = null;
                    } else {
                        // Use some default name for the activity
                        current = new Activity("Unknown Activity");
                        lastPoint = null;
                    }
                    //Continue. We don't have to do anything else with this input
                    continue;
                }
                //Our data is separated by commas
                String[] data = line.split(",");

                //Clean up our values, remove any white space
                for (int i = 0; i < data.length; ++i)
                    data[i] = data[i].trim();

                //Parse the date. We do this using the date formatter defined above
                //and by putting our date and time in the same string
                Date date = dateFormat.parse(data[0] + data[1]);

                //Parse the heart rate. In the sample file it is the third column
                float heartRate = Float.parseFloat(data[2]);

                //Parse the latitude and longitude
                float lat = Float.parseFloat(data[3]);
                float lon = Float.parseFloat(data[4]);

                //Parse the elevation. It should be the last column
                float elevation = Float.parseFloat(data[data.length - 1]);

                //Make a new point
                DataPoint point = new DataPoint(date, heartRate, new Location(lat, lon, elevation), lastPoint);
                //Add our point to the current activity
                current.getPoints().add(point);
                lastPoint = point;
            }

            //We might have an item we haven't added yet
            if (current != null)
                //Add it to the list
                activities.add(current);

            //Close the read stream so we don't run into problems later
            reader.close();

            //Return all our activities
            return activities;
        }
        catch (Exception e){
        	e.printStackTrace();
            System.err.println("Failed to load .CSV");
            return null;
        }
    }
}
