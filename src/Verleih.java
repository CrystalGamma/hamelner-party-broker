public class Verleih extends RechungsPosten {
	public Verleih(Ausleihe pAusleihe, int pMenge, int zeitDelta) {
		this.menge = pMenge;
		this.artikel = pAusleihe.getPosten();
		//this.betrag = ((LagerPosten) this.artikel).ausleihePreis(0, this.menge, zeitDelta);
	}

	@Override
	public String toString() {
		return "Rechnungspunkt für den Verleih von " + this.menge + "x" + this.artikel + " zu einer Gebühr von " + this.betrag + "€.";
	}
}
