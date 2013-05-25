package at.wien.technikum.fh.parkandride.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.provider.BaseColumns;
import at.wien.technikum.fh.parkandride.support.providers.ParkAndRideContentProvider;

public class ParkAndRide{
	
	private String id;
	private String disctrict;
	private String address;
	private String garageName;
	private String garageOperator;
	private String homepage;
	private Coordinates coordinates;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisctrict() {
		return disctrict;
	}
	public void setDisctrict(String disctrict) {
		this.disctrict = disctrict;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGarageName() {
		return garageName;
	}
	public void setGarageName(String garageName) {
		this.garageName = garageName;
	}
	public String getGarageOperator() {
		return garageOperator;
	}
	public void setGarageOperator(String garageOperator) {
		this.garageOperator = garageOperator;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public static  final Collection<ParkAndRide> valueOf(JSONObject object){
		Collection<ParkAndRide> parkAndRides = new ArrayList<ParkAndRide>();
		ParkAndRide parkAndRide;
		try {
			//get root element
			JSONArray jsonArray = object.getJSONArray("features");
			
			for(int i=0;i<jsonArray.length();i++){
				parkAndRide = new ParkAndRide();
				JSONObject json_data = jsonArray.getJSONObject(i);
				//set id
				parkAndRide.setId(json_data.getString("id"));
				
				JSONObject properties = json_data.getJSONObject("properties");
				
				parkAndRide.setHomepage(properties.getString("WEBLINK1"));
				parkAndRide.setGarageName(properties.getString("GARAGENNAME"));
				parkAndRide.setAddress(properties.getString("ADRESSE"));
				parkAndRide.setGarageOperator(properties.getString("GARAGENBETREIBER"));
				parkAndRide.setDisctrict(properties.getString("BEZIRK"));
				
				JSONObject coordinates = json_data.getJSONObject("geometry");
				JSONArray coordinatesArray= coordinates.getJSONArray("coordinates");
				
				Coordinates prCoordinates = new Coordinates();
				
				prCoordinates.setLongitude(coordinatesArray.getDouble(0));
				prCoordinates.setLatitude(coordinatesArray.getDouble(1));
				
				//add coordinates to domain object
				parkAndRide.setCoordinates(prCoordinates);
				
				//add domain object to list
				parkAndRides.add(parkAndRide);
			}
			
			return parkAndRides;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static final class ParkAndRideTable implements BaseColumns {
        private ParkAndRideTable() {
        }
 
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + ParkAndRideContentProvider.AUTHORITY + "/parkandrides");
 
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.domain.parkandrides";
 
        public static final String ID = "_id";
 
        public static final String DISTRICT = "district";
 
        public static final String ADDRESS = "address";
        
        public static final String GARAGENAME = "garageName";
        
        public static final String GARAGOPERATOR = "garageOperator";
        
        public static final String HOMEPAGE = "homepage";
        
        public static final String LONGITUDE = "longitude";
        
        public static final String LATITUDE = "latitude";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result
				+ ((disctrict == null) ? 0 : disctrict.hashCode());
		result = prime * result
				+ ((garageName == null) ? 0 : garageName.hashCode());
		result = prime * result
				+ ((garageOperator == null) ? 0 : garageOperator.hashCode());
		result = prime * result
				+ ((homepage == null) ? 0 : homepage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkAndRide other = (ParkAndRide) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (disctrict == null) {
			if (other.disctrict != null)
				return false;
		} else if (!disctrict.equals(other.disctrict))
			return false;
		if (garageName == null) {
			if (other.garageName != null)
				return false;
		} else if (!garageName.equals(other.garageName))
			return false;
		if (garageOperator == null) {
			if (other.garageOperator != null)
				return false;
		} else if (!garageOperator.equals(other.garageOperator))
			return false;
		if (homepage == null) {
			if (other.homepage != null)
				return false;
		} else if (!homepage.equals(other.homepage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
