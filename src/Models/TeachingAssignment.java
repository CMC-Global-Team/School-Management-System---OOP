package Models;

public class TeachingAssignment {
    private String teacherId;
    private String teacherName;
    private String subject;
    private String className;

    public TeachingAssignment() {}

    public TeachingAssignment(String teacherId, String teacherName, String subject, String className) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subject = subject;
        this.className = className;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return teacherId + "," + teacherName + "," + subject + "," + className;
    }

    public static TeachingAssignment fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) return null;
        return new TeachingAssignment(parts[0], parts[1], parts[2], parts[3]);
    }
}