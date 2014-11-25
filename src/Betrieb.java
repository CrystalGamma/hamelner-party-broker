import java.util.Scanner;

public class Betrieb {
	private long zeit;
	private Kunde aktuellerKunde;
	private Kunde[] kunden;
	private Artikel[] artikel;

	public Betrieb() {
		artikel = new Artikel[] {
				new LagerPosten("Test1", 1000, true, false, 100),
				new LagerPosten("Test2", 200, true, true, 300),
				new LagerPosten("Test3", 10000, false, true, 50),
				new LagerPosten("Test4", 3000, true, true, 42),
				new LagerPosten("Test5", 159900, false, false, 3) };

		kunden = new Kunde[] { new Kunde("Mertens", "Robert") };
		aktuellerKunde = kunden[0];
	}

	private void abrechnung() {
		RechnungsPosten[] posten = aktuellerKunde.abrechnung();
		for (RechnungsPosten p : posten) {
			System.out.println(p);
		}
	}

	private void anDerUhrDrehen(int zeit) {
		if (zeit < 0)
			throw new Error("Zeit kann nicht rückwärts gehen");
		this.zeit += zeit;
	}

	private void bestandAuflisten(boolean verk, boolean verl, boolean verf) {
		for (Artikel art : artikel) {
			if (verk && !art.istVerkaeuflich())
				continue;
			if (verl && !art.istVerleihbar())
				continue;
			if (verf && !art.istVerfuegbar())
				continue;
			System.out.println("Produkt " + art.bestandString());
		}
	}

	private void datenAendern(int pKundenID) {
	}

	private void kundeWechseln(Kunde kunde) {
		aktuellerKunde = kunde;
	}

	private void rueckgabe() {

	}

	private String transaktionen(int pKundenID) {
		return "";
	}

	private String umsatzBericht() {
		return "";
	}

	private void verkaufen() {
		Scanner scanner = new Scanner(System.in);
		System.out
				.println("Zum Verkauf stehen derzeit folgende Produkte zur Verfügung:");
		this.bestandAuflisten(true, false, true);
		System.out.print("Welches Produkt möchten Sie kaufen? "); // Index bei Bestandaufslistung fehlt.
		int eingabeProdukt = scanner.nextInt();
		System.out.print("Aktuell sind " + artikel[0].bestandString()
				+ " in unserem Lager, wie viele davon möchten Sie erwerben? ");
		int eingabeAnzahl = scanner.nextInt();
		System.out.println("Sie kaufen " + eingabeAnzahl + " x "
				+ artikel[0].name + ".");

		aktuellerKunde.kaufen(artikel[eingabeProdukt], eingabeAnzahl);
	}

	private void verleih() {

	}

	private void verlust() {

	}

	public static void main(String[] args) {
		Betrieb betr = new Betrieb();
		System.out.println("verfügbar, verleihbar:");
		betr.bestandAuflisten(false, true, true);
		System.out.println("gesamt verfügbar:");
		betr.bestandAuflisten(false, false, true);
		System.out.println("Gesamtsortiment:");
		betr.bestandAuflisten(false, false, false);

		betr.verkaufen();
	}
}