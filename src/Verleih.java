/**
 * Die Klasse Verleih repräsentiert einen RechnungsPosten.
 * 
 * @author Florian Bussmann
 */
public class Verleih extends RechnungsPosten {
	/**
	 * Erzeugt eine Instanz der Klasse Verleih.
	 * 
	 * @param posten LagerPosten der verliehen wurde
	 * @param menge Menge die verliehen wurde
	 * @param betrag daraus resultierender Betrag
	 */
	public Verleih(LagerPosten posten, int menge, int betrag) {
		this.menge = menge;
		this.artikel = posten;
		this.betrag = betrag;
	}

    /**
     * Gibt eine lesbare Formatierung der Instanz der Klasse Verleih zurück.
	 * 
	 * @return String aus Menge, Produktname und Gebühr sowie Typ des RechnungsPostens
     */
	@Override
	public String toString() {
		return "Verleih von " + this.menge + "x" + this.artikel + " zu einer Gebühr von " + Services.geldString(this.betrag) + ".";
	}
}
