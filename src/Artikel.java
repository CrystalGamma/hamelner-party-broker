public abstract class Artikel {
	protected String name;
	protected int handlingPauschale;
	
	public abstract String toString();
	public abstract boolean istVerkaeuflich();
	public abstract boolean istVerleihbar();
	public abstract boolean istVerfuegbar();
	public abstract int kaufPreis(int menge);
}