public class Verkauf extends RechnungsPosten {
	public Verkauf(int pMenge, Artikel pArtikel) {
		this.menge = pMenge;
		this.artikel = pArtikel;
		this.betrag = this.artikel.kaufPreis(menge)+this.artikel.handlingPauschale;
	}

	@Override
	public String toString() {
		return "Rechnungspunkt für den Verkauf von " + this.menge + "x" + this.artikel + " zu einem Preis von " + this.betrag + "€.";
	}
}
