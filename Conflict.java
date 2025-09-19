import java.util.ArrayList;
import java.util.List;

public class Conflict {
    String semester;
    int overlaps;
    int numSecsOfCourse1;
    int numSecsOfCourse2;
    int numSeniors;
    List<String> overlappedStudentIds = new ArrayList<>();

    public Conflict (String semester, String course1Id, String course2Id) {
        this.semester = semester;
        this.overlaps = calculateOverlaps(course1Id, course2Id);
        this.numSecsOfCourse1 = calculateSecsOfCourse(course1Id);
        this.numSecsOfCourse2 = calculateSecsOfCourse(course2Id);
        this.numSeniors = calculateNumOfSeniors();

    }

    private int calculateOverlaps(String course1Id, String course2Id) {
        int overlaps = 0;
        //Get the data of all student ids taking each course and add it to a list.
        //Then iterate through the two list and any that are the same will increment overlap.

        String[] studentIds1;
        String[] studentIds2;

        for (int i = 0; i < studentIds1.length; i++) {
            for (int j = 0; j < studentIds2.length; i++) {
                if (studentIds1[i].equals(studentIds2[j])) {
                    overlaps += 1;
                    overlappedStudentIds.add(studentIds2[j]);
                }
            }
        }

        return overlaps;
    }

    private int calculateSecsOfCourse (String courseId) {
        //Implement pulling of the amount of sections of the course id
    }

    private int calculateNumOfSeniors () {
        //Use overlappedStudentIds then check their graduating date to determine if they
        //are seniors. Increment the numSeniors variable.
    }
}
