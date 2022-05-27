import org.apache.log4j.Logger;

import java.sql.SQLException;

public interface ILibraryManager {
    LibraryStore str = null;
    Logger logger = null;

    boolean checkIfUserCanLend(int userID) throws SQLException;

    boolean checkIfAnyLentBooksAreLate(int userID) throws SQLException;

    boolean lendBook(int bookID, int userID) throws SQLException;

    boolean checkIfUserIsSupended(int userID) throws SQLException;

    boolean checkIfUserShouldBeUnsuspended(int userID) throws SQLException;

    int registerUser(int userID, String firstName, String lastName, String personalNumber, int level) throws SQLException, UnusableException;

}
