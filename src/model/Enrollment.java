package model;

public class Enrollment {

    private final String stdID;
    private final String secID;

    public Enrollment(String stdID, String secID) {
        this.stdID = stdID;
        this.secID = secID;
    }

    public String getSecID() {
        return secID;
    }

    public String getStdID() {
        return stdID;
    }
}
