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
		return this.name + ": " + this.handlingPauschale + "€";
	}

	@Override
	public boolean istVerfuegbar() {
		return false;
	}
}