package Models;

public class Classroom implements IEntity {
    private String classId;
    private String className;
    private String schoolYear;
    private String course; // niên khóa

    // ===== Implement IEntity Interface =====
    
    @Override
    public String getId() {
        return classId;
    }
    
    @Override
    public String toFileString() {
        // Format: classId,className,schoolYear,course
        return classId + "," + className + "," + schoolYear + "," + course;
    }
    
    @Override
    public boolean validate() {
        if (classId == null || classId.trim().isEmpty()) {
            return false;
        }
        if (className == null || className.trim().isEmpty()) {
            return false;
        }
        if (schoolYear == null || schoolYear.trim().isEmpty()) {
            return false;
        }
        if (course == null || course.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
}