public class DienstLeistung extends Artikel {
	public DienstLeistung() {

	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public boolean istVerkaeuflich() {
		return false;
	}

	@Override
	public boolean istVerleihbar() {
		return false;
	}

	@Override
	public int kaufPreis(int menge) {
		return 0;
	}
}
