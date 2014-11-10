public class LagerPosten extends Artikel {
	public String toString() {
		return "";
	}
	public double kaufPreis(int menge) {
		return 0;
	}
	public boolean istVerkaeuflich() {
		return false;
	}
	public boolean istVerleihbar() {
		return false;
	}
	public void bestandAendern(int menge) {
	}
	public int getBestand() {
		return 0;
	}
	public int ausleihePreis(int zeitGeplant, int menge, int zeitDelta) {
		return 0;
	}
	public int verlustGebuehr(int zeitGeplant, int menge, int zeitDelta) {
		return 0;
	}
}
