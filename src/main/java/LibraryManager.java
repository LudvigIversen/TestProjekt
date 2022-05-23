import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LibraryManager {
    LibraryStore store = null;

    private static Logger logger = LogManager.getLogManager().getLogger(LibraryManager.class.getName());

    public LibraryManager(LibraryStore str){
        this.store = str;
    }

    public boolean checkIfUserCanLend(int userID) throws SQLException {
        User user = store.getUser(userID);

        ArrayList<Book> books = store.getUserBooks(userID);

        if (books.size() < user.getBooks()) {
            return true;
        } else {
            return false;
        }


    }


    public boolean checkIfAnyLentBooksAreLate(int userID) throws SQLException, UnusableException {
        Timestamp timeOfLoanStamp = store.getUserOldestBook(userID);
        if(timeOfLoanStamp == null) {
            throw new UnusableException();
        }

        long timeOfLoan = timeOfLoanStamp.getTime();
        Timestamp sysTime = new Timestamp(System.currentTimeMillis()); // ändra här så det är en metod som hämtar tiden, då kan vi mocka den i tester
        long currentTime = sysTime.getTime();
        long diff = currentTime - timeOfLoan;

        if (diff < 1200000) {
            return false;
        } else {
            return true;
        }

    }


    public boolean lendBook(int bookID, int userID) throws SQLException {
        boolean canLend = checkIfUserCanLend(userID);

        if(canLend == true) {
            store.storeLendBook(bookID, userID);
            return true;
        } else {
            return false;
        }

    }


    public boolean checkIfUserIsSupended(int userID) throws SQLException {
        ArrayList<Integer> IDs = store.getSuspendedIDs();

        if(IDs.contains(userID)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkIfUserShouldBeUnsuspended(int userID) throws SQLException {
        Timestamp timeOfLoanStamp = store.getUserSuspensionDate(userID);
        long timeOfLoan = timeOfLoanStamp.getTime();
        Timestamp sysTime = new Timestamp(System.currentTimeMillis());
        long currentTime = sysTime.getTime();
        long diff = currentTime - timeOfLoan;

        if (diff > 1200000) {
            return true;
        } else {
            return false;
        }
    }


    public int registerUser(int userID, String firstName, String lastName, String personalNumber, int level) throws SQLException, UnusableException {
        int result;
        ArrayList<Integer> currentIDs = store.getAllUserID();
        ArrayList<String> currentPN = store.getUserPersonalNumbers();
        ArrayList<String> bannedPN = store.getBannedUsersPersonalNumber();

        if (personalNumber.length() != 11) {
            throw new UnusableException();
        } else if (userID > 9999 || userID < 1000) {
            throw new UnusableException();
        } else {

            if (currentIDs.contains(userID)) {
                result = 1;
                return result;
            } else if (currentPN.contains(personalNumber)) {
                result = 2;
                return result;
            } else if (bannedPN.contains(personalNumber)) {
                result = 0;
                return result;
            } else {
                store.createUser(userID, firstName, lastName, personalNumber, level);
                result = 3;
                return result;
            }
        }
    }
}
