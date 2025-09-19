package model;

public class Student {
    public final String stdID;
    public final String name;
    public final String email;
    public final String date;

    public Student (String id, String name, String email, String date) {
        this.stdID = id;
        this.name = name;
        this.email = email;
        this.date = date;
    }

    public String getID() {
        return stdID;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getDate () {
        return date;
    }
}

