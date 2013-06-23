package at.technikum.wien.fh.wi.ma.shitdroid.GPS;
/**
 * GPS Kooridinaten
 * @author Christian
 *
 */
public class GPSCoordinate {
    private double longitude;
    private double latitude;
    
    public GPSCoordinate()
    {
    	this.longitude = -1;
    	this.latitude = -1;
    }
    
    /**
     * get the Longitude
     * @return
     */
    public double getLongitude()
    {
    	return this.longitude;
    }
    
    /**
     * set the longitude
     * @param longitude
     */
    public void setLogitude( double longitude)
    {
    	this.longitude = longitude;
    }
    
    /**
     * get the latitude
     * @return latitude
     */
    public double getLatitude()
    {
    	return this.latitude;
    }
    
    /**
     * set the latitude
     * @param latitude
     */
    public void setLatitude( double latitude)
    {
    	this.latitude = latitude;
    }
    
    /**
     * are GPS Coordinates valid?
     * @return
     */
    public boolean isValid()
    {
    	if( this.latitude < 0) return false;
    	if( this.longitude < 0) return false;
    	return true;
    }
}
