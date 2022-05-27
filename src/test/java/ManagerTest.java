import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;



public class ManagerTest {

    @Test
    void checkIfUserCanLendTest1() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 1, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(true, cut.checkIfUserCanLend(5555));


    }

    @Test
    void checkIfUserCanLendTest2() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 1, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(false, cut.checkIfUserCanLend(5555));


    }

    @Test
    void checkIfUserCanLendTest3() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 2, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(true, cut.checkIfUserCanLend(5555));


    }
    @Test
    void checkIfUserCanLendTest4() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 2, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );
        Book four = new Book(1, 123456, "aaa","bbb" );
        Book five = new Book(2, 123456, "aaa","bbb" );
        Book six = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);
        books.add(four);
        books.add(five);
        books.add(six);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(false, cut.checkIfUserCanLend(5555));


    }
    @Test
    void checkIfUserCanLendTest5() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 3, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );
        Book four = new Book(1, 123456, "aaa","bbb" );
        Book five = new Book(2, 123456, "aaa","bbb" );
        Book six = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);
        books.add(four);
        books.add(five);
        books.add(six);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(true, cut.checkIfUserCanLend(5555));

    }

    @Test
    void checkIfUserCanLendTest6() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 3, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );
        Book four = new Book(1, 123456, "aaa","bbb" );
        Book five = new Book(2, 123456, "aaa","bbb" );
        Book six = new Book(2, 123456, "aaa","bbb" );
        Book seven = new Book(1, 123456, "aaa","bbb" );
        Book eight = new Book(2, 123456, "aaa","bbb" );
        Book nine = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);
        books.add(four);
        books.add(five);
        books.add(six);
        books.add(seven);
        books.add(eight);
        books.add(nine);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(false, cut.checkIfUserCanLend(5555));

    }

    @Test
    void checkIfUserCanLendTest7() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 4, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );
        Book four = new Book(1, 123456, "aaa","bbb" );
        Book five = new Book(2, 123456, "aaa","bbb" );
        Book six = new Book(2, 123456, "aaa","bbb" );
        Book seven = new Book(1, 123456, "aaa","bbb" );
        Book eight = new Book(2, 123456, "aaa","bbb" );
        Book nine = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);
        books.add(four);
        books.add(five);
        books.add(six);
        books.add(seven);
        books.add(eight);
        books.add(nine);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(true, cut.checkIfUserCanLend(5555));

    }

    @Test
    void checkIfUserCanLendTest8() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 4, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );
        Book four = new Book(1, 123456, "aaa","bbb" );
        Book five = new Book(2, 123456, "aaa","bbb" );
        Book six = new Book(2, 123456, "aaa","bbb" );
        Book seven = new Book(1, 123456, "aaa","bbb" );
        Book eight = new Book(2, 123456, "aaa","bbb" );
        Book nine = new Book(2, 123456, "aaa","bbb" );
        Book ten = new Book(1, 123456, "aaa","bbb" );
        Book eleven = new Book(2, 123456, "aaa","bbb" );
        Book twelve = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);
        books.add(four);
        books.add(five);
        books.add(six);
        books.add(seven);
        books.add(eight);
        books.add(nine);
        books.add(ten);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(false, cut.checkIfUserCanLend(5555));

    }

    @Test
    void checkIfAnyLentBooksAreLateTest1() throws ParseException, SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        long timeOfLoan = 730;
        Timestamp time = new Timestamp(timeOfLoan);
        when(str.getUserOldestBook(5555)).thenReturn(time);

        long curTime = 1000;
        when(str.currentTime()).thenReturn(curTime);


        assertEquals(false, cut.checkIfAnyLentBooksAreLate(5555));

    }

    @Test
    void checkIfAnyLentBooksAreLateTest2() throws ParseException, SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        long ong = 100;
        Timestamp time = new Timestamp(ong);
        when(str.getUserOldestBook(5555)).thenReturn(time);

        long curTime = 13000000;
        when(str.currentTime()).thenReturn(curTime);



        assertEquals(true, cut.checkIfAnyLentBooksAreLate(5555));

    }


    @Test
    void lendBookTest1() throws SQLException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 1, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(true, cut.lendBook(10,5555));
    }

    @Test
    void lendBookTest2() throws SQLException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        when(str.getUser(5555)).thenReturn(
                new User(5555, "aaa", "bbb", "010101-5555", 1, 0, false)
        );

        ArrayList<Book> books = new ArrayList<Book>();
        Book one = new Book(1, 123456, "aaa","bbb" );
        Book two = new Book(2, 123456, "aaa","bbb" );
        Book three = new Book(2, 123456, "aaa","bbb" );

        books.add(one);
        books.add(two);
        books.add(three);

        when(str.getUserBooks(5555)).thenReturn(books);

        assertEquals(false, cut.lendBook(10,5555));
    }

    @Test
    void checkIfUserIsSuspendedTest1() throws SQLException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> IDs = new ArrayList<Integer>();
        Integer one = 1111;
        Integer two = 1000;
        Integer three = 1234;
        IDs.add(one);
        IDs.add(two);
        IDs.add(three);

        when(str.getSuspendedIDs()).thenReturn(
                IDs
        );

        assertEquals(false, cut.checkIfUserIsSupended(5555));
    }

    @Test
    void checkIfUserIsSuspendedTest2() throws SQLException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> IDs = new ArrayList<Integer>();
        Integer one = 1111;
        Integer two = 1000;
        Integer three = 1234;
        IDs.add(one);
        IDs.add(two);
        IDs.add(three);

        when(str.getSuspendedIDs()).thenReturn(
                IDs
        );

        assertEquals(true, cut.checkIfUserIsSupended(1111));
    }

    @Test
    void checkIfUserShouldBeUnsuspendedTest1() throws ParseException, SQLException { // samma fel här med testet,

        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        long timeOfLoan = 5000;
        Timestamp time = new Timestamp(timeOfLoan);
        when(str.getUserSuspensionDate(5555)).thenReturn(time);

        long curTime = 400000;
        when(str.currentTime()).thenReturn(curTime);

        assertEquals(true, cut.checkIfUserShouldBeUnsuspended(5555));

    }

    @Test
    void checkIfUserShouldBeUnsuspendedTest2() throws ParseException, SQLException { // samma grej som innan, funkar på detta håller men inte andra
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);


        long timeOfLoan = 400;
        Timestamp time = new Timestamp(timeOfLoan);
        when(str.getUserSuspensionDate(5555)).thenReturn(time);

        long curTime = 1000;
        when(str.currentTime()).thenReturn(curTime);

        assertEquals(false, cut.checkIfUserShouldBeUnsuspended(5555));

    }


    @Test
    void registerUserTest1() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertEquals(0, cut.registerUser(2222,"aaaa","bbbb", "123456-7890", 2));
    }

    @Test
    void registerUserTest2() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertEquals(2, cut.registerUser(1111,"aaaa","bbbb", "000000-0000", 2));
    }

    @Test
    void registerUserTest3() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertEquals(1, cut.registerUser(9098,"aaaa","bbbb", "010201-1234", 2));
    }

    @Test
    void registerUserTest4() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertThrows(UnusableException.class, () -> cut.registerUser(98,"aaaa","bbbb", "000000-0000", 2));
    }

    @Test
    void registerUserTest5() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertThrows(UnusableException.class, () -> cut.registerUser(10000,"aaaa","bbbb", "000000-0000", 2));
    }

    @Test
    void registerUserTest6() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertThrows(UnusableException.class, () -> cut.registerUser(1000,"aaaa","bbbb", "000000", 2));
    }

    @Test
    void registerUserTest7() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertThrows(UnusableException.class, () -> cut.registerUser(1000,"aaaa","bbbb", "000000-00000", 2));
    }

    @Test
    void registerUserTest8() throws SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        ArrayList<Integer> currentIDs = new ArrayList<Integer>();
        ArrayList<String> currentPN = new ArrayList<String>();
        ArrayList<String> bannedPN = new ArrayList<String>();

        Integer Id1 = 1111;
        Integer Id2 = 1293;
        currentIDs.add(Id1);
        currentIDs.add(Id2);

        String curPN1 = "010101-1234";
        String curPN2 = "010201-1234";
        currentPN.add(curPN1);
        currentPN.add(curPN2);

        String bannedPN1 = "123456-7890";
        String bannedPN2 = "123556-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertThrows(UnusableException.class, () -> cut.registerUser(10000,"aaaa","bbbb", "000000-00000", 2));
    }

}
