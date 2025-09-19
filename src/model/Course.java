package model;

public class Course {
    public final String id;
    public final String department;
    public final String number;
    public final String title;
    public final int credits;

    public Course (String id, String department,
                   String number, String title, int credits) {
        this.id = id;
        this.department = department;
        this.number = number;
        this.title = title;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }
    public String getDepartment() {
        return department;
    }

    public String getNumber() {
        return number;
    }
    public String getTitle() {
        return title;
    }
    public int getCredits() {
        return credits;
    }
}

