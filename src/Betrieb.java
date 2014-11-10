public class Betrieb {
	private long zeit;
	private Kunde aktuellerKunde;
	private Kunde[] kunden;
	private Artikel[] artikel;

	public Betrieb() {
		artikel = new Artikel[] {
			new LagerPosten("Test", 1000, true, false, 100)
		};
	}

	private void abrechnung(int pKundenID) {
	}
	
	private void anDerUhrDrehen(int zeit){
		if(zeit < 0)
			throw new Error("Zeit kann nicht rückwärts gehen");
		this.zeit += zeit;
	}

	private void bestandAuflisten(boolean verk, boolean verl, boolean verf) {
		for (Artikel art : artikel) {
			if (verk && !art.istVerkaeuflich())
				continue;
			if (verl && !art.istVerleihbar())
				continue;
			// TODO: verfügbar
			System.out.println(art);
		}
	}

	private void datenAendern(int pKundenID) {

	}
	
	private void kundeWechseln(){
		
	}

	private void rueckgabe() {

	}
	
	private String transaktionen(int pKundenID){
		return "";
	}

	private String umsatzBericht() {
		return "";
	}

	private void verkaufen() {

	}

	private void verleih() {

	}

	private void verlust() {

	}

	public static void main(String[] args) {
		Betrieb betr = new Betrieb();
		betr.bestandAuflisten(false, true, false);
	}
}