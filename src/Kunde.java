import java.util.Arrays;
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
		if (plz >= 1000 && plz <= 99998) {
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
				int vorher = menge;
				menge = ausl.verlust(menge);
				betrag += lagerPosten.ausleihePreis(ausl.getEndZeit() - ausl.getStartZeit(),
					vorher - menge,
					zeit - ausl.getEndZeit());
			}
			if (!ausl.istLeer())
				tmp.addFirst(ausl);
		}
		if (menge > 0)
			throw new MengenFehler(MengenFehler.Art.ZuvielRueckgeben, menge);
		ausleihe = tmp;
		Verleih verl = new Verleih(lagerPosten, gesamtMenge, betrag);
		geschlosseneRechnungspunkte.addFirst(verl);
		return verl;
	}

	public Verlust verlustMelden(LagerPosten lagerPosten, int zeit, int menge) {
		int pos = ausleihe.size();
		Ausleihe[] rev = new Ausleihe[pos];
		for (Ausleihe ausl: ausleihe)
			rev[--pos] = ausl;
		LinkedList<Ausleihe> tmp = new LinkedList<Ausleihe>();
		int gesamtMenge = menge;
		int betrag = 0;
		for (Ausleihe ausl: rev) {
			if (ausl.getPosten() == lagerPosten) {
				int vorher = menge;
				menge = ausl.verlust(menge);
				betrag += lagerPosten.verlustGebuehr(ausl.getEndZeit() - ausl.getStartZeit(),
					vorher - menge,
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

	public RechnungsPosten[] abrechnung() {
		LinkedList<RechnungsPosten> tmp = offeneRechnungspunkte;
		geschlosseneRechnungspunkte.addAll(0, tmp);
		offeneRechnungspunkte = new LinkedList<>();
		return Arrays.copyOf(tmp.toArray(), tmp.size(), RechnungsPosten[].class);
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

	public void kaufen(Artikel artikel, int menge) {
		if (!artikel.istVerkaeuflich())
			throw new ArtikelFehler(artikel, ArtikelFehler.Art.NichtVerkaeuflich);
		if (menge < 0)
			throw new MengenFehler(MengenFehler.Art.NegativKaufen, menge);
		if (artikel instanceof LagerPosten)
			((LagerPosten)artikel).bestandAendern(-menge);
		offeneRechnungspunkte.addFirst(new Verkauf(menge, artikel));
	}
	
	@Override
	public String toString(){
		return this.name + ", " + this.vorName;
	}
}
