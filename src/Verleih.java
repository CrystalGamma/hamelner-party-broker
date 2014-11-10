public class Verleih extends RechungsPosten {
	public Verleih(Ausleihe pAusleihe, int pMenge) {
		this.menge = pMenge;
		this.artikel = pAusleihe.getPosten();
		
		long startZeit = pAusleihe.getStartZeit();
		long endZeit = pAusleihe.getEndZeit();
		long zeitDelta = endZeit-startZeit;
		this.betrag = ((LagerPosten) this.artikel).ausleihePreis(0, this.menge, (int)zeitDelta);
	}

	@Override
	public String toString() {
		return "Rechnungspunkt für den Verleih von " + this.menge + "x" + this.artikel + " zu einer Gebühr von " + this.betrag + "€.";
	}
}
