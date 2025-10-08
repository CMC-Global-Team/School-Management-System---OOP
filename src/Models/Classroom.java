package Models;

public class Classroom implements IEntity {
    private String classId;
    private String className;
    private String schoolYear;
    private String course; // niên khóa

    public Classroom() {
    }

    public Classroom(String classId, String className, String schoolYear, String course) {
        this.classId = classId;
        this.className = className;
        this.schoolYear = schoolYear;
        this.course = course;
    }

    // Getter & Setter
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getSchoolYear() { return schoolYear; }
    public void setSchoolYear(String schoolYear) { this.schoolYear = schoolYear; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return "Class: " + classId + " - " + className +
                ", Year: " + schoolYear +
                ", Course: " + course;
    }
    
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