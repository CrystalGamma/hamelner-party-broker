public class Ausleihe {
	private long zeit;
	private int menge;
	private LagerPosten lagerposten;

	private Ausleihe() {

	}

	private void buchen() {

	}

	private LagerPosten getPosten() {
		return lagerposten;
	}

	private int getMenge() {
		return menge;
	}

	private long getZeit() {
		return zeit;
	}

	private int verlust(int pMenge) {
		return menge - pMenge;
	}

	private int rueckgabe(int pMenge) {
		return menge - pMenge;
	}
}
