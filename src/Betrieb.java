import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Betrieb {
	private int zeit;
	private Kunde aktuellerKunde;
	private Artikel[] artikel;
	private HashMap<Integer, Kunde> kunden = new HashMap<>();
	private int naechsteKundenID = 1;

	public Betrieb() {
		artikel = new Artikel[] {
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

	private void kundeHinzufuegen() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bitte machen Sie folgende Angaben");
		System.out.print("Nachname: ");
		String nachname = scanner.nextLine();
		System.out.print("Vorname: ");
		String vorname = scanner.nextLine();
		
		Kunde kunde = new Kunde(nachname, vorname);
		kundeHinzufuegen(kunde);
	}
	
	private void kundeHinzufuegen(Kunde kunde) {
		kunden.put(naechsteKundenID++, kunde);
		aktuellerKunde = kunde;
		System.out.println("Kunde " + kunde + " erfolgreich erstellt. Seine ID ist " + (naechsteKundenID-1) + ".");
	}

	private void abrechnung() {
		LinkedList<RechnungsPosten> posten = aktuellerKunde.abrechnung();
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
		int len = Math.max((int) Math.ceil(Math.log10(artikel.length)), 2);
		System.out.printf("%" + len + "s Artikel\n", "ID");
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

	private void datenAendern() {
		System.out.println("Was möchten Sie ändern");
		System.out.println("1 :komplette Anschrift ändern");
		System.out.println("2 :Straße und Hausnummer ändern");
		System.out.println("3 :Abbrechen");
		int eingabeID;
		
		Scanner scanner=new Scanner(System.in);
		while (true) {
			try {
				eingabeID=scanner.nextInt();

				if(eingabeID == 1 || eingabeID == 2 || eingabeID == 3)
					break;
				else
					System.out.println("Die Option gab es nicht");
					
			} catch (InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scanner.nextLine();
			}
		}
		
		int eingabeInt;
		String eingabe;
		while (true) {
			if (eingabeID==3)
				break;
			if (eingabeID==1) {
				System.out.println("Geben Sie ihren Ort ein");
				eingabe = scanner.next();
				aktuellerKunde.setOrt(eingabe);
				System.out.println("Geben Sie Ihre Plz ein");
				try {
					eingabeInt=scanner.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Fehler in der Eingabe! Es war keine Zahl");
					scanner.nextLine();
					continue;
				}
				if (eingabeInt >= 1000 && eingabeInt <= 99998) {
					aktuellerKunde.setPlz(eingabeInt);
					eingabeID=2;
				} else {
					System.out.println("Plz sind immer 5-Stellig");
				}
			}
			if (eingabeID==2) {
				System.out.println("Geben Sie ihre Straße ein");
				eingabe = scanner.next();
				aktuellerKunde.setStrasse(eingabe);
				System.out.println("Geben Sie Ihre Hausnummer ein");
				try {
					eingabeInt=scanner.nextInt();
					aktuellerKunde.setHausnummer(eingabeInt);
					break;
				} catch(InputMismatchException e) {
					System.out.println("Fehler in der Eingabe! Es war keine Zahl");
					scanner.nextLine();
				}
			} else {
				System.out.println("Nur 1 oder 2 stehen zur Verfügung");
			}
		}
		
	}

	private void kundeWechseln() {
		// fragt kunde nach ID , überprüft und setzt
		if (kunden.isEmpty())
			throw new Error("Es existiert kein Kunde");

		System.out.println("Geben Sie ihre ID ein");
		Scanner scannerID = new Scanner(System.in);

		int schluessel;
		while (true) {
			try {
				schluessel = scannerID.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scannerID.nextLine();
				continue;
			}

			if (schluessel > 0 && schluessel < naechsteKundenID) {
				aktuellerKunde = kunden.get(schluessel);
				break;
			} else {
				System.out.println("Kunden-ID ist ungültig. Bitte um erneute Eingabe: ");
			}
		}

	}

	private void rueckgabe() {
		// TODO: deduplizieren
		int index = 0;
		System.out.printf("%5s  %-20s\n", "ID", "Produktname");
		for (Artikel art : artikel) {
			index++;	
			if (!art.istVerleihbar())
				continue;
			System.out.printf("%5s  %-20s\n", index-1 , art.bestandString());	
		}
		int schluesselID;
		int menge;
		Scanner scannerID=new Scanner(System.in);
		while(true)
		{
			System.out.println("Bitte ID des zurückzugebenen Artikels eingeben");
			try {
				schluesselID = scannerID.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scannerID.nextLine();
				continue;
			}
			if (schluesselID < 0 || schluesselID >= artikel.length
					|| !artikel[schluesselID].istVerleihbar())
				System.out.println("Diese Option ist nicht verfügbar");
			else
				break;
		}
		System.out.println("Bitte eine Menge eingeben");
		while (true) {
			try {
				menge=scannerID.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scannerID.nextLine();
			}
		}
		System.out.println("Bitte bezahlen: " +
				aktuellerKunde.rueckgabe((LagerPosten) artikel[schluesselID],
						this.zeit, menge).toString());
	}

	private void transaktionen() {
		for (String str : aktuellerKunde.getTransaktionen()) {
			System.out.println(str);
		}
	}

	private void umsatzBericht() {
		for (Kunde kunde : kunden.values()) {
			System.out.println(kunde + ": " + kunde.berechneUmsatz());
		}
	}

	private void verkaufen() {
		Scanner scanner = new Scanner(System.in);

		// Gewünschtes Produkt erfragen
		System.out.println(
				"Zum Verkauf stehen derzeit folgende Produkte zur Verfügung:");
		this.bestandAuflisten(true, false, true);
		int eingabeProdukt;
		Artikel gewaehltesProdukt = null;
		try {
			do {
				System.out.print("Welches Produkt möchten Sie kaufen? ");
				eingabeProdukt = scanner.nextInt();
				if(eingabeProdukt < artikel.length) {
					gewaehltesProdukt = artikel[eingabeProdukt];
					if (!gewaehltesProdukt.istVerkaeuflich())
						System.out.println("Dieses Produkt ist leider nicht käuflich.");
				} else {
					System.out.println("Produktnummer ungültig.");
				}
			} while (gewaehltesProdukt == null || !gewaehltesProdukt.istVerkaeuflich());
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Produktnummer ungültig.");
		} catch(InputMismatchException e) {
			System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
		}
		
		// Gewünschte Anzahl erfragen
		System.out.print("Aktuell sind " + gewaehltesProdukt.bestandString()
				+ " in unserem Lager, wie viele davon möchten Sie erwerben? ");
		int eingabeAnzahl = scanner.nextInt();
		
		
		System.out.println(eingabeAnzahl + " x " + gewaehltesProdukt.name + " an " + aktuellerKunde + " verkaufen? Die Kosten für den Kunden betragen €" + gewaehltesProdukt.kaufPreis(eingabeAnzahl) + ".\nBestätigen mit [j], sonst beliebige Taste drücken ");
		String weiter = scanner.next();
		if (weiter.equals("j")) {
			// Kauf abwickeln
			aktuellerKunde.kaufen(gewaehltesProdukt, eingabeAnzahl);
			System.out.println("Es wurden " + eingabeAnzahl + " x "
					+ gewaehltesProdukt.name + " an " + aktuellerKunde + " verkauft.");
		} else {
			System.out.println("Verkauf abgebrochen.");
		}
	}

	private void verleih() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(
				"Zum Verleih stehen derzeit folgende Produkte zur Verfügung:");
		// TODO: deduplizieren
		int index = 0;
		System.out.printf("%5s  %-20s\n", "ID", "Produktname");
		for (Artikel art : artikel) {
			index++;
			if (!art.istVerleihbar() || !art.istVerfuegbar())
				continue;
			System.out.printf("%5s  %-20s\n", index-1 , art.bestandString());
		}
		int eingabeProdukt;
		Artikel gewaehltesProdukt = null;
		while(true) {
			System.out.print("Welches Produkt möchten Sie ausleihen? ");
			try {
				eingabeProdukt = scanner.nextInt();
				gewaehltesProdukt = artikel[eingabeProdukt];
				if (!gewaehltesProdukt.istVerleihbar())
					System.out.println(
							"Dieses Produkt steht aktuell leider nicht zum Ausleihen zur Verfügung.");
				else{
					break;
				}
			} catch(InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scanner.nextLine();
			}
		}

		// Gewünschte Anzahl erfragen
		System.out.print("Aktuell sind " + gewaehltesProdukt.getBestand()
				+ " in unserem Lager, wie viele davon möchten Sie ausleihen? ");
		int eingabeAnzahl = scanner.nextInt();
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
		Ausleihe neueAusleihe = new Ausleihe(zeit, zeit + eingabeTage * 24,
				(LagerPosten) gewaehltesProdukt, eingabeAnzahl);
		aktuellerKunde.ausleihe(neueAusleihe);
		System.out.println("Es wurden " + eingabeAnzahl + " x "
				+ gewaehltesProdukt.name + " an " + aktuellerKunde + " ausgeliehen.");
	}

	private void verlust() {
		int schluesselID;
		int menge;
		Scanner scannerID=new Scanner(System.in);
		while (true) {
			System.out.println("Bitte ID des verlorenen Artikels eingeben");
			try {
				schluesselID=scannerID.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scannerID.nextLine();
				continue;
			}


			if (schluesselID < 0 || schluesselID >= artikel.length
					|| !artikel[schluesselID].istVerleihbar())
				System.out.println("Diese Option ist nicht verfügbar");
			else
				break;
		}
		System.out.println("Bitte eine Menge eingeben");
		while(true)	{
			try {
				menge=scannerID.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.out.println("Fehler in der Eingabe! Es war keine Zahl");
				scannerID.nextLine();
			}
		}
		System.out.println("Bitte bezahlen: " + aktuellerKunde.verlustMelden(
				(LagerPosten) artikel[schluesselID], this.zeit, menge).toString());
	}

	public static void main(String[] args) {
		Betrieb betr = new Betrieb();

		String[] aktionen = new String[] {
				"Status / mögliche Befehle",
				"Auflistung der aktuell verfügbaren verkäuflichen Gegenstände",
				"Auflistung der aktuell verfügbaren Leihgegenstände",
				"Auflistung des gesamten verkäuflichen Bestands",
				"Verkauf eines verfügbaren Objekts",
				"Auflistung des gesamten verleihbaren Bestands",
				"Ausleihe eines verfügbaren Objekts für eine prognostizierte Zeitdauer",
				"Rückgabe eines entliehenen Objekts",
				"Verlustmeldung eines entliehenen Objekts",
				"Abrechnung eines Kunden",
				"Auflistung aller Kunden mit ihren Umsätzen",
				"Auflistung der mit einem Kunden durchgeführten Transaktionen",
				"Neuen Kunden anlegen",
				"Ändern von Kundendaten",
				"Aktuellen Kunden wechseln",
				"An der Uhr drehen",
				"Beenden" };
		boolean run = true;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Für Befehlsliste 0 eingeben");
		do {
			int aktion = 0;
			while (true) {
				System.out.print("Befehl> ");
				System.out.flush();
				try{
					aktion = scanner.nextInt();
				} catch(InputMismatchException e) {
					scanner.nextLine();
				}
				if (aktion < 0)
					aktion = 0;	// wenn der nutzer schwachsinn eingibt, liste anzeigen
				if (aktion >= 0 && aktion < aktionen.length)
					break;
			}

			System.out.println(aktionen[aktion]);
			try {
				switch (aktion) {
					case 0:
						System.out.println("Aktueller Kunde: " + betr.aktuellerKunde);
						System.out.println("Bitte Aktion wählen:");
						int aktionsID = 0;
						for (String aktionsBeschreibung : aktionen) {
							System.out.printf("%4s %s\n", "[" + aktionsID + "]",
									aktionsBeschreibung);
							aktionsID++;
						}
						break;
					case 1:
						betr.bestandAuflisten(true, false, true);
						break;
					case 2:
						betr.bestandAuflisten(false, true, true);
						break;
					case 3:
						betr.bestandAuflisten(true, false, false);
						break;
					case 4:
						betr.verkaufen();
						break;
					case 5:
						betr.bestandAuflisten(false, true, false);
						break;
					case 6:
						betr.verleih();
						break;
					case 7:
						betr.rueckgabe();
						break;
					case 8:
						betr.verlust();
						break;
					case 9:
						betr.abrechnung();
						break;
					case 10:
						betr.umsatzBericht();
						break;
					case 11:
						betr.transaktionen();
						break;
					case 12:
						betr.kundeHinzufuegen();
						break;
					case 13:
						betr.datenAendern();
						break;
					case 14:
						betr.kundeWechseln();
						break;
					case 15:
						System.out.println("Wie lange (Stunden)?");
						betr.anDerUhrDrehen(scanner.nextInt());
						break;
					case 16:
						run = false;
						break;
					default:
				}
			} catch (InputMismatchException e) {
				System.out.println("FEHLER: unerwartete Eingabe");
			} catch(Throwable e) {
				System.out.println("FEHLER: " + e);
			}
		} while (run);
	}
}
