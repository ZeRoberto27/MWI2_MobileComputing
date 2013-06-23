package at.technikum.wien.fh.wi.ma.shitdroid.service;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity;
import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity.WcTable;

/**
 * Implementiert alle Services der WCs
 * 
 * @author Robert
 * 
 */
public class WcService implements IWcService {

	private Context context;

	public WcService(Context context) {
		super();
		this.context = context;
	}

	/**
	 * Mapped eine Entitaet
	 * 
	 * @param c
	 * @return
	 */
	private WcEntity mapWC(Cursor c) {
		WcEntity wc = new WcEntity();
		wc.setStandortId(c.getString(c.getColumnIndex(WcTable.STANDORTID)));
		wc.setBezirk(c.getString(c.getColumnIndex(WcTable.BEZIRK)));
		wc.setStrasse(c.getString(c.getColumnIndex(WcTable.STRASSE)));
		wc.setOrientierungsNr(c.getString(c
				.getColumnIndex(WcTable.ORIENTIERUNGSNR)));
		wc.setTelefon(c.getString(c.getColumnIndex(WcTable.TELEFON)));
		wc.setOeffnungszeit(c.getString(c.getColumnIndex(WcTable.OEFFNUNGSZEIT)));
		wc.setInformation(c.getString(c.getColumnIndex(WcTable.INFORMATION)));
		wc.setAbteilung(c.getString(c.getColumnIndex(WcTable.ABTEILUNG)));
		wc.setKategorie(c.getString(c.getColumnIndex(WcTable.KATEGORIE)));
		wc.setLaengengrad(c.getDouble(c.getColumnIndex(WcTable.LAENGENGRAD)));
		wc.setBreitengrad(c.getDouble(c.getColumnIndex(WcTable.BREITENGRAD)));

		return wc;
	}

	/**
	 * Lesen aller WCs
	 */
	@Override
	public Collection<WcEntity> getWCs() {
		Collection<WcEntity> wcs = new ArrayList<WcEntity>();

		Cursor c = context.getContentResolver().query(WcTable.CONTENT_URI,
				null, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					wcs.add(mapWC(c));
				} while (c.moveToNext());
			}
			c.close();
		}

		return wcs;
	}

	/**
	 * Liefert ein WC zur ID
	 */
	@Override
	public WcEntity getWcById(String id) {
		Cursor c = context.getContentResolver().query(WcTable.CONTENT_URI,
				null, WcTable.STANDORTID + " = ?", new String[] { id }, null);

		if (c.moveToFirst()) {
			return mapWC(c);
		}

		return null;
	}

	/**
	 * Speichert die WCs
	 */
	@Override
	public void saveWcs(Collection<WcEntity> wcs) {
		for (WcEntity wc : wcs) {
			boolean update = false;
			// check if entity exists
			Cursor c = context.getContentResolver().query(WcTable.CONTENT_URI,
					new String[] { WcTable.STANDORTID },
					WcTable.STANDORTID + " = ?",
					new String[] { wc.getStandortId() }, null);

			if (c != null && c.getCount() > 0) {
				update = true;
			}

			ContentValues cv = new ContentValues();
			cv.put(WcTable.STANDORTID, wc.getStandortId());
			cv.put(WcTable.BEZIRK, wc.getBezirk());
			cv.put(WcTable.STRASSE, wc.getStrasse());
			cv.put(WcTable.ORIENTIERUNGSNR, wc.getOrientierungsNr());
			cv.put(WcTable.TELEFON, wc.getTelefon());
			cv.put(WcTable.OEFFNUNGSZEIT, wc.getOeffnungszeit());
			cv.put(WcTable.INFORMATION, wc.getInformation());
			cv.put(WcTable.ABTEILUNG, wc.getAbteilung());
			cv.put(WcTable.KATEGORIE, wc.getKategorie());
			cv.put(WcTable.LAENGENGRAD, wc.getLaengengrad());
			cv.put(WcTable.BREITENGRAD, wc.getBreitengrad());

			if (update) {
				// update row
				context.getContentResolver().update(WcTable.CONTENT_URI, cv,
						WcTable.STANDORTID + " = ?",
						new String[] { wc.getStandortId() });
			} else {
				// insert in DB
				context.getContentResolver().insert(WcTable.CONTENT_URI, cv);
			}

		}
	}
}
