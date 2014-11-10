public class Ausleihe {
	private long startzeit, endzeit;
	private int menge;
	private LagerPosten lagerposten;

	private Ausleihe() {
		
	
	}

	private void buchen() {

		lagerposten.bestandAendern(menge);
	}

	LagerPosten getPosten() {
		return lagerposten;
	}

	private int getMenge() {
		return menge;
	}

	long getStartZeit() {
		return startzeit;
	}
	private void setEndzeit(long endzeit)
	{
		this.endzeit=endzeit;
	}

	private int verlust(int pMenge) {
		if(pMenge>menge)
		{
			pMenge -= menge;
			menge=0; 
		}
		else
		{
		menge=menge-pMenge;
		pMenge=0;
		}
		return pMenge;
	}

	private int rueckgabe(int pMenge) {
		if(pMenge>menge)
		{
			pMenge -= menge;
			menge=0; 
		}
		else
		{
		menge=menge-pMenge;
		pMenge=0;
		}
		return pMenge;//gibt den Parameter zur�ck der Angibt welche Menge von der R�ckgabe noch �brig ist, bei ungleich null kann ausleihe in rechnungspunkt umgewandelt werden
	}
}
