package at.technikum.wien.fh.wi.ma.shitdroid.entity;

/**
 * Entitaet fuer einen WC-Standord mit zusaetzlichen Informationen
 * 
 * @author Robert
 */
public class WCEntity {
	private String standordId;
	private String bezirk;
	private String strasse;
	private String orientierungsNr;
	private String telefon;
	private String oeffnungszeit;
	private String infomration;
	private String abteilung;
	private String kategorie;
	private String laengengrad;
	private String breitengrad;

	/**
	 * @return the standordId
	 */
	public String getStandordId() {
		return standordId;
	}

	/**
	 * @param standordId
	 *            the standordId to set
	 */
	public void setStandordId(String standordId) {
		this.standordId = standordId;
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
	 * @return the infomration
	 */
	public String getInfomration() {
		return infomration;
	}

	/**
	 * @param infomration
	 *            the infomration to set
	 */
	public void setInfomration(String infomration) {
		this.infomration = infomration;
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
	public String getLaengengrad() {
		return laengengrad;
	}

	/**
	 * @param laengengrad
	 *            the laengengrad to set
	 */
	public void setLaengengrad(String laengengrad) {
		this.laengengrad = laengengrad;
	}

	/**
	 * @return the breitengrad
	 */
	public String getBreitengrad() {
		return breitengrad;
	}

	/**
	 * @param breitengrad
	 *            the breitengrad to set
	 */
	public void setBreitengrad(String breitengrad) {
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
		result = prime * result
				+ ((breitengrad == null) ? 0 : breitengrad.hashCode());
		result = prime * result
				+ ((infomration == null) ? 0 : infomration.hashCode());
		result = prime * result
				+ ((kategorie == null) ? 0 : kategorie.hashCode());
		result = prime * result
				+ ((laengengrad == null) ? 0 : laengengrad.hashCode());
		result = prime * result
				+ ((oeffnungszeit == null) ? 0 : oeffnungszeit.hashCode());
		result = prime * result
				+ ((orientierungsNr == null) ? 0 : orientierungsNr.hashCode());
		result = prime * result
				+ ((standordId == null) ? 0 : standordId.hashCode());
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
		WCEntity other = (WCEntity) obj;
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
		if (breitengrad == null) {
			if (other.breitengrad != null)
				return false;
		} else if (!breitengrad.equals(other.breitengrad))
			return false;
		if (infomration == null) {
			if (other.infomration != null)
				return false;
		} else if (!infomration.equals(other.infomration))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		} else if (!kategorie.equals(other.kategorie))
			return false;
		if (laengengrad == null) {
			if (other.laengengrad != null)
				return false;
		} else if (!laengengrad.equals(other.laengengrad))
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
		if (standordId == null) {
			if (other.standordId != null)
				return false;
		} else if (!standordId.equals(other.standordId))
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
		return "WCEntity [standordId=" + standordId + ", bezirk=" + bezirk
				+ ", strasse=" + strasse + ", orientierungsNr="
				+ orientierungsNr + ", telefon=" + telefon + ", oeffnungszeit="
				+ oeffnungszeit + ", infomration=" + infomration
				+ ", abteilung=" + abteilung + ", kategorie=" + kategorie
				+ ", laengengrad=" + laengengrad + ", breitengrad="
				+ breitengrad + "]";
	}

}
