package Models;

import java.util.*;
import java.util.stream.Collectors;

public class Subject implements IEntity {
    private String subjectID;
    private String subjectName;
    private int lessonCount;
    private double confficient;
    private String subjectType;
    private String description;
    private List<String> teachersInCharge;
    private String status;

    public Subject() {
        teachersInCharge = new ArrayList<>();
    }

    public Subject(String subjectID, String subjectName, int lessonCount, double confficient,
                   String subjectType, String description, List<String> teachersInCharge, String status) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.lessonCount = lessonCount;
        this.confficient = confficient;
        this.subjectType = subjectType;
        this.description = description;
        this.teachersInCharge = teachersInCharge != null ? teachersInCharge : new ArrayList<>();
        this.status = status;
    }

    // Getter & Setter
    public String getSubjectID() { return subjectID; }
    public void setSubjectID(String subjectID) { this.subjectID = subjectID; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getLessonCount() { return lessonCount; }
    public void setLessonCount(int lessonCount) { this.lessonCount = lessonCount; }

    public double getConfficient() { return confficient; }
    public void setConfficient(double confficient) { this.confficient = confficient; }

    public String getSubjectType() { return subjectType; }
    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getTeachersInCharge() { return teachersInCharge; }
    public void setTeachersInCharge(List<String> teachersInCharge) {
        this.teachersInCharge = teachersInCharge != null ? teachersInCharge : new ArrayList<>();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String getId() { return subjectID; }

    // Lưu ra file CSV: dùng '|' để phân tách nhiều giáo viên
    @Override
    public String toFileString() {
        String teachers = String.join("|", teachersInCharge); // dùng '|'
        return subjectID + "," + subjectName + "," + lessonCount + "," + confficient + "," +
                subjectType + "," + description + "," + teachers + "," + status;
    }

    @Override
    public boolean validate() {
        if (subjectID == null || subjectID.isEmpty()) return false;
        if (subjectName == null || subjectName.isEmpty()) return false;
        if (lessonCount < 0) return false;
        if (confficient < 0) return false;
        return true;
    }
    public static Subject fromString(String line) {
        String[] parts = line.split(",", 8); // phân tách 8 phần
        if (parts.length != 8) return null;
        try {
            String teacherStr = parts[6].replaceAll("\\[|\\]", ""); // loại bỏ '[' và ']'
            List<String> teacherList = teacherStr.isEmpty() ? new ArrayList<>() :
                    Arrays.stream(teacherStr.split("\\|")) // hoặc split theo ',' nếu file cũ dùng dấu ','
                            .map(String::trim)
                            .collect(Collectors.toList());

            return new Subject(
                    parts[0],
                    parts[1],
                    Integer.parseInt(parts[2]),
                    Double.parseDouble(parts[3]),
                    parts[4],
                    parts[5],
                    teacherList,
                    parts[7]
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        String teachers = String.join(", ", teachersInCharge);
        return "Subject{" +
                "ID='" + subjectID + '\'' +
                ", Name='" + subjectName + '\'' +
                ", Lessons=" + lessonCount +
                ", Coefficient=" + confficient +
                ", Type='" + subjectType + '\'' +
                ", Description='" + description + '\'' +
                ", Teachers=[" + teachers + "]" +
                ", Status='" + status + '\'' +
                '}';
    }
}
