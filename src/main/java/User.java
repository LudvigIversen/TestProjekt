public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String personalNumber;
    private int level;
    private int suscounter;
    private boolean suspended;
    private int books;

    public User(int userID, String firstName, String lastName, String personalNumber, int level, int suscounter, boolean suspended) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.level = level;
        this.suscounter = suscounter;
        this.suspended = suspended;

        if (level == 1) {
            books = 3;
        } else if (level == 2) {
            books = 5;
        } else if (level == 3) {
            books = 7;
        } else if (level == 4) {
            books = 10;
        }

    }

    @Override
    public String toString() {
        String niva = "";
        if (level == 1) {
            niva = "student";
        } else if (level == 2) {
            niva = "master";
        } else if (level == 3) {
            niva = "doktorand";
        } else if (level == 4) {
            niva = "lärare";
        }
        return
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", level=" + niva +
                ", suscounter=" + suscounter +
                ", suspended=" + suspended;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSuscounter() {
        return suscounter;
    }

    public void setSuscounter(int suscounter) {
        this.suscounter = suscounter;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getBooks() {
        return books;
    }

    public void setBooks(int books) {
        this.books = books;
    }


}
