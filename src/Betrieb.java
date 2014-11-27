import java.util.HashMap;
import java.util.Scanner;

public class Betrieb {
	private long zeit;
	private Kunde aktuellerKunde;
	private Artikel[] artikel;
	private HashMap<Integer, Kunde> kunden=new HashMap<>();
	private int naechsteKundenID=1;

	public Betrieb() {
		artikel = new Artikel[] {
			new LagerPosten("Test1", 1000, true, false, 100),
			new LagerPosten("Test2", 200, true, true, 300),
			new LagerPosten("Test3", 10000, false, true, 50),
			new LagerPosten("Test4", 3000, true, true, 42),
			new LagerPosten("Test5", 159900, false, false, 3),
			/*LagerPosten(String name,int verkaufspreis, int leihGebuehr, int handlingPauschale,
			int verlustGebuehr, boolean verkaeuflich, boolean verleihbar,
			int bestand)*/
			new LagerPosten("Bierglas", 160,80,20,100, true, true, 1250),
			new LagerPosten("Pinneken", 140,100,20,50, true, true, 1490),
			new LagerPosten("Dose Lauwarme Cervisia", 200,0,0,0, true, false, 520),
			new LagerPosten("Faß Lauwarme Cervisia", 5000,0,0,0, true, false, 10),
			new LagerPosten("kleine Zapfanlage",20900,8000,900,9000, true,true,18),
			new LagerPosten("große Zapfanlage",0,22000,2900,80000, false, true, 3),
			new LagerPosten("10er Bierdeckel",15,0,0,0, true, false, 3900),
			new LagerPosten("Toilettenwagen",0,8900,29900,0, false, true, 6),
			new DienstLeistung("Frischwasserinstallation",18500)
		};
		kundeHinzufuegen(new Kunde("Mertens", "Robert"));
	}
	private void kundeHinzufuegen(Kunde kunde)
	{
		kunden.put(naechsteKundenID++, kunde);
		aktuellerKunde=kunde;
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
		int index = 0;
		System.out.printf("%5s  %-20s\n", "ID", "Produktname");
		for (Artikel art : artikel) {
			index++;
			if (verk && !art.istVerkaeuflich())
				continue;
			if (verl && !art.istVerleihbar())
				continue;
			if (verf && !art.istVerfuegbar())
				continue;
			System.out.printf("%5s  %-20s\n", index - 1, art.bestandString());
		}
	}

	private void datenAendern(int pKundenID) {
		
	}

	private void kundeWechseln() {
		//fragt kunde nach ID , überprüft und setzt
		//aktuellerKunde = kunde;
		//System.out.println("Geben Sie ihre ID ein");
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
		int eingabeProdukt;
		do {
			System.out.print("Welches Produkt möchten Sie kaufen? "); // Index
																		// bei
																		// Bestandaufslistung
																		// fehlt.
			eingabeProdukt = scanner.nextInt();
			if (!artikel[eingabeProdukt].istVerkaeuflich())
				System.out.println("Dieses Produkt ist leider nicht käuflich.");
		} while (!artikel[eingabeProdukt].istVerkaeuflich());
		System.out.print("Aktuell sind "
				+ artikel[eingabeProdukt].bestandString()
				+ " in unserem Lager, wie viele davon möchten Sie erwerben? ");
		int eingabeAnzahl;
		int bestand = artikel[eingabeProdukt].getBestand();
		do {
			eingabeAnzahl = scanner.nextInt();
			if(eingabeAnzahl > bestand) System.out.println("So viel haben wir nicht. Bitte neue Eingabe: ");
			else System.out.println("Sie kaufen " + eingabeAnzahl + " x "
					+ artikel[eingabeProdukt].name + ".");
		} while (eingabeAnzahl > bestand);
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
