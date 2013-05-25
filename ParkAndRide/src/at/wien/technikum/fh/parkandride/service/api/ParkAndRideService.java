package at.wien.technikum.fh.parkandride.service.api;

import java.util.Collection;

import at.wien.technikum.fh.parkandride.domain.ParkAndRide;

public interface ParkAndRideService {
	
	Collection<ParkAndRide> getParkAndRides();
	
	void saveOrUpdateParkAndRides(Collection<ParkAndRide> parkAndRides);
	
	ParkAndRide getParkAndRideById(String id);
	
}
