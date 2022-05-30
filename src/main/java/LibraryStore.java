import java.sql.*;
import java.util.ArrayList;

public class LibraryStore implements ILibraryStore {

    public ArrayList<Book> getBookWithTitle(String title) throws SQLException {
        ArrayList<Book> books = new ArrayList<Book>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM books where title='" + title + "' AND available=true" );


            while (set.next()) {
                Book book = new Book();
                book.setID(set.getInt("ID"));
                book.setISBN(set.getInt("ISBN"));
                book.setTitle(set.getString("Title"));
                book.setAuthor(set.getString("Author"));
                books.add(book);
            }

        }

        return books;
    }

    public ArrayList<Book> getBookWithISBN(int ISBN) throws SQLException {
        ArrayList<Book> books = new ArrayList<Book>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM books where ISBN='" + ISBN + "' AND available=true" );

            while (set.next()) {
                Book book = new Book();
                book.setID(set.getInt("ID"));
                book.setISBN(set.getInt("ISBN"));
                book.setTitle(set.getString("Title"));
                book.setAuthor(set.getString("Author"));
                books.add(book);
            }

        }

        return books;
    }

    public User getUser(int userID) throws SQLException {
        User user = null;
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM users  where ID='" + userID + "'" );

            while(set.next()) {
                user = new User(set.getInt("ID"), set.getString("FirstName"),
                        set.getString("LastName"), set.getString("PersonalNumber"), set.getInt("Level"),
                        set.getInt("Suscounter"), set.getBoolean("Suspended"));
            }
            return user;
        }


    }

    public ArrayList<Integer> getSuspendedIDs() throws SQLException {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT ID FROM suspended_users");

            while(set.next()) {
                Integer ID = set.getInt("ID");
                IDs.add(ID);
            }

        }
        return IDs;
    }


    public ArrayList<Integer> getAllUserID() throws SQLException{
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT ID FROM users");

            while (set.next()) {
                Integer integer = set.getInt(1);
                IDs.add(integer);
            }

        }

        return IDs;
    }


    public ArrayList<Book> getUserBooks(int userID) throws SQLException { // kanske behöver hämta timestamp, kan också vara en lösning att hämta oldestBook typ
        ArrayList<Book> books = new ArrayList<Book>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("select books.ID, books.ISBN, books.Title, books.Author from users, books, userbooks where users.ID = userbooks.UserID and books.ID = userbooks.BookID and UserID =" + userID);

            while (set.next()) {
                Book book = new Book();
                book.setID(set.getInt("ID"));
                book.setISBN(set.getInt("ISBN"));
                book.setTitle(set.getString("Title"));
                book.setAuthor(set.getString("Author"));
                books.add(book);
            }

        }

        return books;
    }


    public ArrayList<String> getUserPersonalNumbers() throws SQLException{
        ArrayList<String> IDs = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT PersonalNumber FROM users");

            while (set.next()) {
                String pN = set.getString(1);
                IDs.add(pN);
            }

        }

        return IDs;

    }

    public ArrayList<String> getBannedUsersPersonalNumber() throws SQLException {

        ArrayList<String> IDs = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT PersonalNumber FROM banned_users");

            while (set.next()) {
                String pN = set.getString(1);
                IDs.add(pN);
            }

        }

        return IDs;
    }


    public void returnBook(int bookID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from userbooks where BookID =" + bookID);
            statement.executeUpdate("update books set available = true where ID =" + bookID);

        }

    }


    public void storeLendBook(int bookID, int userID) throws SQLException{
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {


            Statement statement = conn.createStatement();
            statement.executeUpdate("insert into userbooks (BookID, UserID, TimeOfLoan) values ("+ bookID + "," + userID +", now())");
            statement.executeUpdate("update books set available = false where ID =" + bookID);

        }
    }


    public void createUser(int userID, String firstName, String lastName, String personalNumber, int level) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            statement.executeUpdate("insert into users (ID, FirstName, LastName, PersonalNumber, Level, Suscounter, Suspended) values (" + userID +", '"+ firstName + "', '"+ lastName + "', '" + personalNumber + "', " + level + ", 0, false )");

        }
    }


    public void deleteUser(int userID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            ArrayList<Integer> booksIDs = new ArrayList<Integer>();
            Integer bID;
            Statement statement3 = conn.createStatement();
            ResultSet set2 = statement3.executeQuery("select * from userbooks where UserID=" + userID);
            while (set2.next()) {
                bID = set2.getInt("BookID");
                booksIDs.add(bID);
            }

            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from users where ID='" + userID + "'");
        }

    }


    public void banUser(int userID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            ArrayList<Integer> booksIDs = new ArrayList<Integer>();
            Integer bID;
            Statement statement3 = conn.createStatement();
            ResultSet set2 = statement3.executeQuery("select * from userbooks where UserID=" + userID);
            while (set2.next()) {
                bID = set2.getInt("BookID");
                booksIDs.add(bID);
            }

            for (int i = 0; i < booksIDs.size(); i++) {
                returnBook(booksIDs.get(i));
            }

            int ID = 0;
            String fN = "";
            String lN = "";
            String pN = "";
            int level = 0;
            int susCounter = 0;
            boolean sus = true;

            Statement statement1 = conn.createStatement();
            ResultSet set = statement1.executeQuery("select * from users where ID='" + userID + "'");
            while (set.next()) {
                ID = set.getInt("ID");
                fN = set.getString("FirstName");
                lN = set.getString("LastName");
                pN = set.getString("PersonalNumber");
                level = set.getInt("Level");
                susCounter = set.getInt("Suscounter");
                sus = set.getBoolean("Suspended");
            }
            Statement statement2 = conn.createStatement();
            statement2.executeUpdate("insert into banned_users (ID, FirstName, LastName, PersonalNumber, Level, Suscounter, Suspended) values (" + userID +", '"+ fN + "', '"+ lN + "', '" + pN + "', " + level + ", "+ susCounter + ", "+sus + " )");
            statement2.executeUpdate("delete from users where ID='" + userID + "'");
        }

    }


    public void suspendUser(int userID) throws SQLException{
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            int ID = 0;
            String fN = "";
            String lN = "";
            String pN = "";
            int level = 0;
            int susCounter = 0;
            boolean sus = true;

            Statement statement1 = conn.createStatement();
            ResultSet set = statement1.executeQuery("select * from users where ID='" + userID + "'");
            while (set.next()) {
                ID = set.getInt("ID");
                fN = set.getString("FirstName");
                lN = set.getString("LastName");
                pN = set.getString("PersonalNumber");
                level = set.getInt("Level");
                susCounter = set.getInt("Suscounter");
                sus = set.getBoolean("Suspended");
            }
            Statement statement2 = conn.createStatement();
            statement2.executeUpdate("insert into suspended_users (ID, FirstName, LastName, PersonalNumber, Level, Suscounter, Suspended, TimeOfSuspension) values (" + userID + ", '" + fN + "', '" + lN + "', '" + pN + "', " + level + ", " + susCounter + ", " + sus + ", now())");
            statement2.executeUpdate("update users set Suspended = true, Suscounter = " + (susCounter + 1) + " where ID='" + userID + "'");

            ArrayList<Integer> booksIDs = new ArrayList<Integer>();
            Integer bID;
            Statement statement3 = conn.createStatement();
            ResultSet set2 = statement3.executeQuery("select * from userbooks where UserID=" + userID);
            while (set2.next()) {
                bID = set2.getInt("BookID");
                booksIDs.add(bID);
            }

            for (int i = 0; i < booksIDs.size(); i++) {
                returnBook(booksIDs.get(i));
            }
        }
    }


    public void unsuspendUser(int userID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from suspended_users where ID='" + userID + "'");
            statement.executeUpdate("update users set Suspended = false where ID='" + userID + "'");
        }
    }


    public Timestamp getUserSuspensionDate(int userID) throws SQLException {
        Timestamp time = null;
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("select TimeOfSuspension from suspended_users where ID=" + userID);

            while (set.next()) {
                time = set.getTimestamp("TimeOfSuspension");
            }
        }
        return time;
    }

    public Timestamp getUserOldestBook(int userID) throws SQLException {
        Timestamp time = null;
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/LibraryApp?useSSL=false",
                "root", "Luddeiversen1234")) {

            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("select TimeOfLoan from userbooks where UserID="+ userID +" order by TimeOfLoan asc limit 1");

            while (set.next()) {
                time = set.getTimestamp("TimeOfLoan");
            }
        }
        return time;
    }

    public long currentTime() {
        return System.currentTimeMillis();
    }

}
