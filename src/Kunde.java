import Rechnung.RechnungsPosten;
import Rechnung.Verkauf;
import Rechnung.Verleih;
import Rechnung.Verlust;

import java.util.Arrays;

import java.util.LinkedList;
public class Kunde {
	String name, vorName, strasse, ort;
	int id, hausnummer, plz, umsatz;// id muss noch gesetzt werden mit static
									// als global
	LinkedList<Ausleihe> ausleihe = new LinkedList<Ausleihe>();
	LinkedList<RechnungsPosten> offeneRechnungspunkte = new LinkedList<RechnungsPosten>();
	LinkedList<RechnungsPosten> geschlosseneRechnungspunkte = new LinkedList<RechnungsPosten>();
	/**
	 * @author Leon Westhof
	 *  */
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
	 * @author Leon Westhof
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
	 * @author Leon Westhof
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

	public LinkedList<RechnungsPosten> abrechnung() {
		LinkedList<RechnungsPosten> tmp = offeneRechnungspunkte;
		geschlosseneRechnungspunkte.addAll(0, tmp);
		offeneRechnungspunkte = new LinkedList<>();
		return tmp;
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
		return Arrays.copyOf(transaktionen.toArray(), transaktionen.size(),
							String[].class);
	}

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
