public class DienstLeistung extends Artikel {
	public DienstLeistung(String pName, int pPreis){
		name = pName;
		handlingPauschale = pPreis;
	}

	public int kaufPreis(int menge) {
		return menge * handlingPauschale;
	}
	
	public boolean istVerkaeuflich() {
		return false;
	}
	
	public boolean istVerleihbar() {
		return false;
	}
	
	public String bestandString() {
		return this.name + ": " + Services.geldString(this.handlingPauschale);
	}

	@Override
	public boolean istVerfuegbar() {
		return false;
	}

	@Override
	public int getBestand() {
		return 0; // unendlich
	}
}