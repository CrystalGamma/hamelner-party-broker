public class Verkauf extends RechnungsPosten {
	public Verkauf(int pMenge, Artikel pArtikel) {
		this.menge = pMenge;
		this.artikel = pArtikel;
		this.betrag = pArtikel.kaufPreis(menge);
	}

	@Override
	public String toString() {
		return "Verkauf von " + this.menge + "x" + this.artikel
				+ " zu einem Preis von " + Services.geldString(this.betrag) + ".";
	}
}
