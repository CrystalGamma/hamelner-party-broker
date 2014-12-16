/**
 * Die Klasse RechnungsPosten liefert das abstrakte Gerüst
 * für die drei unterschiedlichen Arten von RechnungsPosten:
 * Verkauf, Verleih und Verlust.
 */
public abstract class RechnungsPosten {
	protected int menge;
	protected int betrag;
	protected Artikel artikel;
	
	public abstract String toString();
	public int getUmsatz()
	{
		return betrag;
	}
	/**
	 * Prüft ob ein Artikel sofort fällig ist, also eine DienstLeistung ist.
	 * 
	 * @return Ist der Artikel der Instanz eine DienstLeistung?
	 */
	public boolean sofortFaellig()
	{
		return artikel instanceof DienstLeistung;
	}
}
