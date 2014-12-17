import java.util.Arrays;
import java.util.LinkedList;

/**
 * Die Klasse Kunde verwaltet neben den Stammdaten einer Instanz auch deren RechnungsPosten.
 * Darüber hinaus organisiert die Klasse auch die für den Betrieb gängigen Tätigkeiten
 * wie das Kaufen, Ausleihen und Rückgeben von Artikeln.
 */
public class Kunde {
	String name, vorName, strasse, ort;
	int id, hausnummer, plz, umsatz;// id muss noch gesetzt werden mit static
									// als global
	LinkedList<Ausleihe> ausleihe = new LinkedList<Ausleihe>();
	LinkedList<RechnungsPosten> offeneRechnungspunkte = new LinkedList<RechnungsPosten>();
	LinkedList<RechnungsPosten> geschlosseneRechnungspunkte = new LinkedList<RechnungsPosten>();
	
	/**
	 * Erzeugt eine Instanz der Klasse Kunde.
	 * 
	 * @param name Name
	 * @param vorName Vorname
	 * @param straße Straße
	 * @param hausnummer Hausnummer
	 * @param plz Postleitzahl
	 * @param ort Wohnort
	 */
	public Kunde(String name, String vorName, String straße, int hausnummer, int plz,String ort) {
		this.name = name;
		this.vorName = vorName;
		setStrasse(straße);
		setHausnummer(hausnummer);
		setPlz(plz);
		setOrt(ort);
	}

	/**
	 * Ändert die Straße der Instanz.
	 * 
	 * @param strasse Neue Straße des Kunden
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
	/**
	 * Die Hausnummer setzten
	 * 
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
	
	/**
	 * Ändert den Namen der Instanz.
	 * 
	 * @param name Neuer Name des Kunden
	 */
	public void setName(String name){
		this.name=name;
	}

	/**
	 * Ändert den Wohnort der Instanz.
	 * 
	 * @param ort Neuer Wohnort des Kunden
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * Ermittelt den Nachnamen einer Instanz.
	 * 
	 * @return Nachname des Kunden
	 */
	public String getName() {
		return name;
	}

	/**
	 * Ermittelt den Vorname einer Instanz.
	 * 
	 * @return Vorname des Kunden
	 */
	public String getVorName() {
		return vorName;
	}

	/**
	 * Ermittelt die Kunden-ID einer Instanz.
	 * 
	 * @return Kunden-ID des Kunden
	 */
	public int getID() {
		return id;
	}

	/**
	 * Ermittelt die Straße einer Instanz.
	 * 
	 * @return Straße des Kunden
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * Ermittelt die Hausnummer einer Instanz.
	 * 
	 * @return Hausnummer des Kunden
	 */
	public int getHausnummer() {
		return hausnummer;
	}

	/**
	 * Ermittelt die Postleitzahl einer Instanz.
	 * 
	 * @return Postleitzahl des Kunden
	 */
	public int getPlz() {
		return plz;
	}

	/**
	 * Ermittelt den Wohnort einer Instanz.
	 * 
	 * @return Wohnort des Kunden
	 */
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
		Verleih verl = new Verleih(lagerPosten, gesamtMenge, betrag, zeit);
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
		Verlust verl = new Verlust(lagerPosten, gesamtMenge, betrag, zeit);
		geschlosseneRechnungspunkte.addFirst(verl);
		return verl;
	}

	/**
	 * Fügt eine Ausleihe zu der Ausleih-Liste der Instanz hinzu und bucht diese.
	 */
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
	 * 
	 * @return String-Array mit allen offenen und geschlossenen RechnungsPosten des Kunden.
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
	public RechnungsPosten kaufen(Artikel artikel, int menge, int zeit) {
		if (!artikel.istVerkaeuflich())
			throw new ArtikelFehler(artikel, ArtikelFehler.Art.NichtVerkaeuflich);
		if (menge < 0)
			throw new MengenFehler(MengenFehler.Art.NegativKaufen, menge);
		RechnungsPosten posten;
		if (artikel instanceof LagerPosten) {
			((LagerPosten)artikel).bestandAendern(-menge);
			posten = new Verkauf(menge, artikel, zeit);
			offeneRechnungspunkte.addFirst(posten);
		} else {
			posten = new Verkauf(menge, artikel, zeit);
			geschlosseneRechnungspunkte.addFirst(posten);
		}
		return posten;
	}

	/**
	 * Ermittelt die Anschrift einer Instanz.
	 * 
	 * @return Anschrift bestehend aus Straße, Hausnummer, Postleitzahl und Wohnort
	 */
	public String getAnschrift() {
		return strasse + " " + hausnummer + "\n" + plz + " " + ort;
	}
	
	/**
	 * Gibt eine lesbare Formatierung der Instanz der Klasse Kunde zurück.
	 * 
	 * @return String aus Nachname und Vorname des Kunden
	 */
	@Override
	public String toString(){
		return this.name + ", " + this.vorName;
	}
}
