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




    public boolean checkIfAnyLentBooksAreLate(int userID) throws SQLException {
        Timestamp timeOfLoanStamp = store.getUserOldestBook(userID);
        if(timeOfLoanStamp == null) {
            return false;
        }
        long timeOfLoan = timeOfLoanStamp.getTime();
        Timestamp sysTime = new Timestamp(store.currentTime()); // ändra här så det är en metod som hämtar tiden, då kan vi mocka den i tester
        long currentTime = sysTime.getTime();
        long diff = currentTime - timeOfLoan;

        if (diff > 1000) { // ska vara 300000
            return true;
        } else {
            return false;
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
        Timestamp timeOfSuspensionStamp = store.getUserSuspensionDate(userID);

        if (timeOfSuspensionStamp == null) {
            return false;
        }

        long timeOfSuspension = timeOfSuspensionStamp.getTime();
        Timestamp sysTime = new Timestamp(store.currentTime());
        long currentTime = sysTime.getTime();
        long diff = currentTime - timeOfSuspension;


        if (diff > 1000) { // ska vara 300000
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

            if (currentPN.contains(personalNumber)) {
                result = 1;
                return result;
            } else if (bannedPN.contains(personalNumber)) {
                result = 0;
                return result;
            } else {
                store.createUser(userID, firstName, lastName, personalNumber, level);
                result = 2;
                return result;
            }
        }
    }
}
