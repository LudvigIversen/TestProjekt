import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface ILibraryStore {

    ArrayList<Book> getBookWithTitle(String title) throws SQLException;

    ArrayList<Book> getBookWithISBN(int ISBN) throws SQLException;

    User getUser(int userID) throws SQLException;

    ArrayList<Integer> getSuspendedIDs() throws SQLException;

    ArrayList<Integer> getAllUserID() throws SQLException;

    ArrayList<Book> getUserBooks(int userID) throws SQLException;

    ArrayList<String> getUserPersonalNumbers() throws SQLException;

    ArrayList<String> getBannedUsersPersonalNumber() throws SQLException;

    void returnBook(int bookID) throws SQLException;

    void storeLendBook(int bookID, int userID) throws SQLException;

    void createUser(int userID, String firstName, String lastName, String personalNumber, int level) throws SQLException;

    void deleteUser(int userID) throws SQLException;

    void banUser(int userID) throws SQLException;

    void suspendUser(int userID) throws SQLException;

    void unsuspendUser(int userID) throws SQLException;

    Timestamp getUserSuspensionDate(int userID) throws SQLException;

    Timestamp getUserOldestBook(int userID) throws SQLException;

    long currentTime();

    }
