public class Verlust extends RechnungsPosten {
	public Verlust(LagerPosten posten, int pMenge, int betrag) {
		this.menge = pMenge;
		this.artikel = posten;
		this.betrag = betrag;
	}

	@Override
	public String toString() {
		return "Verlust von " + this.menge + "x" + this.artikel + " zu einer Gebühr von " + Services.geldString(this.betrag) + ".";
	}
}
