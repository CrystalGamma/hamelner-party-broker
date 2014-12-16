/**
 * Die Klasse AdressFehler repräsentiert alle Fehler
 * im Zusammenhang mit den Adressdaten von Kunden.
 */
public class AdressFehler extends Error {
	/**
	 * Erzeugt eine Instanz der Klasse AdressFehler mit einer Fehlermeldung
	 * 
	 * @param str Gewünschte Fehlermeldung
	 */
    public AdressFehler(String str) {
        super(str);
    }
}
