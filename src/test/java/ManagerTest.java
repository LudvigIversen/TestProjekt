import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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
    void checkIfAnyLentBooksAreLateTest1() throws ParseException, SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = mock(LibraryManager.class);


        long timeOfLoan = 5000;
        Timestamp time = new Timestamp(timeOfLoan);
        when(str.getUserOldestBook(5555)).thenReturn(time);

        long curTime = 10000;
        when(cut.currentTime()).thenReturn(curTime);

        assertEquals(false, cut.checkIfAnyLentBooksAreLate(5555));

    }

    @Test
    void checkIfAnyLentBooksAreLateTest2() throws ParseException, SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        DateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = sdf.parse("18-5-2022 14:10:00");
        long ong = date.getTime();
        Timestamp time = new Timestamp(ong);



        when(str.getUserOldestBook(5555)).thenReturn(time);

        assertEquals(true, cut.checkIfAnyLentBooksAreLate(5555));

    }



    /*
    @Test
    void checkIfAnyLentBooksAreLateTest2() throws ParseException, SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = mock(LibraryManager.class);

        long ong = 100;
        Timestamp time = new Timestamp(ong);
        when(str.getUserOldestBook(5555)).thenReturn(time);

        long curTime = 13000000;
        when(cut.currentTime()).thenReturn(curTime);

        assertEquals(true, cut.checkIfAnyLentBooksAreLate(5555));

    }

     */

    @Test
    void checkIfAnyLentBooksAreLateTest3() throws ParseException, SQLException, UnusableException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        Timestamp time = null;

        when(str.getUserOldestBook(5555)).thenReturn(time);

        assertThrows(UnusableException.class, () -> cut.checkIfAnyLentBooksAreLate(5555));

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
    void checkIfUserShouldBeUnsuspendedTest1() throws ParseException, SQLException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        DateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = sdf.parse("18-5-2022 15:10:00");
        long ong = date.getTime();
        Timestamp time = new Timestamp(ong);



        when(str.getUserSuspensionDate(5555)).thenReturn(time);

        assertEquals(true, cut.checkIfUserShouldBeUnsuspended(5555));

    }

    @Test
    void checkIfUserShouldBeUnsuspendedTest2() throws ParseException, SQLException {
        LibraryStore str = mock(LibraryStore.class);
        LibraryManager cut = new LibraryManager(str);

        DateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = sdf.parse("18-5-2022 16:00:00");
        long ong = date.getTime();
        Timestamp time = new Timestamp(ong);



        when(str.getUserSuspensionDate(5555)).thenReturn(time);

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
        String bannedPN2 = "123456-7890";
        bannedPN.add(bannedPN1);
        bannedPN.add(bannedPN2);

        when(str.getAllUserID()).thenReturn(currentIDs);
        when(str.getUserPersonalNumbers()).thenReturn(currentPN);
        when(str.getBannedUsersPersonalNumber()).thenReturn(bannedPN);

        assertEquals(3, cut.registerUser(2222,"aaaa","bbbb", "128128-0000", 2));
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

        assertEquals(0, cut.registerUser(2222,"aaaa","bbbb", "123456-7890", 2));
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

        assertEquals(1, cut.registerUser(1111,"aaaa","bbbb", "000000-0000", 2));
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

        assertEquals(2, cut.registerUser(9098,"aaaa","bbbb", "010201-1234", 2));
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

        assertThrows(UnusableException.class, () -> cut.registerUser(98,"aaaa","bbbb", "000000-0000", 2));
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

        assertThrows(UnusableException.class, () -> cut.registerUser(10000,"aaaa","bbbb", "000000-0000", 2));
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

        assertThrows(UnusableException.class, () -> cut.registerUser(1000,"aaaa","bbbb", "000000", 2));
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

        assertThrows(UnusableException.class, () -> cut.registerUser(1000,"aaaa","bbbb", "000000-00000", 2));
    }

    @Test
    void registerUserTest9() throws SQLException, UnusableException {
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
