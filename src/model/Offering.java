package model;

public class Offering {
    public final String id;
    public final String code;

    public Offering (String id, String code) {
        this.id = id;
        this.code = code;
    }
     public String getID () {
        return id;
     }

     public String getCode() {
         return code;
     }
}
