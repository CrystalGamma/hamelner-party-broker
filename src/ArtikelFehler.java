public class ArtikelFehler extends RuntimeException {
    enum Art {
        NichtVerkaeuflich,
        NichtVerleihbar
    }

    Artikel artikel;
    Art art;

    public ArtikelFehler(Artikel artikel, Art art) {
        this.art = art;
        this.artikel = artikel;
    }

    public String toString() {
        switch (art) {
            case NichtVerkaeuflich:
                return "'" + artikel.toString() + "' ist nicht verkäuflich";
            case NichtVerleihbar:
                return "'" + artikel.toString() + "' ist nicht verleihbar";
        }
        return "sollte nie erreicht werden";
    }
}
