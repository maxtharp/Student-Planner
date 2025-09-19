package model;

public class Planned {
    public final String crsID;
    public final String stID;
    public final String sem;

    public Planned(String crsID, String stID, String sem) {
        this.crsID = crsID;
        this.stID = stID;
        this.sem = sem;
    }

    public String getCrsID() {
        return crsID;
    }

    public String getStID() {
        return stID;
    }
    public String getSem() {
        return sem;
    }
}

