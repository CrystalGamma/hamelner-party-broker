public class Betrieb {
	private long zeit;
	private Kunde aktuellerKunde;
	private Kunde[] kunden;
	private Artikel[] artikel;

	public Betrieb() {

	}

	private String abrechung(int pKundenID) {
		return "";
	}
	
	private void anDerUhrDrehen(int pZeit){
		zeit += pZeit;
	}

	private String bestandAuflisten(int pModus) {
		return "";
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
		new Betrieb();
	}
}