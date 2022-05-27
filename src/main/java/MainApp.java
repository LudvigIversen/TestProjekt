import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainApp {
    static Logger logger = Logger.getLogger(MainApp.class);
    static LibraryStore store = new LibraryStore();
    static LibraryManager manager = new LibraryManager(store);

    public void userView(int UserID) throws SQLException, UnusableException {
        MainApp app = new MainApp();
        User user = store.getUser(UserID);
        boolean sus = manager.checkIfUserIsSupended(user.getUserID());

        if (sus == true) {
            logger.info("Användaren " + user.getUserID() + " är avstängd");
            System.out.println("Du är avstängd!");
            boolean suus = manager.checkIfUserShouldBeUnsuspended(user.getUserID());
            if (suus == true) {
                logger.info("Användaren " + user.getUserID() + " är inte längre avstängd");
                System.out.println("Du är längre inte avstängd, välkommen!");
                store.unsuspendUser(user.getUserID());
                app.userView(user.getUserID());
            }
            System.exit(0);

        } else if (sus == false) {
            logger.info("Användaren " + user.getUserID() + " är inte avstängd");
            boolean sus1 = manager.checkIfAnyLentBooksAreLate(user.getUserID());

            if (sus1 == true) {
                logger.info("Användaren " + user.getUserID() + " har försenade böcker");
                int count = user.getSuscounter();
                if (count < 2) {
                    logger.info("Användaren " + user.getUserID() + " blir avstängd");
                    System.out.println("Du har försenade böcker och blir nu avstängd i 15 dagar! ");
                    store.suspendUser(user.getUserID());
                    System.exit(0);
                } else if (count >= 2) {
                    logger.info("Användaren " + user.getUserID() + " blir bannad");
                    System.out.println("Detta är tredje gången du blir avstängd och är därför bannad! ");
                    store.banUser(user.getUserID());
                    System.exit(0);
                }

            } else if (sus1 == false) {
                logger.info("Användaren " + user.getUserID() + " loggar in");
                System.out.println("Inloggad! Välkommen " + user.getFirstName());
            }
        }
        Scanner in = new Scanner(System.in);
        boolean cont = true;
        while (cont == true) {
            System.out.println("Välj 1 för att söka efter och låna en bok");
            System.out.println("Välj 2 för att se dina böcker och returnera");
            System.out.println("Välj 3 för att radera ditt konto");
            System.out.println("Välj 4 för att logga ut");
            int val = in.nextInt();
            switch (val) {
                case 1:
                    boolean canLoan = manager.checkIfUserCanLend(user.getUserID());
                    logger.info("Användaren " + user.getUserID() + " försöker låna böcker");
                        System.out.println("Sök efter en titel: ");
                        String bookTitle = in.next();
                    logger.info("Användaren " + user.getUserID() + " söker efter boken: " + bookTitle);
                        ArrayList<Book> books = store.getBookWithTitle(bookTitle);
                        if (books.size() == 0) {
                            logger.info(bookTitle + " fanns inte");
                            System.out.println("Tyvärr finns denna boken inte tillänglig");
                            break;
                        }
                        for (int i = 0; i < books.size(); i++) {
                            System.out.println("Välj " + i + " för att låna " + books.get(i).toString());
                        }
                        System.out.println("Välj 9999 för att gå tillbaka");
                        Scanner in2 = new Scanner(System.in);
                        int val1 = in2.nextInt();
                        if (val1 == 9999) {
                            break;
                        }


                        boolean lend = manager.lendBook(books.get(val1).bookID, user.getUserID());
                        if (lend == false) {
                            logger.info("Användaren " + user.getUserID() + " kan inte låna fler böcker");
                            System.out.println("Du kan inte låna fler böcker");
                        } else if (lend == true) {
                            logger.info("Användaren " + user.getUserID() + " lånar boken: " + books.get(val1).getID());
                            System.out.println("Du har nu lånat: " + books.get(val1).title);
                        }
                    break;
                case 2:
                    logger.info("Användaren " + user.getUserID() + " vill lämna tillbaka böcker");
                    ArrayList<Book> lentBooks = store.getUserBooks(user.getUserID());
                    if (lentBooks.size() == 0) {
                        logger.info("Användaren " + user.getUserID() + " kan inte returnera böcker då det inte finns några lånade");
                        System.out.println("Du har inga lånade böcker");
                        break;
                    }
                    System.out.println("Dina lånade böcker:");
                    for (int i = 0; i < lentBooks.size(); i++) {
                        System.out.println("Välj " + i + " för att returnera " +lentBooks.get(i).toString());
                    }
                    System.out.println("Välj 9999 för att gå tillbaka");
                    Scanner in3 = new Scanner(System.in);
                    int val2 = in3.nextInt();
                    if (val2 == 9999) {
                        break;
                    } else {
                        logger.info("Användaren " + user.getUserID() + " lämnar tillbaka " + lentBooks.get(val2).getID());
                        System.out.println("Du har lämnat tillbaka " + lentBooks.get(val2).title);
                        store.returnBook(lentBooks.get(val2).bookID);
                    }
                    break;
                case 3:
                    logger.info("Användaren " + user.getUserID() + " försöker ta bort sitt konto");
                    System.out.println("Är du säker på att du vill ta bort ditt konto? Y/N");
                    Scanner in4 = new Scanner(System.in);
                    String val3 = in4.next();
                    if (val3.charAt(0) == 'Y' ) {
                        logger.info("Användaren " + user.getUserID() + " raderar sitt konto");
                        System.out.println("Ditt konto kommer nu bli raderat");
                        store.deleteUser(user.getUserID());
                        System.exit(0);
                    } else if (val3.charAt(0) == 'N' ) {
                        logger.info("Användaren " + user.getUserID() + " raderar inte sitt konto");
                        System.out.println("Bra val");
                        break;
                    }
                case 4:
                    System.out.println("Välkommen åter");
                    logger.info("Användaren " + user.getUserID() + " stänger av");
                    System.exit(0);
            }
        }

    }

    public void librarianView() throws SQLException, UnusableException {
        boolean cont = true;
        Scanner in = new Scanner(System.in);
        logger.info("Bibliotekarien loggar in");
        while (cont == true) {
            System.out.println("Välj 1 för att söka efter böcker");
            System.out.println("Välj 2 för att söka efter användare");
            System.out.println("Välj 3 för att stänga av en användare");
            System.out.println("Välj 4 för att banna en användare");
            System.out.println("Välj 5 för att registrera en ny användare");
            System.out.println("Välj 6 för att logga ut");
            int val = in.nextInt();
            switch (val) {
                case 1:
                    logger.info("Bibliotikarien söker efter bok med ISBN");
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("Sök efter en titel: ");
                    int isbn = in.nextInt();
                    logger.info("Bibliotekarien söker med ISBN: " + isbn);
                    ArrayList<Book> books = store.getBookWithISBN(isbn);
                    if (books.size() == 0) {
                        logger.info(isbn + " fanns inte i databasen");
                        System.out.println("Tyvärr finns denna boken inte tillänglig");
                        break;
                    }
                    logger.info("Resultat för " + isbn + "visas");
                    for (int i = 0; i < books.size(); i++) {
                        System.out.println(books.get(i).toString());
                    }
                    System.out.println("Välj 9999 för att gå tillbaka");
                    Scanner in2 = new Scanner(System.in);
                    int val1 = in2.nextInt();
                    if (val1 == 9999) {
                        break;
                    }
                case 2:
                    logger.info("Bibliotekarien söker efter en användare");
                    Scanner in3 = new Scanner(System.in);
                    System.out.println("Skriv in det användarID du söker");
                    int usID = in3.nextInt();
                    User user = store.getUser(usID);
                    if (user == null) {
                        logger.info("Bibliotekarien sökte efter en användare som inte finns");
                        System.out.println("Användaren med ID " + usID + " finns inte");
                        break;
                    } else {
                        logger.info("Bibliotekariens sökta användare visas");
                        System.out.println(user.toString());
                        break;
                    }

                case 3:
                    logger.info("Bibliotekarien försöker stänga av en användare");
                    Scanner in4 = new Scanner(System.in);
                    System.out.println("Skriv in användarID på personen som ska bli avstängd");
                    int usID1 = in4.nextInt();
                    boolean sus1 = manager.checkIfUserIsSupended(usID1);
                    if (sus1 == true) {
                        logger.info("Användaren " + usID1 + " är redan avstängd");
                        System.out.println("Användare " + usID1 + " är redan avstängd");
                        break;
                    } else if (sus1 == false) {
                        boolean sus2 = manager.checkIfAnyLentBooksAreLate(usID1);
                        if (sus2 == true) {
                            logger.info("Användare " + usID1 + " är nu avstängd i 15 dagar");
                            System.out.println("Användare " + usID1 + " är nu avstängd i 15 dagar");
                            store.suspendUser(usID1);
                            break;
                        } else if (sus2 == false) {
                            logger.info("Användare " + usID1 + " blir inte avstängd");
                            System.out.println("Användare " + usID1 + " borde inte bli avstängd då den inte har några sena böcker");
                            break;
                        }
                    }
                case 4:
                    logger.info("Bibliotekarien förösker stänga av en användare");
                    Scanner in5 = new Scanner(System.in);
                    System.out.println("Skriv in användarID på personen som ska bli banned");
                    int usID2 = in5.nextInt();
                    User user2 = store.getUser(usID2);
                    logger.info("Bibliotekarien försöker stänga av användaren: " + usID2);
                    boolean sus3 = manager.checkIfAnyLentBooksAreLate(usID2);

                    if (sus3 == false) {
                        logger.info(usID2 + " har inga försenade böcker och behövs inte bannas");
                        System.out.println("Användaren har inga försenade böcker, banna behövs inte");
                        break;
                    } else if (sus3 == true) {
                        if (user2.getSuscounter() < 2) {
                            logger.info(usID2 + " ska bli suspended och inte bannas");
                            System.out.println("Användaren ska bli suspended och inte bannad");
                            break;
                        } else if (user2.getSuscounter() == 2) {
                            logger.info(usID2 + " är nu bannad");
                            System.out.println("Användare " + usID2 + " blir nu bannad");
                            store.banUser(usID2);
                            break;
                        }
                    }
                    break;

                    case 5:
                        logger.info("Bibliotekarien väljer att registrera en användare");
                        MainApp app = new MainApp();
                        app.registerView();

                    case 6:
                    System.out.println("Välkommen åter");
                    System.exit(0);
            }
        }


    }

    public void registerView() throws UnusableException, SQLException {
        logger.info("Registrering av ny användare startas");
        Scanner input = new Scanner(System.in);
        boolean cont = true;
        MainApp app = new MainApp();
        while (cont == true) {
            /*
            System.out.println("Ange ett ID (Fyrsiffrigt): ");
            int ID = input.nextInt();

            if (ID < 1000 || ID > 9999) {
                System.out.println("AnvändarID ska vara fyrsiffrigt");
                continue;
            }

             */
            int ID = 0;
            boolean con = false;
            ArrayList<Integer> IDs = store.getAllUserID();
            while (con == false) {
                ID = new Random().nextInt(9000) + 1000;
                if (!IDs.contains(ID)) {
                    con = true;
                } else {
                    continue;
                }
            }
            System.out.println("Ditt användarID kommer bli: " + ID);

            System.out.println("Ange förnamn: ");
            String firstName = input.next();

            System.out.println("Ange efternamn: ");
            String lastName = input.next();

            System.out.println("Ange personnummer (yymmdd-xxxx): ");
            String PN = input.next();

            System.out.println("Ange typ av användare (1=student, 2=master, 3=doktorand, 4=lärare): ");
            int level = input.nextInt();

            int result = manager.registerUser(ID, firstName, lastName, PN, level);

            if (result == 1) {

                logger.error("Användare med personnummer: " + PN + " har redan ett konto");
                System.out.println("Användaren är redan registrerad, inget konto behövs skapas!");
                main(null);
            } else if (result == 2) {
                logger.info("Kontot med användar ID: " + ID + " skapas");
                System.out.println("Kontot är skapat!");
                app.userView(ID);
            } else if (result == 0) {
                logger.info("Anänvdaren med personnummret: " + PN + " är en bannad användare");
                System.out.println("Användaren är bannad och kan inte skapa ett nytt konto");
                main(null);
            }
        }

    }

    public static void main(String[] args) throws SQLException, UnusableException {
        PropertyConfigurator.configure("C:\\Users\\ludde\\Desktop\\ProjektFörTester\\src\\log4j.properties");

        Scanner scan = new Scanner(System.in);
        MainApp app = new MainApp();
        logger.info("Programmet startar");

        System.out.println("Välkommen till biblioteket! Vad vill du göra? ");
        System.out.println("Välj 1 för att logga in ");

        System.out.println("Välj 2 för att registrera dig ");

        System.out.println("Välj 3 för att stäng av");

        int val = scan.nextInt();


        if (val == 1) {
            logger.info("Användare väljer att logga in");
            System.out.println("Skriv ditt användarID");
            int userID = scan.nextInt();
            logger.info("Användare skriver in ID: " + userID);
            if (userID < 10000) {
                logger.info("Student inloggning");
                ArrayList<Integer> IDs = store.getAllUserID();
                if (IDs.contains(userID)) {
                    logger.info("Användar ID: " + userID + " fanns");
                    app.userView(userID);
                } else {
                    System.out.println("Det finns ingen registrerad användare med detta ID!");
                    logger.error("Användare ID: " + userID + " fanns inte");
                    main(null);
                }
            }  else if (userID > 10000) {
                logger.info("Användare loggar in som bibliotekarie");
                System.out.println("Du loggas nu in som bibliotekarie! ");
                app.librarianView();
            }
        } else if (val == 2) {
            logger.info("Användare väljer att registrera sig");
            app.registerView();
        } else if (val == 3) {
            logger.info("Systemet stängs av");
            System.exit(0);
        }

    }
}


