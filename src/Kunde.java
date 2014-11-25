import java.util.LinkedList;
public class Kunde {
	String name, vorName, strasse, ort;
	int id, hausnummer, plz, umsatz;// id muss noch gesetzt werden mit static
									// als global
	LinkedList<Ausleihe> ausleihe = new LinkedList<Ausleihe>();
	LinkedList<RechnungsPosten> offeneRechnungspunkte = new LinkedList<RechnungsPosten>();
	LinkedList<RechnungsPosten> geschlosseneRechnungspunkte = new LinkedList<RechnungsPosten>();
	public Kunde(String name, String vorName) {
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

	/*public void rueckgabe(LagerPosten lagerPosten, int zeit, int menge) {
		//Ändern in geht durch wenn funktioniert wird es angenommen wenn nicht ber temp array wiedr zurückgeschrieben
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
					// ausleihe[i].setEndZeit(zeit);///endzeit setzten//
					// geplante endzeit wurde gesetzt nun muss ich noch die
					// reale endzeit eingeben und florian die kosten berechnen
					offeneRechnungspunkte[posRechnungspunkte] = new Verleih(
							ausleihe[i], (alteRestmenge - restMenge));
					if (restMenge != 0) {
						for (int k = i; k < (ausleihe.length - 1); k++)//
						{
							ausleihe[k] = ausleihe[k + 1];
						}
					}

					lagerPosten.bestandAendern(alteRestmenge - restMenge);// hier
																			// muss
																			// die
																			// verfügbare
																			// stehen

				}

			} else {
				throw new Error("Diese Menge hast du nicht ausgeliehen");
			}
		} else {
			throw new Error("Negative Mengen");
		}
	}

	public void verlustMelden(LagerPosten lagerPosten, int zeit, int menge) {
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
					// ausleihe[i].setEndZeit(zeit);///endzeit setzten//
					// geplante endzeit wurde gesetzt nun muss ich noch die
					// reale endzeit eingeben und florian die kosten berechnen
					offeneRechnungspunkte[posRechnungspunkte] = new Verlust(
							ausleihe[i], (alteRestmenge - restMenge));
					if (restMenge != 0) {
						for (int k = i; k < (ausleihe.length - 1); k++)//
						{
							ausleihe[k] = ausleihe[k + 1];
						}
					}

					lagerPosten.bestandAendern(alteRestmenge - restMenge);// hier
																			// muss
																			// die
																			// gesammtmenge
																			// stehen

				}

			} else {
				throw new Error("Diese Menge hast du nicht ausgeliehen");
			}
		} else {
			throw new Error("Negative Mengen");
		}
	}*/

	public void ausleihe(Ausleihe ausl) {
		ausleihe.addFirst(ausl);
		ausl.buchen();
	}

	public RechnungsPosten[] abrechnung() {
		LinkedList<RechnungsPosten> tmp = offeneRechnungspunkte;
		geschlosseneRechnungspunkte.addAll(0, tmp);
		return (RechnungsPosten[])tmp.toArray();
	}

	public int berechneUmsatz() {
		int gesamtUmsatz = 0;
		for (RechnungsPosten rp: geschlosseneRechnungspunkte) {
			gesamtUmsatz += rp.getUmsatz();
		}
		return gesamtUmsatz;
	}

	public String[] getTransaktionen() {
		LinkedList<String> transaktionen = new LinkedList<String>();
		for (RechnungsPosten rp : geschlosseneRechnungspunkte)
			transaktionen.addFirst(rp.toString());
		for (RechnungsPosten rp: offeneRechnungspunkte)
			transaktionen.addFirst(rp.toString());
		for (Ausleihe ausl: ausleihe)
			transaktionen.addFirst(ausl.toString());
		return (String[])transaktionen.toArray();
	}

	public void kaufen(Artikel artikel, int menge) {
		if (!artikel.istVerkaeuflich())
			throw new Error("Artikel ist nicht verkäuflich");
		if (menge < 0)
			throw new Error("Kann keine negative Menge kaufen");
		if (artikel instanceof LagerPosten)
			((LagerPosten)artikel).bestandAendern(-menge);
		offeneRechnungspunkte.addFirst(new Verkauf(menge, artikel));
	}
	
}
