/**
 * Die Klasse DienstLeistung basiert auf einem Artikel, hat
 * jedoch einen unendlichen Bestand und ist damit immer verfügbar.
 * 
 * @author Florian Bussmann
 */
public class DienstLeistung extends Artikel {
	/**
	 * Erzeugt eine Instanz der Klasse DienstLeistung
	 * 
	 * @param name Produktname
	 * @param preis Preis pro Einheit
	 */
	public DienstLeistung(String name, int preis){
		this.name = name;
		this.handlingPauschale = preis;
	}

	/**
	 * Ermittelt den Kaufpreis der DienstLeistung unter
	 * Berücksichtigung der angegebenen Menge.
	 * 
	 * @param menge Menge zu der der Kaufpreis berechnet wird
	 */
	public int kaufPreis(int menge) {
		return menge * handlingPauschale;
	}
	
	/**
	 * Ermittelt, ob die DienstLeistung verkäuflich ist.
	 * 
	 * @return true - DienstLeistungen sind jederzeit verkäuflich.
	 */
	public boolean istVerkaeuflich() {
		return true;
	}

	/**
	 * Ermittelt, ob die DienstLeistung verleihbar ist.
	 * 
	 * @return false - DienstLeistungen sind nie verleihbar.
	 */
	public boolean istVerleihbar() {
		return false;
	}
	
	/**
	 * Lesbare Formatierung für Bestandsauflistungen.
	 * 
	 * @return String aus Produktname und Handling-Pauschale
	 */
	public String bestandString() {
		return this.name + ": " + Services.geldString(this.handlingPauschale);
	}

	/**
	 * Ermittelt, ob die DienstLeistung verfügbar ist.
	 * 
	 * @return true - DienstLeistungen sind jederzeit verfügbar.
	 */
	@Override
	public boolean istVerfuegbar() {
		return true;
	}

	/**
	 * Ermittelt den aktuellen Bestand der DienstLeistung.
	 * 
	 * @return 0 - DienstLeistungen sind auf Abruf verfügbar und haben daher keinen Bestand.
	 */
	@Override
	public int getBestand() {
		return 0; // unendlich
	}
}