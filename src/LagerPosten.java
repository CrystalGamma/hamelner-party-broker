public class LagerPosten extends Artikel {
	int bestand;
	boolean verkaeuflich, verleihbar;
	int preis, leihGebuehr, ueberzugsGebuehr, verlustGebuehr;

	public LagerPosten(String name) {
		this.name = name;
	}

	public LagerPosten(String name,int verkaufspreis, int leihGebuehr, int handlingPauschale,
			int verlustGebuehr, boolean verkaeuflich, boolean verleihbar,
			int bestand) {
		this.name = name;
		this.leihGebuehr = leihGebuehr;
		this.handlingPauschale = handlingPauschale;
		this.verlustGebuehr = verlustGebuehr;
		this.verkaeuflich = verkaeuflich;
		this.verleihbar = verleihbar;
		this.bestand = bestand;
		this.preis= verkaufspreis;
		this.ueberzugsGebuehr = leihGebuehr / 5 + 1;
	}

	public LagerPosten(String name, int wert, boolean verk, boolean verl,
			int best) {
		this.name = name;
		verleihbar = verl;
		verkaeuflich = verk;
		handlingPauschale = wert / 2;
		preis = wert;
		leihGebuehr = (wert - (wert / 2)) / 10 + 1;
		ueberzugsGebuehr = leihGebuehr / 5 + 1;
		verlustGebuehr = wert - (wert / 2);
		bestand = best;
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

	public String bestandString() {
		if (verkaeuflich && verleihbar) {
			return bestand + "x " + name + "(Kaufpreis " + Services.geldString(preis)
					+ ", Handling " + Services.geldString(handlingPauschale)
					+ ", Leihgebühr " + Services.geldString(leihGebuehr) + "/d)";
		}
		if (verkaeuflich)
			return bestand + "x " + name + "(Kaufpreis " + Services.geldString(preis) + ")";
		if (verleihbar)
			return bestand + "x " + name + "(Handling " + Services.geldString(handlingPauschale)
					+ ", Leihgebühr " + Services.geldString(leihGebuehr) + "/d)";
		return "";
	}

	public int kaufPreis(int menge) {
		return preis*menge;
	}

	public boolean istVerkaeuflich() {
		return verkaeuflich;
	}

	public boolean istVerleihbar() {
		return verleihbar;
	}

	public void bestandAendern(int menge) {
		if (bestand < -menge)
			throw new MengenFehler(MengenFehler.Art.ZuvielAusgeben, -menge);
		bestand += menge;
	}

	public int getBestand() {
		return bestand;
	}

	private static int inTagen(int stunden) {
		return (stunden + 23) / 24;
	}

	public int ausleihePreis(int zeitGeplant, int menge, int zeitDelta) {
		if (zeitDelta > 0)
			return handlingPauschale + leihGebuehr * inTagen(zeitGeplant)
					+ ueberzugsGebuehr * zeitDelta;
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
