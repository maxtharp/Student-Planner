package model;

public class Offering {
    public final String crsID;
    public final String code;

    public Offering (String id, String code) {
        this.crsID = id;
        this.code = code;
    }
     public String getID () {
        return crsID;
     }

     public String getCode() {
         return code;
     }
}
