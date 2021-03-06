import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Der Betrieb verwaltet einen Pool von Kunden und
 * organisiert alle Tätigkeiten in Verbindung mit den
 * Artikeln des Betriebes.
 */
public class Betrieb {
	private int zeit;
	private Kunde aktuellerKunde;
	private Artikel[] artikel;
	private HashMap<Integer, Kunde> kunden = new HashMap<>();
	private int naechsteKundenID = 1;

	/**
	 * Erzeugt eine Instanz der Klasse Betrieb und baut einen Basisbestand von Artikeln auf.
	 */
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
			new LagerPosten("Lautsprecher",0,3000,1000,30000,false,true,30),
			new LagerPosten("Monitor",0,1500,1000,15000,false,true,25),
			new LagerPosten("Mixpult",0,4000,1500,400000,false,true,10),
			new LagerPosten("Verstärker",0,3000,1250,3500,false,true,10),
			new LagerPosten("Nebelmaschiene",0,2000,1200,25000,false, true, 15),
			new DienstLeistung("Frischwasserinstallation",18500),
			new DienstLeistung("Verkabelung",15000),
			new DienstLeistung("Hallenreinigung bis 100 qm",450000),
		};
	}
	
	/**
	 * Diese Methode fügt der  HashMap einen weiteren Kunden hinzu
	 * 
	 *  Leon Westhof
	 *  */
	private void kundeHinzufuegen() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bitte machen Sie folgende Angaben");
		System.out.print("Nachname: ");
		String nachname = scanner.nextLine();
		System.out.print("Vorname: ");
		String vorname = scanner.nextLine();
		System.out.print("Straße: ");
		String strasse = scanner.nextLine();
		System.out.print("Hausnummer: ");
		int hausnummer = scanner.nextInt();
		System.out.print("Postleitzahl: ");
		int plz = scanner.nextInt();
		System.out.print("Ort: ");
		scanner.nextLine();
		String ort = scanner.nextLine();

		nachname = nachname.trim();
		vorname = vorname.trim();
		strasse = strasse.trim();
		ort = ort.trim();

		if (nachname.isEmpty() || vorname.isEmpty() || strasse.isEmpty()
				|| ort.isEmpty())
			throw new Error("Eins der Felder ist leer");
		
		Kunde kunde = new Kunde(nachname, vorname, strasse, hausnummer, plz, ort);
		kundeHinzufuegen(kunde);
	}
	
	/**
	 * Diese Methode fügt der  HashMap einen weiteren Kunden hinzu
	 * 
	 * @param kunde Hinzuzufügender Kunde
	 */
	private void kundeHinzufuegen(Kunde kunde) {
		kunden.put(naechsteKundenID++, kunde);
		aktuellerKunde = kunde;
		System.out.println("Kunde " + kunde + " erfolgreich erstellt. Seine ID ist " + (naechsteKundenID-1) + ".");
	}

	/**
	 * Rechnet die offenen Transaktionen des aktuellen Kunden ab und fordert zu einer Zahlung auf.
	 */
	private void abrechnung() {
		LinkedList<RechnungsPosten> posten = aktuellerKunde.abrechnung();
		if(posten.size() > 0){
			int zuZahlen = 0;
			for (RechnungsPosten p : posten) {
				zuZahlen += p.betrag;
				System.out.println(p);
			}
			System.out.println("Bitte bezahlen: " + Services.geldString(zuZahlen) + ".");
		}else{
			System.err.println("Der aktuelle Kunde " + aktuellerKunde + " hat keine offenen Transaktionen.");
		}
	}

	/**
	 * Ermöglicht das stundenweise vorstellen der Uhrzeit 
	 * 
	 * @param zeit Vorzustellende Zeit in Stunden
	 */
	private void anDerUhrDrehen(int zeit) {
		if(zeit < 0) System.err.println("Zeit kann nicht rückwärts gehen.");
		else {
			this.zeit += zeit;
			System.out.println("Die Uhrzeit wurde um " + zeit + " Stunden vorgestellt.");
		}
	}

	/**
	 * Auflistung des verfügbaren Bestands mit ID und Produktnamen.
	 * 
	 * @param verk Sollen verkäufliche Produkte mit aufgeführt werden?
	 * @param verl Sollen verleihbare Produkte mit aufgeführt werden?
	 */
	private void bestandAuflisten(boolean verk, boolean verl) {
		int len = Math.max((int) Math.ceil(Math.log10(artikel.length)), 2);
		System.out.printf("%" + len + "s Artikel\n", "ID");
		String format = "%" + len + "s %s\n";
		for (int index = 0; index < artikel.length; index++) {
			Artikel art = artikel[index];
			if (verk && !art.istVerkaeuflich())
				continue;
			if (verl && !art.istVerleihbar())
				continue;
			if (!art.istVerfuegbar())
				continue;
			System.out.printf(format, index, art.bestandString());
		}
	}
	/**
	 * Diese Methode diehnt dazu die Addresse des Kunden zu ändern oder dessen Nachnamen.
	 * Man hat die Möglichlichkeit zwischen straße und ahsnummer oder kompletter anschrift zu wählen. Nach Auswahl gibt ein bei falscheingabe eine Fehlermeldung und eine Neueingabe. Bei richtiger auswhl kann man dann seine entsprechenden daten ändern. Fahlermeldungen gibt es bei unssinigen Angaben zu Zahlenfragen
	 * Leon Westhof
	 *  */
	private void datenAendern() {
		System.out.println("Was möchten Sie ändern");
		System.out.println("1 :komplette Anschrift ändern");
		System.out.println("2 :Straße und Hausnummer ändern");
		System.out.println("3 :Name ändern");
		System.out.println("4 :Abbrechen");
		int eingabeID;
		
		Scanner scanner=new Scanner(System.in);
		while (true) {
			try {
				eingabeID=scanner.nextInt();

				if(eingabeID == 1 || eingabeID == 2 || eingabeID == 3|| eingabeID == 4)
					break;
				else
					System.err.println("Die Option gab es nicht");
					
			} catch (InputMismatchException e) {
				System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
				scanner.nextLine();
			}
		}
		
		int eingabeInt;
		String eingabe;
		while (true) {
			if (eingabeID==4)
				break;
			if(eingabeID==3){
				System.out.println("Geben Sie ihren Nachnamen ein");
				eingabe = scanner.next();
				aktuellerKunde.setName(eingabe);
				break;
			}
			if (eingabeID==1) {
				System.out.println("Geben Sie ihren Ort ein");
				eingabe = scanner.next();
				aktuellerKunde.setOrt(eingabe);
				System.out.println("Geben Sie Ihre Plz ein");
				try {
					eingabeInt=scanner.nextInt();
				} catch (InputMismatchException e) {
					System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
					scanner.nextLine();
					continue;
				}
				aktuellerKunde.setPlz(eingabeInt);
				eingabeID=2;
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
					System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
					scanner.nextLine();
				}
			} else {
				System.err.println("Durch die fehlerhafte Eingabe bitten wir erneut um Ihre Eingabe.");
			}
		}
		
	}
	/**
	 * Diese Methode bietet dem Anwender die Möglichkeit den Kunden zu wechseln.
	 * Leon Westhof
	 *  */
	private void kundeWechseln() {
		// fragt kunde nach ID , überprüft und setzt
		if (kunden.isEmpty())
			throw new Error("Es existiert kein Kunde");

		System.out.print("Geben Sie die Kunden-ID ein: ");
		Scanner scannerID = new Scanner(System.in);

		int schluessel;
		while (true) {
			try {
				schluessel = scannerID.nextInt();
			} catch (InputMismatchException e) {
				System.err.print("Fehler in der Eingabe! Es war keine akzeptable Zahl. Bitte um erneute Eingabe: ");
				scannerID.nextLine();
				continue;
			}

			if (schluessel > 0 && schluessel < naechsteKundenID) {
				aktuellerKunde = kunden.get(schluessel);
				System.out.println("Der aktuelle Kunde ist jetzt " + aktuellerKunde + ".");
				break;
			} else {
				System.err.print("Kunden-ID ist ungültig. Bitte um erneute Eingabe: ");
			}
		}

	}
	/**
	 * Mit dieser Methode kann ein Artikel zurückgegeben werden.
	 * Dafür wird zuerst der komplette bestand an zurückgebbaren Objekten aufgelistet. Wenn dieser eine falsche
	 * ID oder Unsinn eingibt wird er zur erneuten Eingabe aufgefordert. Wenn diese aber stimmt muss er die 
	 * Menge eingeben. nach einer bestätigungsbitte wird geprüft, welche Menge er ausgeliehen hat und ob
	 * diese mit der Rückgabe Menge übereinstimmt. Wenn ja wird sie zurückgegeben. Wenn nicht gibt es eine 
	 * Fehlermeldung.
	 * Leon Westhof
	 *  */
	private void rueckgabe() {
		// TODO: deduplizieren
		int index = 0;
		System.out.printf("%5s  %-20s\n", "ID", "Artikel");
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
				System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
				scannerID.nextLine();
				continue;
			}
			if (schluesselID < 0 || schluesselID >= artikel.length
					|| !artikel[schluesselID].istVerleihbar())
				System.err.println("Diese Option ist nicht verfügbar");
			else
				break;
		}
		System.out.println("Bitte eine Menge eingeben");
		while (true) {
			try {
				menge=scannerID.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
				scannerID.nextLine();
			}
		}
		System.out.println("Sind Sie sicher?\nBestätigen Sie mit [j],verneinen Sie mit einer beliebigen Taste");
		String weiter = scannerID.next();
		if (weiter.equals("j")) {
			System.out.println("Bitte bezahlen: " +
					aktuellerKunde.rueckgabe((LagerPosten) artikel[schluesselID],
							this.zeit, menge).toString());
			
		} else {
			System.out.println("Abgebrochen.");
		}
		
	}

	/**
	 * Listet die Transaktionen des aktuellen Kunden in der Konsole auf.
	 */
	private void transaktionen() {
		if(aktuellerKunde.getTransaktionen().length > 0){
			for (String str : aktuellerKunde.getTransaktionen()) {
				System.out.println(str);
			}
		}else{
			System.err.println("Der aktuelle Kunde " + aktuellerKunde + " hat noch keine Transaktionen abgeschlossen.");
		}
	}

	/**
	 * Listet die Umsätze aller Kunden in der Konsole auf.
	 */
	private void umsatzBericht() {
		int id = 1;
		for (Kunde kunde : kunden.values()) {
			System.out.println(id + ": " + kunde + ": "
					+ Services.geldString(kunde.berechneUmsatz()));
			id++;
		}
	}

	/**
	 * Eingabeführung zum Abschluss eines Verkaufs eines verfügbaren Objekts.
	 * 
	 * Florian Bussmann
	 */
	private void verkaufen() {
		Scanner scanner = new Scanner(System.in);

		// Gewünschtes Produkt erfragen
		System.out.println(
				"Zum Verkauf stehen derzeit folgende Produkte zur Verfügung:");
		this.bestandAuflisten(true, false);
		int eingabeProdukt;
		Artikel gewaehltesProdukt = null;
		try {
			do {
				System.out.print("Welches Produkt möchten Sie verkaufen? ");
				eingabeProdukt = scanner.nextInt();
				if(eingabeProdukt < artikel.length) {
					gewaehltesProdukt = artikel[eingabeProdukt];
					if (!gewaehltesProdukt.istVerkaeuflich())
						System.err.println("Das gewünschte Produkt ist leider nicht käuflich.");
				} else {
					System.err.println("Produktnummer ungültig.");
				}
			} while (gewaehltesProdukt == null || !gewaehltesProdukt.istVerkaeuflich());
		} catch(ArrayIndexOutOfBoundsException e) {
			scanner.nextLine();
			throw new Error("Produktnummer ungültig. ");
		}
		
		// Gewünschte Anzahl erfragen
		if (gewaehltesProdukt instanceof LagerPosten)
			System.out.print("Aktuell sind " + gewaehltesProdukt.bestandString()
				+ " in unserem Lager, wie viele davon möchten Sie an den Kunden verkaufen? ");
		else
			System.out.println("Wieviele wollen Sie buchen?");
		int eingabeAnzahl = scanner.nextInt();
		
		System.out.println(eingabeAnzahl + " x " + gewaehltesProdukt.name + " an " + aktuellerKunde + " verkaufen? Die Kosten für den Kunden betragen " + Services.geldString(gewaehltesProdukt.kaufPreis(eingabeAnzahl)) + ".\nBestätigen mit [j], sonst beliebige Taste drücken ");
		String weiter = scanner.next();
		
		if (weiter.equals("j")) {
			// Kauf abwickeln
			RechnungsPosten posten = aktuellerKunde.kaufen(gewaehltesProdukt, eingabeAnzahl, zeit);
			if (posten.sofortFaellig())
				System.out.println("Bitte bezahlen: "+posten);
			else
				System.out.println(posten);
		} else {
			System.out.println("Verkauf abgebrochen.");
		}
	}

	/**
	 * Eingabeführung zum Abschluss einer Ausleihe eines verfügbaren Objekts.
	 * 
	 * Florian Bussmann
	 */
	private void verleih() {
		Scanner scanner = new Scanner(System.in);
		System.out.println(
				"Zum Verleih stehen derzeit folgende Produkte zur Verfügung:");
		// TODO: deduplizieren
		int index = 0;
		System.out.printf("%5s  %-20s\n", "ID", "Artikel");
		for (Artikel art : artikel) {
			index++;
			if (!art.istVerleihbar() || !art.istVerfuegbar())
				continue;
			System.out.printf("%5s  %-20s\n", index-1 , art.bestandString());
		}
		int eingabeProdukt;
		Artikel gewaehltesProdukt = null;
		while(true) {
			System.out.print("Welches Produkt möchten Sie verleihen? ");
			try {
				eingabeProdukt = scanner.nextInt();
				gewaehltesProdukt = artikel[eingabeProdukt];
				if (!gewaehltesProdukt.istVerleihbar())
					System.err.println(
							"Das gewünschte Produkt ist leider nicht verleihbar.");
				else{
					break;
				}
			} catch(InputMismatchException e) {
				System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
				scanner.nextLine();
			}
		}

		// Gewünschte Anzahl erfragen
		System.out.print("Aktuell sind " + gewaehltesProdukt.getBestand()
				+ " in unserem Lager, wie viele davon möchten Sie an den Kunden verleihen? ");
		int eingabeAnzahl = scanner.nextInt();
		// Zeitdauer erfragen
		System.out.print("Wie lange soll der Artikel ausgeliehen werden? ");
		int eingabeTage = 0;
		do {
			eingabeTage = scanner.nextInt();
			if (eingabeTage <= 0)
				System.out
						.print("Ungültige Zeitangabe, bitte erneut versuchen: ");
		} while (eingabeTage <= 0);
		Ausleihe neueAusleihe = new Ausleihe(zeit, zeit + eingabeTage * 24,
				(LagerPosten) gewaehltesProdukt, eingabeAnzahl);

		System.out.println(neueAusleihe
				+ " buchen?\nBestätigen mit [j], sonst beliebige Taste drücken ");
		String weiter = scanner.next();
		if (weiter.equals("j")) {
			// Ausleihe abwickeln
			aktuellerKunde.ausleihe(neueAusleihe);
			System.out.println("Es wurden " + eingabeAnzahl + " x "
					+ gewaehltesProdukt.name + " an " + aktuellerKunde + " ausgeliehen.");
		} else {
			System.out.println("Verleih abgebrochen.");
		}
	}
	/**
	 * Mit dieser Methode wird ein verlust gemeldet.
	 * Dafür wird zuerst der komplette Bestand an zurückgebbaren Objekten aufgelistet. Wenn dieser eine falsche
	 * ID oder Unsinn eingibt wird er zur erneuten Eingabe aufgefordert. Wenn diese aber stimmt muss er die 
	 * Menge eingeben. Nach einer Bestätigungsbitte wird geprüft, welche Menge er ausgeliehen hat und ob
	 * diese mit der Rückgabe Menge übereinstimmt. Wenn ja wird sie zurückgegeben. Wenn nicht gibt es eine 
	 * Fehlermeldung.
	 * Leon Westhof
	 *  */
	private void verlust() {
		int schluesselID;
		int menge;
		int index = 0;
		System.out.printf("%5s  %-20s\n", "ID", "Artikel");
		for (Artikel art : artikel) {
			index++;
			if (!art.istVerleihbar())
				continue;
			System.out.printf("%5s  %-20s\n", index-1 , art.bestandString());
		}
		Scanner scannerID=new Scanner(System.in);
		while (true) {
			System.out.println("Bitte ID des verlorenen Artikels eingeben");
			try {
				schluesselID=scannerID.nextInt();
			} catch(InputMismatchException e) {
				System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
				scannerID.nextLine();
				continue;
			}


			if (schluesselID < 0 || schluesselID >= artikel.length
					|| !artikel[schluesselID].istVerleihbar())
				System.err.println("Diese Option ist nicht verfügbar");
			else
				break;
		}
		System.out.println("Bitte eine Menge eingeben");
		while(true)	{
			try {
				menge=scannerID.nextInt();
				break;
			} catch(InputMismatchException e) {
				System.err.println("Fehler in der Eingabe! Es war keine akzeptable Zahl");
				scannerID.nextLine();
			}
		}
		System.out.println("Sind Sie sicher?\nBestätigen Sie mit [j], verneinen Siemit einer beliebigen Taste");
		String weiter = scannerID.next();
		if (weiter.equals("j")) {
			System.out.println("Bitte bezahlen: " + aktuellerKunde.verlustMelden(
					(LagerPosten) artikel[schluesselID], this.zeit, menge).toString());
			
		} else {
			System.out.println("Abgebrochen.");
		}
	}

	/**
	 * Auflistung des Gesamtbestands mit ID und Produktnamen.
	 * 
	 * @param verk Sollen verkäufliche Produkte mit aufgeführt werden?
	 * @param verl Sollen verleihbare Produkte mit aufgeführt werden?
	 */
	private void gesamtBestand(boolean verk, boolean verl) {
		int index = 0;
		System.out.printf("%s\t %s\n", "ID", "Artikel");
		for (Artikel art : artikel) {
			index++;
			if (verl && !art.istVerleihbar())
				continue;
			if (verk && !art.istVerkaeuflich())
				continue;
			System.out.printf("%d\t %s\n", index-1,  art.toString());
		}
	}

	/**
	 * Initialisiert einen Betrieb und bietet ein Hauptmenü
	 * für die gängigen Aktionen in diesem Betrieb an.
	 * 
	 * Florian Bussmann
	 * 
	 * @param args nicht verwendet.
	 */
	public static void main(String[] args) {
		Betrieb betr = new Betrieb();
		betr.kundeHinzufuegen(new Kunde("Mertens", "Robert",
				"Musterstraße", 1234, 12345, "Musterhausen"));

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
				"Abrechnung des aktuellen Kunden",
				"Auflistung aller Kunden mit ihren Umsätzen",
				"Auflistung der mit dem aktuellen Kunden durchgeführten Transaktionen",
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
				System.out.print("Befehl(0 für Befehlliste)> ");
				System.out.flush();
				try{
					aktion = scanner.nextInt();
				} catch(InputMismatchException e) {
					scanner.nextLine();
				}
				if (aktion < 0 || aktion >= aktionen.length)
					aktion = 0;	// wenn der nutzer schwachsinn eingibt, liste anzeigen
				if (aktion >= 0 && aktion < aktionen.length)
					break;
			}

			System.out.println(aktionen[aktion]);
			try {
				switch (aktion) {
					case 0:
						System.out.println("Aktueller Kunde: " + betr.aktuellerKunde);
						System.out.println(betr.aktuellerKunde.getAnschrift());
						System.out.println("Bitte Aktion wählen:");
						int aktionsID = 0;
						for (String aktionsBeschreibung : aktionen) {
							System.out.printf("%4s %s\n", "[" + aktionsID + "]",
									aktionsBeschreibung);
							aktionsID++;
						}
						break;
					case 1:
						betr.bestandAuflisten(true, false);
						break;
					case 2:
						betr.bestandAuflisten(false, true);
						break;
					case 3:
						betr.gesamtBestand(true, false);
						break;
					case 4:
						betr.verkaufen();
						break;
					case 5:
						betr.gesamtBestand(false, true);
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
						int eingabe;
						do {
							System.out.println("Wie lange (Stunden)?");
							eingabe = scanner.nextInt();
							betr.anDerUhrDrehen(eingabe);
						}while(eingabe < 0);
						break;
					case 16:
						run = false;
						break;
					default:
				}
			} catch (InputMismatchException e) {
				System.err.println("FEHLER: unerwartete Eingabe");
			} catch(Throwable e) {
				System.err.println("FEHLER: " + e.getMessage());
			}
		} while (run);
	}
}
