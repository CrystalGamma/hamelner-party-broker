public class DienstLeistung extends Artikel {
	public DienstLeistung(String pName, double pPreis){
		name = pName;
		handlingPauschale = pPreis;
	}

	public double kaufPreis(int menge) {
		return menge * handlingPauschale;
	}
	
	public boolean istVerkaeuflich() {
		return false;
	}
	
	public boolean istVerleihbar() {
		return false;
	}
	
	public String toString() {
		return "Die Dienstleistung " + this.name + " kostet " + this.handlingPauschale + "€.";
	}
}