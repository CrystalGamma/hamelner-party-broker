public class Kunde {
	String name, vorName, strasse, ort;
	int id, hausnummer, plz, umsatz;// id muss noch gesetzt werden mit static
									// als global
	Ausleihe ausleihe[] = new Ausleihe[10];// maximal 10 ausleihen
	int ausleihNummer, posRechnungspunkte;
	RechnungsPosten[] offeneRechnungspunkte = new RechnungsPosten[10];
	RechnungsPosten[] geschlosseneRechnungspunkte = new RechnungsPosten[10];

	public Kunde(String name, String vorName) {
		ausleihNummer = 0;
		this.name = name;
		this.vorName = vorName;
	}

	public void setStrasse(String strasse) {

		this.strasse = strasse;
	}

	public void setHausnummer(int hausnummer) {
		// b oder a zusatz muss abgefangen werden
		if (hausnummer > 0) {
			this.hausnummer = hausnummer;
		} else {
			throw new Error("Negative oder 0 Hausnummern gibt es nicht");
		}
	}

	public void setPlz(int plz) {
		if (plz >= 10000 && plz >= 99999) {
			this.plz = plz;
		} else {
			throw new Error("Plz ist immer 5-stellig");
		}
	}

	public void setOrt(String ort) {

		this.ort = ort;
	}

	public String getName() {
		return name;
	}

	public String getVorName() {
		return vorName;
	}

	public int getID() {
		return id;
	}

	public String getStrasse() {
		return strasse;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public int getPlz() {
		return plz;
	}

	public String getOrt() {
		return ort;
	}

	public void rueckgabe(LagerPosten lagerPosten, int zeit, int menge) {
		if (menge > 0) {
			int gesamtMenge = 0;
			for (int i = 0; i < ausleihe.length; i++) {
				if (ausleihe[i].getPosten().name == lagerPosten.name)// ausgeliehe
																		// Menge
																		// von
																		// diesem
																		// posten
																		// errechnen
				{
					gesamtMenge = gesamtMenge + ausleihe[i].getMenge();
				}

			}
			// bis hierhin wurde die gesammt menge des ausgeliehenen gutes
			// bestimmt
			if (gesamtMenge >= menge) {
				int restMenge = menge;
				int alteRestmenge;
				for (int i = 0; restMenge > 0; i++) {
					alteRestmenge = restMenge;
					restMenge = ausleihe[i].rueckgabe(restMenge);
					offeneRechnungspunkte[posRechnungspunkte] = new Verleih(
							ausleihe[i], (alteRestmenge - restMenge));// zeit
					if (restMenge != 0) {
						for (int k = i; k < (ausleihe.length - 1); k++)//
						{
							ausleihe[k] = ausleihe[k + 1];
						}
					}

					lagerPosten.bestandAendern(alteRestmenge - restMenge);

				}

			}
		} else {
			throw new Error("Diese Menge hast du nicht ausgeliehen");
		}
	}

	public void verlustMelden(LagerPosten LagerPosten, int zeit, int menge) {

	}

	public void ausleihe(Ausleihe ausleihe) {

		this.ausleihe[ausleihNummer] = ausleihe;
		ausleihe.buchen();
		ausleihNummer++;
	}

	public RechnungsPosten[] abrechnung() {
		// exception werfen wenn array voll bzw. in liste ändern
		RechnungsPosten[] RechnungspunkteTemp = new RechnungsPosten[10];

		for (int i = 0; i < offeneRechnungspunkte.length; i++) {
			geschlosseneRechnungspunkte[geschlosseneRechnungspunkte.length] = offeneRechnungspunkte[i];
			RechnungspunkteTemp[i] = offeneRechnungspunkte[i];
		}
		return RechnungspunkteTemp;// liefert die bis zu diesem zeitpunkt noch
									// offene Rechnungspunkte zurück und schiebt
									// diese von offen nach geschlossen
	}

	public int berechneUmsatz() {
		int gesamtUmsatz;
		for (int i = 0; i < geschlosseneRechnungspunkte.length; i++)// Rechnungsposten
		{
			gesamtUmsatz = gesamtUmsatz
					+ geschlosseneRechnungspunkte[i].getUmsatz();
		}
		return gesamtUmsatz;
	}

	public String[] getTransaktionen() {
		String[] Transaktionen = new String[10];
		int position = 0;
		for (int i = 0; i < geschlosseneRechnungspunkte.length; i++)// geschlossene
																	// rechnungspunkte
		{
			Transaktionen[position] = geschlosseneRechnungspunkte[i].toString();
			position++;
		}
		for (int i = 0; i < offeneRechnungspunkte.length; i++) // offene
																// Rechnungspunkte=Ausleihe
		{
			Transaktionen[position] = offeneRechnungspunkte[i].toString();
			position++;
		}

		return Transaktionen;
	}

	public void kaufen(Artikel Artikel, int menge) {
		if (Artikel.istVerkaeuflich())// unterscheidung Dienstleistung und
										// Objekte
		{

		}
	}
}
