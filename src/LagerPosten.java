/**
 * Die Klasse LagerPosten basiert auf einem Artikel, wird vom Funktionsumfang her jedoch
 * deutlich erweitert. So werden unter Anderem Gebühren für das Leihen, Überziehen und den
 * Verlust von LagerPosten eingeführt sowie die Methoden zur Berechnung der sich daraus
 * ergebenden Preise im Falle einer Ausleihe.
 */
public class LagerPosten extends Artikel {
	int bestand;
	boolean verkaeuflich, verleihbar;
	int preis, leihGebuehr, ueberzugsGebuehr, verlustGebuehr;

	/**
	 * Erzeugt eine Instanz der Klasse LagerPosten.
	 * 
	 * @param name Produktname
	 */
	public LagerPosten(String name) {
		this.name = name;
	}

	/**
	 * Erzeugt eine Instanz der Klasse LagerPosten.
	 * 
	 * @param name Produktname
	 * @param verkaufspreis Verkaufspreis
	 * @param leihGebuehr Leihgebühr
	 * @param handlingPauschale Handling-Pauschale
	 * @param verlustGebuehr Verlustgebühr
	 * @param verkaeuflich Ist das Produkt verkäuflich?
	 * @param verleihbar Ist das Produkt verleihbar?
	 * @param bestand Bestand des Produkts
	 */
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

	/**
	 * Ändert, ob es möglich sein soll, den LagerPosten käuflich zu erwerben.
	 * 
	 * @param b Soll das Produkt verkäuflich sein?
	 */
	public void setVerkaeuflich(boolean b) {
		verkaeuflich = b;
	}

	/**
	 * Ändert, ob es möglich sein soll, den LagerPosten auszuleihen.
	 * 
	 * @param b Soll das Produkt verleihbar sein?
	 */
	public void setVerleihbar(boolean b) {
		verleihbar = b;
	}

	/**
	 * Ändert den Preis der Instanz.
	 * 
	 * @param p Neuer Preis des LagerPosten
	 */
	public void setPreis(int p) {
		preis = p;
	}

	/**
	 * Ändert die Leihgebühr der Instanz.
	 * 
	 * @param p Neue Leihgebühr des LagerPosten
	 */
	public void setLeihGebuehr(int p) {
		leihGebuehr = p;
	}

	/**
	 * Ändert die Überzugsgebühr der Instanz.
	 * 
	 * @param p Neue Überzugsgebühr des LagerPosten
	 */
	public void setUeberzugsGebuehr(int p) {
		ueberzugsGebuehr = p;
	}

	/**
	 * Ändert die Verlustgebühr der Instanz.
	 * 
	 * @param p Neue Verlustgebühr des LagerPosten
	 */
	public void setVerlustGebuehr(int p) {
		verlustGebuehr = p;
	}

	/**
	 * Jona Stubbe
	 * @return eine Menschenlesbare Formatierung von Bestand, Name und Preisen, soweit zutreffend
	 */
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

	/**
	 * Ermittelt den Kaufpreis des LagerPosten unter Berücksichtung einer angegebenen Menge.
	 * 
	 * @param menge Menge zu der der Kaufpreis ermittelt werden soll
	 */
	public int kaufPreis(int menge) {
		return preis*menge;
	}

	/**
	 * Ermittelt, ob der LagerPosten verkäuflich ist.
	 * 
	 * @return LagerPosten verkäuflich?
	 */
	public boolean istVerkaeuflich() {
		return verkaeuflich;
	}

	/**
	 * Ermittelt, ob der LagerPosten verleihbar ist.
	 * 
	 * @return LagerPosten verleihbar?
	 */
	public boolean istVerleihbar() {
		return verleihbar;
	}

	/**
	 * Verändert die Lagermenge des Postens.
	 * Überprüft ob genug vorhanden ist.
	 * @throws MengenFehler wenn der Bestand negativ werden würde
	 * Jona Stubbe
	 * @param menge Änderungswert (+/-)
	 */
	public void bestandAendern(int menge) {
		if (bestand < -menge)
			throw new MengenFehler(MengenFehler.Art.ZuvielAusgeben, -menge);
		bestand += menge;
	}

	/**
	 * Ermittelt den Bestand der Instanz.
	 * 
	 * @return Aktueller Bestand des LagerPosten
	 */
	public int getBestand() {
		return bestand;
	}

	/**
	 * Rechnet Stunden in Tage um.
	 * 
	 * @param stunden Anzahl Stunden
	 * @return Anzahl der Stunden in Tage
	 */
	private static int inTagen(int stunden) {
		return (stunden + 23) / 24;
	}

	/**
	 * berechnet die gesamte Leihgebühr für eine Ausleihe
	 * Jona Stubbe
	 * @param zeitGeplant bei der Ausleihe angegebene Ausleihzeit
	 * @param menge Menge für die ein Preis ermittelt werden soll
	 * @param zeitDelta Differenz zwischen tatsächlicher und geplanter Zeit
	 * 
	 * @return Berechneter Ausleihepreis unter Berücksichtung der Zeit und der Menge.
	 */
	public int ausleihePreis(int zeitGeplant, int menge, int zeitDelta) {
		if (zeitDelta > 0)
			return menge * (handlingPauschale + leihGebuehr * inTagen(zeitGeplant)
					+ ueberzugsGebuehr * zeitDelta);
		else
			return menge * (handlingPauschale + leihGebuehr * inTagen(zeitGeplant));
	}

	/**
	 * berechnet die Gebühr, die bei Verlust gezahlt werden muss
	 * Jona Stubbe
	 * @param zeitGeplant bei Ausleihe angegebene Ausleihdauer
	 * @param menge Menge für die eine Verlustgebühr ermittelt werden soll
	 * @param zeitDelta Differenz zwischen geplanter Leihdauer und Verlustmeldungszeit
	 * 
	 * @return Berechnete Verlustgebühr unter Berücksichtigung der Zeit und der Menge.
	 */
	public int verlustGebuehr(int zeitGeplant, int menge, int zeitDelta) {
		return ausleihePreis(zeitGeplant, menge, zeitDelta) + ((-handlingPauschale+verlustGebuehr)*menge);
	}

	/**
	 * Ermittelt ob der Bestand der Instanz größer als 0 ist.
	 * 
	 * @return Lagerposten verfügbar?
	 */
	@Override
	public boolean istVerfuegbar() {
		return bestand > 0;
	}
}
