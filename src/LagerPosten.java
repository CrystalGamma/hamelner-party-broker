public class LagerPosten extends Artikel {
	String name;
	int bestand;
	boolean verkäuflich, verleihbar;

	public LagerPosten(String name, ) {

	}
	public String toString() {
		return "";
	}
	public int kaufPreis(int menge) {
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
