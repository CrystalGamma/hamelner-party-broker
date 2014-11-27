public abstract class Artikel {
	protected String name;
	protected int handlingPauschale;
	
	public String toString() {
		return name;
	}
	public abstract int getBestand();
	public abstract String bestandString();
	public abstract boolean istVerkaeuflich();
	public abstract boolean istVerleihbar();
	public abstract boolean istVerfuegbar();
	public abstract int kaufPreis(int menge);
	public void setHandlingPauschale(int p) {
		handlingPauschale = p;
	}
}