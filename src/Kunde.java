import java.util.Arrays;

import java.util.LinkedList;
public class Kunde {
	String name, vorName, strasse, ort;
	int id, hausnummer, plz, umsatz;// id muss noch gesetzt werden mit static
									// als global
	LinkedList<Ausleihe> ausleihe = new LinkedList<Ausleihe>();
	LinkedList<RechnungsPosten> offeneRechnungspunkte = new LinkedList<RechnungsPosten>();
	LinkedList<RechnungsPosten> geschlosseneRechnungspunkte = new LinkedList<RechnungsPosten>();
	
	public Kunde(String name, String vorName, String straße, int hausnummer, int plz,String ort) {
		this.name = name;
		this.vorName = vorName;
		setStrasse(straße);
		setHausnummer(hausnummer);
		setPlz(plz);
		setOrt(ort);
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	/**
	 * Die Hausnummer setzten
	 * @param hausnummer die zu setztende Hausnummer
	 * Leon Westhof
	 *  */
	public void setHausnummer(int hausnummer) {
		// TODO: b oder a zusatz muss abgefangen werden
		if (hausnummer > 0) {
			this.hausnummer = hausnummer;
		} else {
			throw new AdressFehler("Negative oder 0 Hausnummern gibt es nicht");
		}
	}
	/**
	 * Setzt die Postleitzahl
	 * @param plz die zu setztende Postleitzahl
	 * Leon Westhof
	 *  */
	public void setPlz(int plz) {
		if (plz >= 1000 && plz <= 99998) {
			this.plz = plz;
		} else {
			throw new AdressFehler("Plz ist immer 5-stellig");//die überprüfung ist überflüssig
		}
	}
	public void setName(String name){
		this.name=name;
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

	/**
	 * Gibt eine gegebene Menge eines LagerPostens an den Bestand zurück.
	 * Jona Stubbe
	 * @param lagerPosten: Posten der zurückgegeben werden soll
	 * @param zeit: Zeitpunkt der Rückgabe
	 * @param menge: Rückgabemenge
	 * @return einen RechnungsPosten der durch die Rückgabe entstanden ist
	 */
	public Verleih rueckgabe(LagerPosten lagerPosten, int zeit, int menge) {
		int pos = ausleihe.size();
		Ausleihe[] rev = new Ausleihe[pos];
		for (Ausleihe ausl: ausleihe)
			rev[--pos] = ausl;
		LinkedList<Ausleihe> tmp = new LinkedList<Ausleihe>();
		int gesamtMenge = menge;
		int betrag = 0;
		for (Ausleihe ausl: rev) {
			if (ausl.getPosten() == lagerPosten) {
				int alt = ausl.getMenge();
				ausl = ausl.reduzieren(menge);
				int diff = alt - ausl.getMenge();
				menge -= diff;
				betrag += lagerPosten.ausleihePreis(ausl.getEndZeit() - ausl.getStartZeit(),
					diff,
					zeit - ausl.getEndZeit());
			}
			if (!ausl.istLeer())
				tmp.addFirst(ausl);
		}
		if (menge > 0)
			throw new MengenFehler(MengenFehler.Art.ZuvielRueckgeben, menge);
		lagerPosten.bestandAendern(gesamtMenge);
		ausleihe = tmp;
		Verleih verl = new Verleih(lagerPosten, gesamtMenge, betrag);
		geschlosseneRechnungspunkte.addFirst(verl);
		return verl;
	}

	/**
	 * meldet den Verlust eines Ausgeliehenen Postens:
	 * Entfernt die gegebene Menge von den Ausleihen des Kunden,
	 * aber fügt sie nicht dem Lager zu.
	 * Berechnet dem Kunden die entsprechenden Kosten.
	 * Jona Stubbe
	 * @param lagerPosten: verloren zu meldender Artikel
	 * @param zeit: Zeitpunkt der Verlustmeldung
	 * @param menge: verlorene Menge
	 * @return RechungsPosten mit Betrag entsprechender Gebühren
	 */
	public Verlust verlustMelden(LagerPosten lagerPosten, int zeit, int menge) {
		if(lagerPosten.verlustGebuehr == 0)
			throw new Error("Verlust dieses Produktes ist nicht möglich.");
		int pos = ausleihe.size();
		Ausleihe[] rev = new Ausleihe[pos];
		for (Ausleihe ausl: ausleihe)
			rev[--pos] = ausl;
		LinkedList<Ausleihe> tmp = new LinkedList<Ausleihe>();
		int gesamtMenge = menge;
		int betrag = 0;
		for (Ausleihe ausl: rev) {
			if (ausl.getPosten() == lagerPosten) {
				int alt = ausl.getMenge();
				ausl = ausl.reduzieren(menge);
				int diff = alt - ausl.getMenge();
				menge -= diff;
				betrag += lagerPosten.verlustGebuehr(ausl.getEndZeit() - ausl.getStartZeit(),
					diff,
					zeit - ausl.getEndZeit());
			}
			if (!ausl.istLeer())
				tmp.addFirst(ausl);
		}
		if (menge > 0)
			throw new MengenFehler(MengenFehler.Art.ZuvielVerloren, menge);
		ausleihe = tmp;
		Verlust verl = new Verlust(lagerPosten, gesamtMenge, betrag);
		geschlosseneRechnungspunkte.addFirst(verl);
		return verl;
	}

	public void ausleihe(Ausleihe ausl) {
		ausleihe.addFirst(ausl);
		// TODO: sortiert nach startzeit einfügen
		ausl.buchen();
	}

	/**
	 * Kopiert alle offenen RechnungsPunkte in die Liste der geschlossene
	 * RechnungsPunkte und gibt sie zurück.
	 * Jona Stubbe
	 * @return die vormals offenen Rechnungspunkte
	 */
	public LinkedList<RechnungsPosten> abrechnung() {
		LinkedList<RechnungsPosten> tmp = offeneRechnungspunkte;
		geschlosseneRechnungspunkte.addAll(0, tmp);
		offeneRechnungspunkte = new LinkedList<>();
		return tmp;
	}

	/**
	 * Addiert den Betrag aller Transaktionen des Kunden
	 * Jona Stubbe
	 * @return den gesamten Umsatz des Kunden
	 */
	public int berechneUmsatz() {
		int gesamtUmsatz = 0;
		for (RechnungsPosten rp: geschlosseneRechnungspunkte) {
			gesamtUmsatz += rp.getUmsatz();
		}
		return gesamtUmsatz;
	}

	/**
	 * Erstellt ein Array aus den Stringdarstellungen aller Transaktionen
	 * (offene/geschlossene Rechungspunkte, ausstehende Ausleihen)
	 * Jona Stubbe
	 */
	public String[] getTransaktionen() {
		LinkedList<String> transaktionen = new LinkedList<String>();
		for (RechnungsPosten rp : geschlosseneRechnungspunkte)
			transaktionen.addFirst(rp.toString());
		for (RechnungsPosten rp: offeneRechnungspunkte)
			transaktionen.addFirst(rp.toString());
		for (Ausleihe ausl: ausleihe)
			transaktionen.addFirst(ausl.toString());
		return Arrays.copyOf(transaktionen.toArray(), transaktionen.size(),
							String[].class);
	}

	/**
	 * Entfernt eine bestimmte Menge eines Artikels aus dem Lager,
	 * stellt dem Kunden den Kauf in Rechnung.
	 * Jona Stubbe
	 * @param artikel: zu (ver-)kaufender Artikel
	 * @param menge: zu (ver-)kaufende Menge
	 * @return RechnungsPunkt für den Kauf; schon geschlossen
	 * 			falls sofort fällig
	 */
	public RechnungsPosten kaufen(Artikel artikel, int menge) {
		if (!artikel.istVerkaeuflich())
			throw new ArtikelFehler(artikel, ArtikelFehler.Art.NichtVerkaeuflich);
		if (menge < 0)
			throw new MengenFehler(MengenFehler.Art.NegativKaufen, menge);
		RechnungsPosten posten;
		if (artikel instanceof LagerPosten) {
			((LagerPosten)artikel).bestandAendern(-menge);
			posten = new Verkauf(menge, artikel);
			offeneRechnungspunkte.addFirst(posten);
		} else {
			posten = new Verkauf(menge, artikel);
			geschlosseneRechnungspunkte.addFirst(posten);
		}
		return posten;
	}

	public String getAnschrift() {
		return strasse + " " + hausnummer + "\n" + plz + " " + ort;
	}
	
	@Override
	public String toString(){
		return this.name + ", " + this.vorName;
	}
}
