public class Enrollment {
    public final String stID;
    public final String secID;

    public Enrollment (String stID, String secID) {
        this.secID = secID;
        this.stID = stID;
    }

    public String getStID () {
        return stID;
    }

    public String getSecID() {
        return secID;
    }
}
