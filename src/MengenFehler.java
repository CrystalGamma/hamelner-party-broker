public class MengenFehler extends RuntimeException {
    enum Art {
        NegativKaufen,
        ZuvielAusgeben,
        ZuvielRueckgeben,
        ZuvielVerloren
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
            case ZuvielAusgeben:
                return "Nicht genug Bestand verfügbar um " + menge + "auszugeben";
            case ZuvielRueckgeben:
                return "Rückgabemenge übersteigt Ausleihmenge";
            case ZuvielVerloren:
                return "Verlustmenge übersteigt Ausleihmenge";
        }
        throw new Error("sollte nie erreicht werden");
    }
}
