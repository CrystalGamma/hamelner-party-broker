package Rechnung;

public abstract class RechnungsPosten {
	protected int menge;
	protected int betrag;
	protected Artikel artikel;
	
	public abstract String toString();
	public int getUmsatz()
	{
		return betrag;
	}
	public boolean sofortFaellig()
	{
		return artikel instanceof DienstLeistung;
	}
}
