public class Verlust extends RechungsPosten {
	public Verlust(Ausleihe pAusleihe, int pMenge, int zeitDelta) {
		this.menge = pMenge;
		this.artikel = pAusleihe.getPosten();
		
		long startZeit = pAusleihe.getStartZeit();
		long endZeit = pAusleihe.getEndZeit();
		long zeitDelta = endZeit-startZeit;
		this.betrag = ((LagerPosten) this.artikel).verlustGebuehr(0, pMenge, zeitDelta);
	}

	@Override
	public String toString() {
		return "Rechnungspunkt für den Verlust von " + this.menge + "x"
				+ this.artikel + " in Höhe von " + this.betrag + "€.";
	}
}
