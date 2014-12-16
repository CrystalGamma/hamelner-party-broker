public class Verkauf extends RechnungsPosten {
	public Verkauf(int menge, Artikel artikel) {
		this.menge = menge;
		this.artikel = artikel;
		this.betrag = artikel.kaufPreis(menge);
	}

	@Override
	public String toString() {
		return "Verkauf von " + this.menge + "x " + this.artikel
				+ " zu einem Preis von " + Services.geldString(this.betrag) + ".";
	}
}
