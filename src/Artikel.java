public abstract class Artikel {
	public abstract String toString();
	public abstract boolean istVerkaeuflich();
	public abstract boolean istVerleihbar();
	public abstract int kaufPreis(int menge);
}