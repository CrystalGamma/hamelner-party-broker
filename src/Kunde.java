public class Kunde {
	String name, vorName, strasse, ort;
	int id, hausnummer, plz, umsatz;//id muss noch gesetzt werden mit static als global
	Ausleihe ausleihe[]=new Ausleihe[10];//maximal 10 ausleihen
	int ausleihNummer;
	RechnungsPosten[] offeneRechnungspunkte=new RechnungsPosten[10];
	RechnungsPosten[] geschlosseneRechnungspunkte=new RechnungsPosten[10];
	public Kunde(String name, String vorName)
	{
		ausleihNummer=0;
		this.name=name;
		this.vorName=vorName;
	}
	public void setStrasse(String strasse) {

		this.strasse=strasse;
	}

	public void setHausnummer(int hausnummer) {
//b oder a zusatz muss abgefangen werden
		if(hausnummer>0)
		{
		this.hausnummer=hausnummer;
		}
		else
		{
			throw new Error("Negative oder 0 Hausnummern gibt es nicht");
		}
	}

	public void setPlz(int plz) {
		if(plz >= 10000 && plz >= 99999)
		{
		this.plz=plz;
		}
		else
		{
			throw new Error("Plz ist immer 5-stellig");
		}
	}

	public void setOrt(String ort) {

		this.ort=ort;
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

	public String getStrasse()
	{
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
		
		
	}

	public void verlustmelden(LagerPosten LagerPosten, int zeit, int menge) {

	}

	public void ausleihe(Ausleihe ausleihe) {

		this.ausleihe[ausleihNummer]=ausleihe;
		ausleihe.buchen();
		ausleihNummer++;
	}

	public RechnungsPosten[] abrechnung() {
		//noch nicht fertig
		RechnungsPosten[] RechnungspunkteTemp= new RechnungsPosten[10];
		return RechnungspunkteTemp;//liefert die bis zu diesem zeitpunkt noch offene Rechnungspunkte zurück und schiebt diese von offen nach geschlossen
	}

	public int berechneUmsatz() {
		return umsatz;
	}

	public String getTransaktionen() {
		return null;
	}

	public void kaufen(Artikel Artikel, int menge) {

	}
}
