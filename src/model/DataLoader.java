package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<Student> loadStudents(String path) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String id = tokens[0].replace("'", "").trim();
                String name = tokens[1].replace("'", "").trim();
                String email = tokens[2].replace("'", "").trim();
                String gradDate = tokens[3].replace("'", "").trim();
                students.add(new Student(id, name, email, gradDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Course> loadCourses(String path) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String id = tokens[0].replace("'", "").trim();
                String dept = tokens[1].replace("'", "").trim();
                String num = tokens[2].replace("'", "").trim();
                String title = tokens[3].replace("'", "").trim();
                int minHrs = Integer.parseInt(tokens[4].replace("'", "").trim());
                int maxHrs = Integer.parseInt(tokens[5].replace("'", "").trim());
                courses.add(new Course(id, dept, num, title, minHrs, maxHrs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }
    public List<Planned> loadPlanned(String path) {
        List<Planned> planned = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String crsID = tokens[0].replace("'", "").trim();
                String stdID = tokens[1].replace("'", "").trim();
                String sem = tokens[2].replace("'", "").trim();
                planned.add(new Planned(crsID, stdID, sem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planned;
    }
    public List<Offering> loadOffering(String path) {
        List<Offering> offerings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String crsID = tokens[0].replace("'", "").trim();
                String code = tokens[1].replace("'", "").trim();
                offerings.add(new Offering(crsID, code));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offerings;
    }

    public List<Section> LoadSection(String path) {
        List<Section> sections = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String secID = tokens[0].replace("'", "").trim();
                String deptCode = tokens[1].replace("'", "").trim();
                int crsNum = Integer.parseInt(tokens[2].replace("'", "").trim());
                int secNum = Integer.parseInt(tokens[3].replace("'", "").trim());
                String sem = tokens[4].replace("'", "").trim();
                sections.add(new Section(secID, deptCode, crsNum, secNum, sem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sections;
    }

    public List<Enrollment> LoadEnrollment(String path) {
        List<Enrollment> enrollments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String stdID = tokens[0].replace("'", "").trim();
                String secID = tokens[1].replace("'", "").trim();
                enrollments.add(new Enrollment(stdID, secID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrollments;
    }
}