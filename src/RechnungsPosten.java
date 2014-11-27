public abstract class RechnungsPosten {
	// TODO: int
	private long zeit;
	protected int menge;
	protected int betrag;
	protected Artikel artikel;
	
	public abstract String toString();
	public int getUmsatz()
	{
		return betrag;
	}
}
