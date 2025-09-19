package model;

public class Section {
    public final String id;
    public final String code;
    public final int crsNum;
    public final int secNum;
    public final String sem;

    public Section (String id, String code, int crsNum, int secNum, String sem) {
        this.id = id;
        this.code = code;
        this.crsNum = crsNum;
        this.secNum = secNum;
        this.sem = sem;
    }

    public String getID() {
        return id;
    }
    public String getCode() {
        return code;
    }
    public int getCrsNum() {
        return crsNum;
    }
    public int getSecNum() {
        return secNum;
    }
    public String getSem() {
        return sem;
    }
}
