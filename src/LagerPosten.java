public class LagerPosten extends Artikel {
	int bestand;
	boolean verkaeuflich, verleihbar;
	int preis, leihGebuehr, ueberzugsGebuehr, verlustGebuehr;

	public LagerPosten(String name) {
		this.name = name;
	}

	public void setVerkaeuflich(boolean b) {
		verkaeuflich = b;
	}

	public void setVerleihbar(boolean b) {
		verleihbar = b;
	}

	public void setPreis(int p) {
		preis = p;
	}

	public void setLeihGebuehr(int p) {
		leihGebuehr = p;
	}

	public void setUeberzugsGebuehr(int p) {
		ueberzugsGebuehr = p;
	}

	public void setVerlustGebuehr(int p) {
		verlustGebuehr = p;
	}

	public String toString() {
		return bestand + "x " + name;
	}

	public int kaufPreis(int menge) {
		return preis;
	}

	public boolean istVerkaeuflich() {
		return verkaeuflich;
	}

	public boolean istVerleihbar() {
		return verleihbar;
	}

	public void bestandAendern(int menge) {
		if (bestand < menge) {
			throw new Error("Nicht genug Bestand verfügbar");
		}
		bestand -= menge;
	}

	public int getBestand() {
		return bestand;
	}

	private static int inTagen(int stunden) {
		return (stunden + 23) / 24;
	}

	public int ausleihePreis(int zeitGeplant, int menge, int zeitDelta) {
		if (zeitDelta > 0)
			return handlingPauschale + leihGebuehr * inTagen(zeitGeplant) + ueberzugsGebuehr * zeitDelta;
		else
			return handlingPauschale + leihGebuehr * inTagen(zeitGeplant);
	}

	public int verlustGebuehr(int zeitGeplant, int menge, int zeitDelta) {
		return ausleihePreis(zeitGeplant, menge, zeitDelta) + verlustGebuehr;
	}

	@Override
	public boolean istVerfuegbar() {
		return bestand > 0;
	}
}
