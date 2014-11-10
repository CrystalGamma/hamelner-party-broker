public abstract class Artikel {
	protected String name;
	protected double handlingPauschale;
	
	public abstract String toString();
	public abstract boolean istVerkaeuflich();
	public abstract boolean istVerleihbar();
	public abstract int kaufPreis(int menge);
}