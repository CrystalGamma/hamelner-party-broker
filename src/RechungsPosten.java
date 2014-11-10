public abstract class RechungsPosten {
	private long zeit;
	protected int menge;
	protected double betrag;
	protected Artikel artikel;
	
	public abstract String toString();
}
