/**
 * Die Klasse Services ist eine Auslagerung von Service-Methoden,
 * die in den übrigen Klassen ihre Anwendung finden.
 */
public class Services {
	/**
	 * Wandelt einen Betrag von Cent in Euro um.
	 * 
	 * @param cents Preis in Cent
	 * @return Preis in Euro
	 */
    public static String geldString(int cents) {
        return  String.format("%.2f€", (float)cents/100);
    }
}
