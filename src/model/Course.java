package model;

public class Course {
    public final String crsID;
    public final String departmentCode;
    public final String crsNumber;
    public final String title;
    public final int minHours;
    public final int maxHours;

    public Course (String crsID, String departmentCode,
                   String crsNumber, String title, int minHours, int maxHours) {
        this.crsID = crsID;
        this.departmentCode = departmentCode;
        this.crsNumber = crsNumber;
        this.title = title;
        this.minHours = minHours;
        this.maxHours = maxHours;
    }

    public String getCrsID() {
        return crsID;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getCrsNumber() {
        return crsNumber;
    }
    public String getTitle() {
        return title;
    }
    public int getMinHours() {
        return minHours;
    }
    public int getMaxHours() {
        return maxHours;
    }
}

