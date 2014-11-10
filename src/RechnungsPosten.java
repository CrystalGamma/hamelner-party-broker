public abstract class RechnungsPosten {
	private long zeit;
	protected int menge;
	protected int betrag;
	protected Artikel artikel;
	
	public abstract String toString();
}
