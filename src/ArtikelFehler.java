/**
 * Die Klasse ArtikelFehler repräsentiert alle Fehler,
 * die im Zusammenhang mit den Artikeln stehen.
 * 
 * @author Jona Stubbe
 */
public class ArtikelFehler extends RuntimeException {
    enum Art {
        NichtVerkaeuflich,
        NichtVerleihbar
    }

    Artikel artikel;
    Art art;

    /**
     * Erzeugt eine Instanz der Klasse ArtikelFehler für einen Artikel mit einer Fehlerart.
     * 
     * @param artikel Artikel
     * @param art Art des Fehlers (nicht verkäuflich oder nicht verleihbar)
     */
    public ArtikelFehler(Artikel artikel, Art art) {
        this.art = art;
        this.artikel = artikel;
    }

    /**
     *  Gibt eine lesbare Formatierung der Instanz der Klasse ArtikelFehler zurück.
	 * 
	 * @return String aus Name des Artikels sowie Fehlerbeschreibung
     */
    public String toString() {
        switch (art) {
            case NichtVerkaeuflich:
                return "'" + artikel.toString() + "' ist nicht verkäuflich.";
            case NichtVerleihbar:
                return "'" + artikel.toString() + "' ist nicht verleihbar.";
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
