public class Ausleihe {
	private long startzeit, endzeit;
	private int menge;
	private LagerPosten lagerposten;

	private Ausleihe() {
		
	
	}

	public void buchen() {

		lagerposten.bestandAendern(menge);
	}

	public LagerPosten getPosten() {
		return lagerposten;
	}

	public int getMenge() {
		return menge;
	}

	public long getStartZeit() {
		return startzeit;
	}
	public void setEndzeit(long endzeit)
	{
		this.endzeit=endzeit;
	}

	public int verlust(int pMenge) {
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

	public int rueckgabe(int pMenge) {
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
		return pMenge;//gibt den Parameter zurück der Angibt welche Menge von der Rückgabe noch übrig ist, bei ungleich null kann ausleihe in rechnungspunkt umgewandelt werden
	}
}
