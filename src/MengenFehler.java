public class MengenFehler extends RuntimeException {
    enum Art {
        NegativKaufen,
        Zuviel
    }

    Art art;
    int menge;

    public MengenFehler(Art art, int menge) {
        this.art = art;
        this.menge = menge;
    }

    public String toString() {
        switch (art) {
            case NegativKaufen:
                return "Negative Menge " + menge + " kann nicht gekauft werden";
            case Zuviel:
                return "Nicht genug Bestand verfügbar um " + menge + "auszugeben";
        }
        return "Sollte niemals erreicht werden";
    }
}
