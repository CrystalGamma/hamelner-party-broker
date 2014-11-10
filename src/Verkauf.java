public class Verkauf extends RechungsPosten {
	public Verkauf(int pMenge, Artikel pArtikel) {
		this.menge = pMenge;
		this.artikel = pArtikel;
		this.betrag = pArtikel.kaufPreis(menge);
	}

	@Override
	public String toString() {
		return "Rechnungspunkt für den Verkauf von " + this.menge + "x" + this.artikel + " zu einem Preis von " + this.betrag + "€.";
	}
}
