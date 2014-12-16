/**
 * Die Klasse Verkauf repräsentiert einen RechnungsPosten.
 * 
 * @author Florian Bussmann
 */
public class Verkauf extends RechnungsPosten {
	/**
	 * Erzeugt eine Instanz der Klasse Verkauf.
	 * 
	 * @param menge Menge die verkauft wurde
	 * @param artikel Artikel der verkauft wurde
	 */
	public Verkauf(int menge, Artikel artikel) {
		this.menge = menge;
		this.artikel = artikel;
		this.betrag = artikel.kaufPreis(menge);
	}

    /**
     * Gibt eine lesbare Formatierung der Instanz der Klasse Verkauf zurück.
	 * 
	 * @return String aus Menge, Produktname und Preis sowie Typ des RechnungsPostens
     */
	@Override
	public String toString() {
		return "Verkauf von " + this.menge + "x " + this.artikel
				+ " zu einem Preis von " + Services.geldString(this.betrag) + ".";
	}
}
