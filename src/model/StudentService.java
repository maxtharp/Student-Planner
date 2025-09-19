package model;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    private final List<Student> students;
    private final List<Course> courses;
    private final List<Section> sections;
    private final List<Enrollment> enrollments;
    private final List<Planned> planned;

    public StudentService(DataLoader loader) {
        this.students = loader.loadStudents("csv/student.csv");
        this.courses = loader.loadCourses("csv/course.csv");
        this.sections = loader.LoadSection("csv/section.csv");
        this.enrollments = loader.LoadEnrollment("csv/enrollment.csv");
        this.planned = loader.loadPlanned("csv/planned.csv");
    }

    public Optional<Student> findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Map<String, List<String>> getStudentSchedule(String studentId) {
        Map<String, List<String>> schedule = new TreeMap<>();

        // Past/current enrollments
        for (Enrollment e : enrollments) {
            if (e.getStdID().equals(studentId)) {
                sections.stream()
                        .filter(sec -> sec.getID().equals(e.getSecID()))
                        .findFirst()
                        .ifPresentOrElse(sec -> {
                            String semester = sec.getSem();
                            String courseKey = sec.getCode() + " " + sec.getCrsNum();
                            schedule.computeIfAbsent(semester, k -> new ArrayList<>()).add(courseKey);
                        }, () -> {
                            System.out.println("No section found for enrollment secId=" + e.getSecID());
                        });
            }
        }

        // Planned future courses
        for (Planned p : planned) {
            if (p.getStID().equals(studentId)) {
                String semester = p.getSem();
                String courseKey = p.getCrsID();
                schedule.computeIfAbsent(semester, k -> new ArrayList<>()).add(courseKey);
            }
        }

        return schedule;
    }
}