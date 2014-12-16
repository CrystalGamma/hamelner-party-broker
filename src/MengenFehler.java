/**
 * Die Klasse MengenFehler repräsentiert alle Fehler
 * im Zusammenhang mit unerwarteten Mengenangaben.
 */
public class MengenFehler extends RuntimeException {
    enum Art {
        NegativKaufen,
        NegativLeihen,
        ZuvielAusgeben,
        ZuvielRueckgeben,
        ZuvielVerloren
    }

    Art art;
    int menge;

    /**
	 * Erzeugt eine Instanz der Klasse MengenFehler mit einer Fehlermeldung.
	 * 
	 * @param art Art des Fehlers
	 * @param menge Angegebenene Menge
	 */
    public MengenFehler(Art art, int menge) {
        this.art = art;
        this.menge = menge;
    }

    /**
     * Gibt eine lesbare Formatierung der Instanz der Klasse MengenFehler zurück.
	 * 
	 * @return String aus angegebene Menge sowie Fehlerbeschreibung
     */
    public String toString() {
        switch (art) {
            case NegativKaufen:
                return "Negative Menge " + menge + " kann nicht gekauft werden.";
            case NegativLeihen:
                return "Negative Menge " + menge + " kann nicht verliehen werden.";
            case ZuvielAusgeben:
                return "Nicht genug Bestand verfügbar um " + menge + " auszugeben.";
            case ZuvielRueckgeben:
                return "Rückgabemenge übersteigt Ausleihmenge um " + menge + ".";
            case ZuvielVerloren:
                return "Verlustmenge übersteigt Ausleihmenge um " + menge + ".";
        }
        throw new Error("sollte nie erreicht werden");
    }

    /**
     * Ruft die toString-Methode der Klasse auf
     * 
     * @return Lesbare Fehlermeldung
     */
    public String getMessage() {
        return toString();
    }
}
