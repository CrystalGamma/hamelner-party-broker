/**
 * Die Klasse Verlust repräsentiert einen RechnungsPosten.
 * 
 * @author Florian Bussmann
 */
public class Verlust extends RechnungsPosten {
	/**
	 * Erzeugt eine Instanz der Klasse Verlust.
	 * 
	 * @param posten LagerPosten der verloren wurde
	 * @param menge Menge die verloren wurde
	 * @param betrag daraus resultierender Betrag
	 * @param zeit Zeitpunkt des Verkaufs
	 */
	public Verlust(LagerPosten posten, int menge, int betrag, int zeit) {
		this.menge = menge;
		this.artikel = posten;
		this.betrag = betrag;
		this.zeit = zeit;
	}

    /**
     * Gibt eine lesbare Formatierung der Instanz der Klasse Verlust zurück.
	 * 
	 * @return String aus Menge, Produktname und Gebühr sowie Typ des RechnungsPostens
     */
	@Override
	public String toString() {
		return "Verlust von " + this.menge + "x" + this.artikel
				+ " zu einer Gebühr von " + Services.geldString(this.betrag)
				+ " um " + zeit + "h";
	}
}
