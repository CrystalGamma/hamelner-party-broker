public class Ausleihe {
	private long startZeit, endZeit;
	private int menge;
	private LagerPosten lagerPosten;

	public Ausleihe(int startZeit, int endZeit, LagerPosten posten, int menge) {
		this.startZeit = startZeit;
		this.endZeit = endZeit;
		lagerPosten = posten;
		this.menge = menge;
	}

	public void buchen() {
		lagerPosten.bestandAendern(menge);
	}

	public LagerPosten getPosten() {
		return lagerPosten;
	}

	public int getMenge() {
		return menge;
	}

	public boolean istLeer() {
		return menge == 0;
	}

	public long getStartZeit() {
		return startZeit;
	}

	public void setEndZeit(long endZeit) {
		this.endZeit = endZeit;
	}

	public int verlust(int menge) {
		if (menge > this.menge) {
			menge -= this.menge;
			this.menge = 0;
		} else {
			this.menge = this.menge - menge;
			menge = 0;
		}
		return menge;
	}

	public int rueckgabe(int menge) {
		if (menge > this.menge) {
			menge -= this.menge;
			this.menge = 0;
			//rechnungspunkt erzeugen
		} else {
			this.menge = this.menge - menge;
			menge = 0;
			//rechnungspunkt erzeugen
		}
		return menge;//gibt den Parameter zurück der Angibt welche Menge von der Rückgabe noch übrig ist, bei ungleich null kann ausleihe in rechnungspunkt umgewandelt werden
	}

	long getEndZeit() {
		return endZeit;
	}
}
