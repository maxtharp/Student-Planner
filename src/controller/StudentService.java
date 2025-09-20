package controller;
import model.*;

import java.util.*;

public class StudentService {

    private final List<Student> students;
    private final List<Course> courses;
    private final List<Section> sections;
    private final List<Enrollment> enrollments;
    private final List<Planned> planned;
    private final List<Offering> offerings;

    public StudentService(DataLoader loader) {
        this.students = loader.loadStudents("csv/student.csv");
        this.courses = loader.loadCourses("csv/course.csv");
        this.sections = loader.LoadSection("csv/section.csv");
        this.enrollments = loader.LoadEnrollment("csv/enrollment.csv");
        this.planned = loader.loadPlanned("csv/planned.csv");
        this.offerings = loader.loadOffering("csv/offering.csv");

    }

    public Optional<Student> findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Function to filter through specific students classes
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

    // Function to filter through available courses based on when they are offered
    public List<String> getOfferedCoursesForSemester(String semesterCode) {
        int year = Integer.parseInt(semesterCode.replaceAll("\\D+", ""));
        boolean isEvenYear = year % 2 == 0;

        boolean isSpring = semesterCode.startsWith("spr");
        boolean isFall = semesterCode.startsWith("fall");

        return offerings.stream()
                .filter(off -> isOffered(off.getCode(), isSpring, isFall, isEvenYear))
                .map(off -> {
                    // map offering.courseId -> real course key
                    Course c = courses.stream()
                            .filter(course -> course.getCrsID().equals(off.getID()))
                            .findFirst().orElse(null);
                    return (c != null) ? (c.getDepartmentCode() + " " + c.getCrsNumber()) : null;
                })
                .filter(Objects::nonNull)
                .toList();
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

    public int termOrder(String sem) {
        return switch (sem) {
            case "sp" -> 1;
            case "su" ->2;
            case "fa" -> 3;
            default -> 4;
        };
    }

    private boolean isOffered(String code, boolean isSpring, boolean isFall, boolean isEvenYear) {
        return switch (code) {
            case "e" -> true;
            case "sp" -> isSpring;
            case "ef" -> isFall;
            case "fo" -> isFall && !isEvenYear;
            case "fe" -> isFall && isEvenYear;
            case "so" -> isSpring && !isEvenYear;
            case "se" -> isSpring && isEvenYear;
            default -> false;
        };
    }

}