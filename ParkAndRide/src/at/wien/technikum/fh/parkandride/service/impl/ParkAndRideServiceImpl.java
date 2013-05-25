package at.wien.technikum.fh.parkandride.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import at.wien.technikum.fh.parkandride.domain.Coordinates;
import at.wien.technikum.fh.parkandride.domain.ParkAndRide;
import at.wien.technikum.fh.parkandride.domain.ParkAndRide.ParkAndRideTable;
import at.wien.technikum.fh.parkandride.service.api.ParkAndRideService;

public class ParkAndRideServiceImpl implements ParkAndRideService {

	private Context context;

	public ParkAndRideServiceImpl(Context context) {
		super();
		this.context = context;
	}

	@Override
	public Collection<ParkAndRide> getParkAndRides() {
		Collection<ParkAndRide> parkAndRides = new ArrayList<ParkAndRide>();

		Cursor c = context.getContentResolver().query(ParkAndRideTable.CONTENT_URI,
				null, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					parkAndRides.add(mapParkAndRide(c));
				} while (c.moveToNext());
			}
		}
		c.close();

		return parkAndRides;
	}

	@Override
	public void saveOrUpdateParkAndRides(Collection<ParkAndRide> parkAndRides) {

		// saving park and rides in DB using the content provider
		for (ParkAndRide parkAndRide : parkAndRides) {
			boolean update = false;
			// check if entity exists
			Cursor c = context.getContentResolver().query(
					ParkAndRideTable.CONTENT_URI, new String[] { ParkAndRideTable.ID },
					ParkAndRideTable.ID + " = ?",
					new String[] { parkAndRide.getId() }, null);

			if (c.getCount() > 0) {
				update = true;
			}

			ContentValues contentValues = new ContentValues();
			contentValues.put(ParkAndRideTable.ADDRESS, parkAndRide.getAddress());
			contentValues
					.put(ParkAndRideTable.DISTRICT, parkAndRide.getDisctrict());
			contentValues.put(ParkAndRideTable.GARAGENAME,
					parkAndRide.getGarageName());
			contentValues.put(ParkAndRideTable.GARAGOPERATOR,
					parkAndRide.getGarageOperator());
			contentValues.put(ParkAndRideTable.HOMEPAGE, parkAndRide.getHomepage());
			if (!update) {
				contentValues.put(ParkAndRideTable.ID, parkAndRide.getId());
			}

			contentValues.put(ParkAndRideTable.LATITUDE, parkAndRide
					.getCoordinates().getLatitude());
			contentValues.put(ParkAndRideTable.LONGITUDE, parkAndRide
					.getCoordinates().getLongitude());

			if (update) {
				// update row
				context.getContentResolver().update(ParkAndRideTable.CONTENT_URI,
						contentValues, ParkAndRideTable.ID + " = ?",
						new String[] { parkAndRide.getId() });
			} else {
				// insert in DB
				context.getContentResolver().insert(ParkAndRideTable.CONTENT_URI,
						contentValues);
			}

		}

	}

	@Override
	public ParkAndRide getParkAndRideById(String id) {
		
		Cursor c = context.getContentResolver().query(
				ParkAndRideTable.CONTENT_URI, null,
				ParkAndRideTable.ID + " = ?",
				new String[] { id }, null);
		
		
		if(c.moveToFirst()){
			return mapParkAndRide(c);
		}
		
		return null;
	}
	
	//maps one parkAndRide object from cursor
	private ParkAndRide mapParkAndRide(Cursor c){
		ParkAndRide parkAndRide = new ParkAndRide();
		parkAndRide.setAddress(c.getString(c
				.getColumnIndex(ParkAndRideTable.ADDRESS)));
		parkAndRide.setDisctrict(c.getString(c
				.getColumnIndex(ParkAndRideTable.DISTRICT)));
		parkAndRide.setGarageName(c.getString(c
				.getColumnIndex(ParkAndRideTable.GARAGENAME)));
		parkAndRide.setGarageOperator(c.getString(c
				.getColumnIndex(ParkAndRideTable.GARAGOPERATOR)));
		parkAndRide.setHomepage(c.getString(c
				.getColumnIndex(ParkAndRideTable.HOMEPAGE)));
		parkAndRide.setId(c.getString(c
				.getColumnIndex(ParkAndRideTable.ID)));

		Coordinates coordinates = new Coordinates();
		coordinates.setLatitude(c.getDouble(c
				.getColumnIndex(ParkAndRideTable.LATITUDE)));
		coordinates.setLongitude(c.getDouble(c
				.getColumnIndex(ParkAndRideTable.LONGITUDE)));

		parkAndRide.setCoordinates(coordinates);
		
		return parkAndRide;
	}

}
