import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Betrieb {
	private int zeit;
	private Kunde aktuellerKunde;
	private Artikel[] artikel;
	private HashMap<Integer, Kunde> kunden = new HashMap<>();
	private int naechsteKundenID = 1;

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

	private void kundeHinzufuegen(Kunde kunde) {
		kunden.put(naechsteKundenID++, kunde);
		aktuellerKunde = kunde;
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
		int len = Math.max((int)Math.ceil(Math.log10(artikel.length)), 2);
		System.out.printf("%" + len + "s Artikel", "ID");
		String format = "%" + len + "s %s\n";
		for (int index = 0; index < artikel.length; index++) {
			Artikel art = artikel[index];
			if (verk && !art.istVerkaeuflich())
				continue;
			if (verl && !art.istVerleihbar())
				continue;
			if (verf && !art.istVerfuegbar())
				continue;
			System.out.printf(format, index, art.bestandString());
		}
	}

	private void datenAendern(int pKundenID) {
		
	}

	private void kundeWechseln() {
		//fragt kunde nach ID , überprüft und setzt
		if(kunden.isEmpty())
			throw new Error("Es existiert kein Kunde");
		
			
		System.out.println("Geben Sie ihre ID ein");
		Scanner scannerID=new Scanner(System.in);
		
		int schluessel;
		while(true)
		{	
			try
			{
				schluessel=scannerID.nextInt();
			}
			catch(InputMismatchException e)
			{
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scannerID.nextLine();
				continue;
			}
		
		
			if(schluessel>=1)
			{
				aktuellerKunde=kunden.get(schluessel);
				break;
			}
			else
			{
				System.out.println("ID's beginnen bei 1");
			}
		}
		
	}

	private void rueckgabe() {

	}

	private void transaktionen() {
		for (String str: aktuellerKunde.getTransaktionen()) {
			System.out.println(str);
		}
	}

	private void umsatzBericht() {
		for (Kunde kunde: kunden.values()) {
			System.out.println(kunde.getVorName() + ", " + kunde.getName()
					+ ": "+ kunde.berechneUmsatz());
		}
	}

	private void verkaufen() { // TODO Exception-Handling
		Scanner scanner = new Scanner(System.in);

		// Gewünschtes Produkt erfragen
		System.out
				.println("Zum Verkauf stehen derzeit folgende Produkte zur Verfügung:");
		this.bestandAuflisten(true, false, true);
		int eingabeProdukt;
		Artikel gewaehltesProdukt = null;
		do {
			System.out.print("Welches Produkt möchten Sie kaufen? ");
			eingabeProdukt = scanner.nextInt();
			gewaehltesProdukt = artikel[eingabeProdukt];
			if (!gewaehltesProdukt.istVerkaeuflich())
				System.out.println("Dieses Produkt ist leider nicht käuflich.");
		} while (!gewaehltesProdukt.istVerkaeuflich());

		// Gewünschte Anzahl erfragen
		System.out.print("Aktuell sind " + gewaehltesProdukt.bestandString()
				+ " in unserem Lager, wie viele davon möchten Sie erwerben? ");
		int eingabeAnzahl;
		int bestand = gewaehltesProdukt.getBestand();
		do {
			eingabeAnzahl = scanner.nextInt();
			if (eingabeAnzahl > bestand)
				System.out
						.println("So viel haben wir nicht. Bitte neue Eingabe: ");
			else
				System.out.println("Sie kaufen " + eingabeAnzahl + " x "
						+ gewaehltesProdukt.name + ".");
		} while (eingabeAnzahl > bestand);

		// Kauf abwickeln
		aktuellerKunde.kaufen(gewaehltesProdukt, eingabeAnzahl);
	}

	private void verleih() { // TODO Exception-Handling
		Scanner scanner = new Scanner(System.in);

		// Gewünschtes Produkt erfragen
		// TODO Bestand auflisten zeigt aktuell nur den Verkaufspreis, Unterscheidung notwendig.
		System.out
				.println("Zum Verleih stehen derzeit folgende Produkte zur Verfügung:");
		this.bestandAuflisten(false, true, true);
		int eingabeProdukt;
		Artikel gewaehltesProdukt = null;
		do {
			System.out.print("Welches Produkt möchten Sie ausleihen? ");
			eingabeProdukt = scanner.nextInt();
			gewaehltesProdukt = artikel[eingabeProdukt];
			if (!gewaehltesProdukt.istVerleihbar())
				System.out
						.println("Dieses Produkt steht aktuell leider nicht zum Ausleihen zur Verfügung.");
		} while (!gewaehltesProdukt.istVerleihbar());

		// Gewünschte Anzahl erfragen
		System.out.print("Aktuell sind " + gewaehltesProdukt.bestandString()
				+ " in unserem Lager, wie viele davon möchten Sie ausleihen? ");
		int eingabeAnzahl;
		int bestand = gewaehltesProdukt.getBestand();
		do {
			eingabeAnzahl = scanner.nextInt();
			if (eingabeAnzahl > bestand)
				System.out
						.print("So viel haben wir nicht. Bitte neue Eingabe: ");
			else
				System.out.println("Sie sind dabei sich " + eingabeAnzahl + " x "
						+ gewaehltesProdukt.name + " auszuleihen.");
		} while (eingabeAnzahl > bestand);
		
		// Zeitdauer erfragen
		System.out.print("Bitte geben Sie an für wie viel Tage Sie das Produkt ausleihen wollen: ");
		int eingabeTage = 0;
		do {
			eingabeTage = scanner.nextInt();
			if (eingabeTage <= 0)
				System.out
						.print("Ungültige Zeitangabe, bitte erneut versuchen: ");
		} while (eingabeTage <= 0);
		
		// Ausleihe abwickeln
		Ausleihe neueAusleihe = new Ausleihe(zeit, zeit+eingabeTage*24, (LagerPosten)gewaehltesProdukt, eingabeAnzahl);
		aktuellerKunde.ausleihe(neueAusleihe);
		System.out.println("Ausleihe erfolgreich abgeschlossen.");
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
	}
}
