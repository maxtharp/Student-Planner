public class Planned {
    public final String id;
    public final String stID;
    public final String sem;

    public Planned (String id, String stID, String sem) {
        this.id = id;
        this.stID = stID;
        this.sem = sem;
    }

    public String getID () {
        return id;
    }

    public String getStID () {
        return stID;
    }

    public String getSem (){
        return sem;
    }
}
