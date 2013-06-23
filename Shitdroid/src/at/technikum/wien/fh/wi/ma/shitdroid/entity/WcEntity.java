package at.technikum.wien.fh.wi.ma.shitdroid.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.provider.BaseColumns;
import at.technikum.wien.fh.wi.ma.shitdroid.service.ShitroidContentProvider;

/**
 * Entitaet fuer einen WC-Standord mit zusaetzlichen Informationen
 * 
 * @author Robert
 */
public class WcEntity {
	private String standortId;
	private String bezirk;
	private String strasse;
	private String orientierungsNr;
	private String telefon;
	private String oeffnungszeit;
	private String information;
	private String abteilung;
	private String kategorie;
	private double laengengrad;
	private double breitengrad;

	public static final class WcTable implements BaseColumns {
		private WcTable() {
		}

		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ ShitroidContentProvider.AUTHORITY + "/wc");

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.domain.wc";

		public static final String STANDORTID = "standortId";
		public static final String BEZIRK = "bezirk";
		public static final String STRASSE = "strasse";
		public static final String ORIENTIERUNGSNR = "orientierungsNr";
		public static final String TELEFON = "telefon";
		public static final String OEFFNUNGSZEIT = "oeffnungszeit";
		public static final String INFORMATION = "information";
		public static final String ABTEILUNG = "abteilung";
		public static final String KATEGORIE = "kategorie";
		public static final String LAENGENGRAD = "laengengrad";
		public static final String BREITENGRAD = "breitengrad";
	}

	public static final Collection<WcEntity> valueOf(JSONObject object) {
		Collection<WcEntity> wcs = new ArrayList<WcEntity>();
		WcEntity wc;
		try {
			// get root element
			JSONArray jsonArray = object.getJSONArray("features");

			for (int i = 0; i < jsonArray.length(); i++) {
				wc = new WcEntity();
				JSONObject json_data = jsonArray.getJSONObject(i);
				// set id
				wc.setStandortId(json_data.getString("id"));

				JSONObject properties = json_data.getJSONObject("properties");

				wc.setBezirk(properties.getString("BEZIRK"));
				wc.setStrasse(properties.getString("STRASSE"));
				wc.setOrientierungsNr(properties.getString("ONR"));
				wc.setTelefon(properties.getString("TELEFON"));
				wc.setOeffnungszeit(properties.getString("OEFFNUNGSZEIT"));
				wc.setInformation(properties.getString("INFORMATION"));
				wc.setAbteilung(properties.getString("ABTEILUNG"));
				wc.setKategorie(properties.getString("KATEGORIE"));

				JSONObject coordinates = json_data.getJSONObject("geometry");
				JSONArray coordinatesArray = coordinates
						.getJSONArray("coordinates");

				wc.setLaengengrad(coordinatesArray.getDouble(0));
				wc.setBreitengrad(coordinatesArray.getDouble(1));

				wcs.add(wc);
			}

			return wcs;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the standortId
	 */
	public String getStandortId() {
		return standortId;
	}

	/**
	 * @param standortId
	 *            the standortId to set
	 */
	public void setStandortId(String standortId) {
		this.standortId = standortId;
	}

	/**
	 * @return the bezirk
	 */
	public String getBezirk() {
		return bezirk;
	}

	/**
	 * @param bezirk
	 *            the bezirk to set
	 */
	public void setBezirk(String bezirk) {
		this.bezirk = bezirk;
	}

	/**
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * @param strasse
	 *            the strasse to set
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	/**
	 * @return the orientierungsNr
	 */
	public String getOrientierungsNr() {
		return orientierungsNr;
	}

	/**
	 * @param orientierungsNr
	 *            the orientierungsNr to set
	 */
	public void setOrientierungsNr(String orientierungsNr) {
		this.orientierungsNr = orientierungsNr;
	}

	/**
	 * @return the telefon
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * @param telefon
	 *            the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * @return the oeffnungszeit
	 */
	public String getOeffnungszeit() {
		return oeffnungszeit;
	}

	/**
	 * @param oeffnungszeit
	 *            the oeffnungszeit to set
	 */
	public void setOeffnungszeit(String oeffnungszeit) {
		this.oeffnungszeit = oeffnungszeit;
	}

	/**
	 * @return the information
	 */
	public String getInformation() {
		return information;
	}

	/**
	 * @param information
	 *            the information to set
	 */
	public void setInformation(String information) {
		this.information = information;
	}

	/**
	 * @return the abteilung
	 */
	public String getAbteilung() {
		return abteilung;
	}

	/**
	 * @param abteilung
	 *            the abteilung to set
	 */
	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}

	/**
	 * @return the kategorie
	 */
	public String getKategorie() {
		return kategorie;
	}

	/**
	 * @param kategorie
	 *            the kategorie to set
	 */
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * @return the laengengrad
	 */
	public double getLaengengrad() {
		return laengengrad;
	}

	/**
	 * @param laengengrad
	 *            the laengengrad to set
	 */
	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	/**
	 * @return the breitengrad
	 */
	public double getBreitengrad() {
		return breitengrad;
	}

	/**
	 * @param breitengrad
	 *            the breitengrad to set
	 */
	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abteilung == null) ? 0 : abteilung.hashCode());
		result = prime * result + ((bezirk == null) ? 0 : bezirk.hashCode());
		long temp;
		temp = Double.doubleToLongBits(breitengrad);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((information == null) ? 0 : information.hashCode());
		result = prime * result
				+ ((kategorie == null) ? 0 : kategorie.hashCode());
		temp = Double.doubleToLongBits(laengengrad);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((oeffnungszeit == null) ? 0 : oeffnungszeit.hashCode());
		result = prime * result
				+ ((orientierungsNr == null) ? 0 : orientierungsNr.hashCode());
		result = prime * result
				+ ((standortId == null) ? 0 : standortId.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WcEntity other = (WcEntity) obj;
		if (abteilung == null) {
			if (other.abteilung != null)
				return false;
		} else if (!abteilung.equals(other.abteilung))
			return false;
		if (bezirk == null) {
			if (other.bezirk != null)
				return false;
		} else if (!bezirk.equals(other.bezirk))
			return false;
		if (Double.doubleToLongBits(breitengrad) != Double
				.doubleToLongBits(other.breitengrad))
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		} else if (!kategorie.equals(other.kategorie))
			return false;
		if (Double.doubleToLongBits(laengengrad) != Double
				.doubleToLongBits(other.laengengrad))
			return false;
		if (oeffnungszeit == null) {
			if (other.oeffnungszeit != null)
				return false;
		} else if (!oeffnungszeit.equals(other.oeffnungszeit))
			return false;
		if (orientierungsNr == null) {
			if (other.orientierungsNr != null)
				return false;
		} else if (!orientierungsNr.equals(other.orientierungsNr))
			return false;
		if (standortId == null) {
			if (other.standortId != null)
				return false;
		} else if (!standortId.equals(other.standortId))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		} else if (!strasse.equals(other.strasse))
			return false;
		if (telefon == null) {
			if (other.telefon != null)
				return false;
		} else if (!telefon.equals(other.telefon))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WcEntity [standortId=" + standortId + ", bezirk=" + bezirk
				+ ", strasse=" + strasse + ", orientierungsNr="
				+ orientierungsNr + ", telefon=" + telefon + ", oeffnungszeit="
				+ oeffnungszeit + ", information=" + information
				+ ", abteilung=" + abteilung + ", kategorie=" + kategorie
				+ ", laengengrad=" + laengengrad + ", breitengrad="
				+ breitengrad + "]";
	}
}
