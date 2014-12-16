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

	/**
	 * Klont die Instanz der Klasse Ausleihe
	 * @return Kopie der Instanz
	 */
	public Ausleihe clone() {
		return new Ausleihe(startZeit, endZeit, lagerPosten, menge);
	}

	/**
	 * Finalisiert eine Ausleihe und ändert dazu den verfügbaren Bestand des Artikels.
	 */
	public void buchen() {
		lagerPosten.bestandAendern(-menge);
	}

	/**
	 * Ruft den ausgeliehenen LagerPosten ab.
	 * 
	 * @return LagerPosten der ausgeliehen wurde.
	 */
	public LagerPosten getPosten() {
		return lagerPosten;
	}

	/**
	 * Ruft die ausgeliehene Menge ab.
	 * 
	 * @return Menge die ausgeliehen wurde.
	 */
	public int getMenge() {
		return menge;
	}

	/**
	 * Prüft ob die Ausleihe wieder leer ist.
	 * 
	 * @return Wahrheitswert ob Ausleihe leer ist.
	 */
	public boolean istLeer() {
		return menge == 0;
	}

	/**
	 * Ruft die Startzeit der Ausleihe ab.
	 * 
	 * @return Startzeit der Ausleihe
	 */
	public int getStartZeit() {
		return startZeit;
	}

	/**
	 * Verändert die prognostizierte Endzeit der Ausleihe.
	 * 
	 * @param endZeit Neue prognostizierte Endzeit der Ausleihe
	 */
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

	/**
	 * Ruft die prognostizierte Endzeit der Ausleihe ab.
	 * 
	 * @return Prognostizierte Endzeit der Ausleihe
	 */
	public int getEndZeit() {
		return endZeit;
	}

	/**
	 * Gibt eine lesbare Formatierung der Instanz der Klasse Ausleihe zurück.
	 * 
	 * @return String aus Menge, LagerPosten sowie Start- und Endzeit der Ausleihe
	 */
	public String toString() {
		return "Ausleihe von " + menge + "x " + lagerPosten + " von "
				+ startZeit + "h bis " + endZeit + "h";
	}
}
