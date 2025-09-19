package controller;
import model.*;

import java.util.*;

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
        //Semester comparator to ensure semesters are displayed in the correct order
        Map<String, List<String>> schedule = new TreeMap<>(semesterComparator);

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
                String courseKey = buildCourseKey(p.getCrsID()) + " (planned)";
                schedule.computeIfAbsent(semester, k -> new ArrayList<>()).add(courseKey);
            }
        }
        return schedule;
    }

    private String buildCourseKey(String courseId) {
        return courses.stream()
                .filter(c -> c.getCrsID().equals(courseId))
                .map(c -> c.getDepartmentCode() + " " + c.getCrsNumber())
                .findFirst()
                .orElse(courseId); // Fallback if not found
    }

    private final Comparator<String> semesterComparator = (s1, s2) -> {
        // Expected input example: sp2026
        String sem1 = s1.substring(0, 2).toLowerCase();
        String sem2 = s2.substring(0, 2).toLowerCase();
        int year1 = Integer.parseInt(s1.substring(2));
        int year2 = Integer.parseInt(s2.substring(2));

        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }

        // Same year -> define order
        int rank1 = termOrder(sem1);
        int rank2 = termOrder(sem2);
        return Integer.compare(rank1, rank2);
    };

    private int termOrder(String sem) {
        return switch (sem) {
            case "sp" -> 1;
            case "fa" -> 2;
            default -> 3;
        };
    }
}