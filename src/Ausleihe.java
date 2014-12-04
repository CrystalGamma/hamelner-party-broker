/**
 * @author Jona Stubbe
 * Immutable Objekte die eine Ausleihe beschreiben
 */
public class Ausleihe implements Cloneable {
	private int startZeit, endZeit;
	private int menge;
	private LagerPosten lagerPosten;

	/**
	 * Jona Stubbe
	 * @param startZeit: Ausleihezeitpunkt
	 * @param endZeit: Rückgabefrist
	 * @param posten: LagerPosten der ausgelihen wird
	 * @param menge: auszuleihende Menge
	 */
	public Ausleihe(int startZeit, int endZeit, LagerPosten posten, int menge) {
		this.startZeit = startZeit;
		this.endZeit = endZeit;
		lagerPosten = posten;
		this.menge = menge;
		if (!posten.istVerleihbar())
			throw new ArtikelFehler(posten, ArtikelFehler.Art.NichtVerleihbar);
		if (menge < 0)
			throw new MengenFehler(MengenFehler.Art.NegativLeihen, menge);
	}

	public Ausleihe clone() {
		return new Ausleihe(startZeit, endZeit, lagerPosten, menge);
	}

	public void buchen() {
		lagerPosten.bestandAendern(-menge);
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

	public int getStartZeit() {
		return startZeit;
	}

	public void setEndZeit(int endZeit) {
		this.endZeit = endZeit;
	}

	/**
	 * Jona Stubbe
	 * @param menge: Menge um die die Ausleihe zu reduzieren ist
	 * @return eine um bis zu menge reduzierte Version von sich selbst
	 */
	public Ausleihe reduzieren(int menge) {
		Ausleihe cpy = clone();
		if (menge > this.menge)
			cpy.menge = 0;
		else
			cpy.menge = this.menge - menge;
		return cpy;
	}

	public int getEndZeit() {
		return endZeit;
	}

	public String toString() {
		return "Ausleihe von " + menge + "x " + lagerPosten + " von "
				+ startZeit + "h bis " + endZeit + "h";
	}
}
