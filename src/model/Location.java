package model;

import database.DatabaseEntity;

/**
 * Class for holding a latitude and longitude coordinate.
 * Note: I'm not sure if we need this or if Java has a built in
 * class for handling it.
 *
 * Also, perhaps we should add methods for handling point related
 * operations, such as distance between and conversion to coordinates
 * on a map
 */
public class Location implements java.io.Serializable, DatabaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4344739848693878246L;
	private static final float EARTHS_EQUITORIAL_RADIUS = 63781370f;
	private static final float EARTHS_POLAR_RADIUS = 63567523f;

    private float latitude;
    private float longitude;

    private float elevation;

    /**
     * A constructor
     * @param latitude The latitude
     * @param longitude The longitude
     */
    public Location(float latitude, float longitude, float elevation){
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    /**
     * Gets the latitude of this location
     * @return The latitude
     */
    public float getLatitude(){
        return latitude;
    }

    /**
     * Gets the longitude of this location
     * @return The longitude
     */
    public float getLongitude(){
        return longitude;
    }

    /**
     * Gets the elevation of this location
     * @return The elevation
     */
    public float getElevation() {return elevation;}

    /**
     * Calculates the earth's radius as a specific latitude (given by a location)
     * @param loc Location to calculate radius to center of earth for
     * @return Distance in meters
     */
    public static double earthRadiusAtLocation(Location loc)
    {
		double lat = loc.getLatitude();	// get latitude
		
		// run formule to get radius
		double c = Math.pow(Math.pow(EARTHS_EQUITORIAL_RADIUS, 2) * Math.cos(lat), 2)
		+ Math.pow(Math.pow(EARTHS_POLAR_RADIUS, 2) * Math.sin(lat), 2);
		double e = Math.pow(EARTHS_EQUITORIAL_RADIUS * Math.cos(lat), 2)
		+ Math.pow(EARTHS_POLAR_RADIUS * Math.sin(lat), 2);

		// sqrt, account for error in conversion and add the elevation
		return Math.sqrt(c/e) / 10 + loc.getElevation();
    }
    
    /**
     * Gets the distance between two points using the haversine formula
     * You can see the wikipedia article here (if you're brave)
     * http://en.wikipedia.org/wiki/Haversine_formula
     * @param a The first point
     * @param b The second point
     * @return The distance between them
     */
    public static float haverSineDistance(Location a, Location b){
    	// get the radian angles for each location
    	double lat1 = Math.toRadians(a.getLatitude());
    	double long1 = Math.toRadians(a.getLongitude());
    	double lat2 = Math.toRadians(b.getLatitude());
    	double long2 = Math.toRadians(b.getLongitude());
    	
    	// Calculate the average earth radius between locations
    	double earthRadius = Location.earthRadiusAtLocation(a) + Location.earthRadiusAtLocation(b);
    	earthRadius /= 2;
    	
    	// Calculate haversine distance
    	double distance = Math.pow(Math.sin((lat2 - lat1)/2.0), 2);
    	distance += Math.pow(Math.sin((long2 - long1)/2.0), 2) * Math.cos(lat1) * Math.cos(lat2);
    	distance = Math.asin(Math.sqrt(distance));
    	distance *= 2.0 * earthRadius;
    	
    	return (float)distance;
    }
}