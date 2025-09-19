public class ConflictCalc {
    String semester;
    String[] coursesOffered;
    int numCourses;
    Double[][] conflictMatrix;

    public ConflictCalc (String semester){
        this.semester = semester;
        this.coursesOffered = placeboMethod;
        //Make a method somewhere to pull the coursesOffered in a given semester
        this.numCourses = coursesOffered.length;
        this.conflictMatrix = new Double[numCourses][numCourses];
    }

    private void calculateConflicts() {
        double conflictScore;
        int overlap;
        for (int i = 0; i < numCourses; i++) {
            for (int j = 0; j < numCourses; j++) {
                Conflict conflict = new Conflict(semester, coursesOffered[i], coursesOffered[j]);

                //Calculate conflict score here. Working on implementation of that calculation

                conflictMatrix[i][j] = conflictScore;

            }
        }
    }


}
