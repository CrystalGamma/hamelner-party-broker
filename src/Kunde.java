public class Kunde {
	String name, vorName, strasse, ort;
	int iD, hausnummer, plz, umsatz;

	public void setName( String name) {
		
		this.name=name;
	}

	public void setVorName(String vorName) {
		
		this.vorName=vorName;
	}

	public void setStrasse(String strasse) {

		this.strasse=strasse;
	}

	public void setHausnummer(int hausnummer) {

		if(hausnummer>0)
		{
		this.hausnummer=hausnummer;
		}
		else
		{
			throw new Error();
		}
	}

	public void setPlz(int plz) {

		this.plz=plz;
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
		return iD;
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

	public void rueckgabe(LagerPosten LagerPosten, int zeit, int menge) {

	}

	public void verlustmelden(LagerPosten LagerPosten, int zeit, int menge) {

	}

	public void ausleihe(Ausleihe Ausleihe) {

	}

	public void abrechnung() {

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
