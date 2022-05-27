import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.sound.midi.Soundbank;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
    static Logger logger = Logger.getLogger(MainApp.class);
    static LibraryStore store = new LibraryStore();
    static LibraryManager manager = new LibraryManager(store);

    // User user = null;

    public void userView(int UserID) throws SQLException, UnusableException {
        /*
        - Checka allt med banned/suspended
        - Låna (Söka efter böcker Title)
        - Returnera
        - Radera konto
         */
        User user = store.getUser(UserID);
        boolean sus = manager.checkIfUserIsSupended(user.getUserID());

        if (sus == true) {
            System.out.println("Du är avstängd!");
            boolean suus = manager.checkIfUserShouldBeUnsuspended(user.getUserID());
            if (suus == true) {
                System.out.println("Du är längre inte avstängd, logga in igen för att låna nya böcker! ");
                System.exit(0);
            }
            System.exit(0);

        } else if (sus == false) {
            boolean sus1 = manager.checkIfAnyLentBooksAreLate(user.getUserID());

            if (sus1 == true) {
                int count = user.getSuscounter();
                if (count < 2) {
                    System.out.println("Du har försenade böcker och blir nu avstängd i 15 dagar! ");
                    store.suspendUser(user.getUserID());
                } else if (count == 2) {
                    System.out.println("Detta är tredje gången du blir avstängd och är därför bannad! ");
                    store.banUser(user.getUserID());
                }

            } else if (sus1 == false) {
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

                        System.out.println("Sök efter en titel: ");
                        String bookTitle = in.next();
                        ArrayList<Book> books = store.getBookWithTitle(bookTitle);
                        if (books.size() == 0) {
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
                            System.out.println("Du kan inte låna fler böcker");
                        } else if (lend == true) {
                            System.out.println("Du har nu lånat: " + books.get(val1).title);
                        }
                    break;
                case 2:
                    ArrayList<Book> lentBooks = store.getUserBooks(user.getUserID());
                    if (lentBooks.size() == 0) {
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
                        System.out.println("Du har lämnat tillbaka " + lentBooks.get(val2).title);
                        store.returnBook(lentBooks.get(val2).bookID);
                    }
                    break;
                case 3:
                    System.out.println("Kom ihåg att lämna tillbaka dina böcker innan du tar bort ditt konto");
                    System.out.println("Är du säker på att du vill ta bort ditt konto? Y/N");
                    Scanner in4 = new Scanner(System.in);
                    String val3 = in4.next();
                    if (val3.charAt(0) == 'Y' ) {
                        System.out.println("Ditt konto kommer nu bli raderat");
                        store.deleteUser(user.getUserID());
                        System.exit(0);
                    } else if (val3.charAt(0) == 'N' ) {
                        System.out.println("Bra val");
                        break;
                    }
                case 4:
                    System.out.println("Välkommen åter");
                    System.exit(0);
            }
        }

    }

    public void librarianView() throws SQLException, UnusableException {
        /*
        - Söka efter böcker genom ISBN
        - Suspend en användare
        - Radera användare
        - Banned användare
        - Registrera användare
         */
        boolean cont = true;
        Scanner in = new Scanner(System.in);
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
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("Sök efter en titel: ");
                    int isbn = in.nextInt();
                    ArrayList<Book> books = store.getBookWithISBN(isbn);
                    if (books.size() == 0) {
                        System.out.println("Tyvärr finns denna boken inte tillänglig");
                        break;
                    }
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
                    Scanner in3 = new Scanner(System.in);
                    System.out.println("Skriv in det användarID du söker");
                    int usID = in3.nextInt();
                    User user = store.getUser(usID);
                    System.out.println(user.toString());
                    break;

                case 3:
                    Scanner in4 = new Scanner(System.in);
                    System.out.println("Skriv in användarID på personen som ska bli avstängd");
                    int usID1 = in4.nextInt();
                    boolean sus1 = manager.checkIfUserIsSupended(usID1);
                    if (sus1 == true) {
                        System.out.println("Användare " + usID1 + " är redan avstängd");
                        break;
                    } else if (sus1 == false) {
                        boolean sus2 = manager.checkIfAnyLentBooksAreLate(usID1);
                        if (sus2 == true) {
                            System.out.println("Användare " + usID1 + " är nu avstängd i 15 dagar");
                            store.suspendUser(usID1);
                            break;
                        } else if (sus2 == false) {
                            System.out.println("Användare " + usID1 + " borde inte bli avstängd då den inte har några sena böcker");
                            break;
                        }
                    }
                case 4: // Inga bannade användare, testa det!
                    Scanner in5 = new Scanner(System.in);
                    System.out.println("Skriv in användarID på personen som ska bli banned");
                    int usID2 = in5.nextInt();
                    User user2 = store.getUser(usID2);
                    boolean sus3 = manager.checkIfAnyLentBooksAreLate(usID2);

                    if (sus3 == false) {
                        System.out.println("Användaren har inga försenade böcker, banna behövs inte");
                        break;
                    } else if (sus3 == true) {
                        if (user2.getSuscounter() < 2) {
                            System.out.println("Användaren ska bli suspended och inte bannad");
                            break;
                        } else if (user2.getSuscounter() == 2) {
                            System.out.println("Användare " + usID2 + " blir nu bannad");
                            store.banUser(usID2);
                            break;
                        }
                    }
                    break;

                    case 5:
                        MainApp app = new MainApp();
                        app.registerView();

                    case 6:
                    System.out.println("Välkommen åter");
                    System.exit(0);
            }
        }


    }

    public void registerView() throws UnusableException, SQLException {
        Scanner input = new Scanner(System.in);
        boolean cont = true;

        while (cont == true) {
            System.out.println("Ange ett ID: ");
            int ID = input.nextInt();

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
                System.out.println("ID är upptaget, testa skriva in ett nytt");
                continue;
            } else if (result == 2) {
                System.out.println("Användaren är redan registrerad, inget konto behövs skapas!");
                System.exit(0);
                // Vill vi komma tillbaka till något här?
            } else if (result == 3) {
                System.out.println("Kontot är skapat!");
                MainApp app = new MainApp();
                app.userView(ID);
            } else if (result == 0) {
                System.out.println("Användaren är bannad och kan inte skapa ett nytt konto");
                System.exit(0);
            }
        }

    }

    public static void main(String[] args) throws SQLException, UnusableException {
        PropertyConfigurator.configure("C:\\Users\\ludde\\Desktop\\ProjektFörTester\\src\\log4j.properties");

        Scanner scan = new Scanner(System.in);
        MainApp app = new MainApp();

        System.out.println("Välkommen till biblioteket! Vad vill du göra? ");
        System.out.println("Välj 1 för att logga in ");
        System.out.println("Välj 2 för att registrera dig ");
        System.out.println("Välj 3 för att stäng av");
        int val = scan.nextInt();


        if (val == 1) {
            System.out.println("Skriv ditt användarID");
            int userID = scan.nextInt();

            if (userID < 10000) {
                ArrayList<Integer> IDs = store.getAllUserID();
                if (IDs.contains(userID)) {
                    app.userView(userID);
                } else {
                    System.out.println("Det finns ingen registrerad användare med detta ID!");
                    System.exit(0);
                }
            }  else if (userID > 10000) {
                System.out.println("Du loggas nu in som bibliotikarie! ");
                app.librarianView();
            }
        } else if (val == 2) {
            app.registerView();
        } else if (val == 3) {
            System.exit(0);
        }



    }
}


