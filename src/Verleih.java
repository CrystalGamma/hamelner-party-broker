public class Verleih extends RechnungsPosten {
	public Verleih(LagerPosten posten, int menge, int betrag) {
		this.menge = menge;
		this.artikel = posten;
		this.betrag = betrag;
	}

	@Override
	public String toString() {
		return "Verleih von " + this.menge + "x" + this.artikel + " zu einer Gebühr von " + Services.geldString(this.betrag) + ".";
	}
}
