package Models;

import java.util.*;
import java.util.stream.Collectors;

public class Teacher extends Person implements IEntity {
    private List<String> teacherSubjects;
    private String teacherDegree;
    private int teacherExperience;
    private String teacherEmail;
    private String teacherPhone;
    private String teacherHomeroom;

    public Teacher() {
        teacherSubjects = new ArrayList<>();
    }

    public Teacher(String id, String name, String status,
                   List<String> teacherSubjects, String teacherDegree, int teacherExperience,
                   String teacherEmail, String teacherPhone, String teacherHomeroom) {
        super(id, name, status);
        this.teacherSubjects = teacherSubjects != null ? teacherSubjects : new ArrayList<>();
        this.teacherDegree = teacherDegree;
        this.teacherExperience = teacherExperience;
        this.teacherEmail = teacherEmail;
        this.teacherPhone = teacherPhone;
        this.teacherHomeroom = teacherHomeroom;
    }

    // Getter & Setter
    public List<String> getTeacherSubjects() { return teacherSubjects; }
    public void setTeacherSubjects(List<String> teacherSubjects) {
        this.teacherSubjects = teacherSubjects != null ? teacherSubjects : new ArrayList<>();
    }

    public String getTeacherDegree() { return teacherDegree; }
    public void setTeacherDegree(String teacherDegree) { this.teacherDegree = teacherDegree; }

    public int getTeacherExperience() { return teacherExperience; }
    public void setTeacherExperience(int teacherExperience) { this.teacherExperience = teacherExperience; }

    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }

    public String getTeacherPhone() { return teacherPhone; }
    public void setTeacherPhone(String teacherPhone) { this.teacherPhone = teacherPhone; }

    public String getTeacherHomeroom() { return teacherHomeroom; }
    public void setTeacherHomeroom(String teacherHomeroom) { this.teacherHomeroom = teacherHomeroom; }

    @Override
    public String getId() { return id; }

    // Xuất ra file
    @Override
    public String toFileString() {
        String subjects = String.join("|", teacherSubjects);
        return id + "," + name + "," + subjects + "," + teacherDegree + "," + teacherExperience + "," +
                teacherEmail + "," + teacherPhone + "," + teacherHomeroom + "," + status;
    }
    public static Teacher fromString(String line) {
        String[] parts = line.split(",", 9);
        if (parts.length != 9) return null;
        try {
            String subjectStr = parts[2];
            List<String> subjects = subjectStr.isEmpty() ? new ArrayList<>() :
                    Arrays.stream(subjectStr.split("\\|")) // dùng | để phân tách nhiều môn
                            .map(String::trim)
                            .collect(Collectors.toList());
            int exp = 0;
            try { exp = Integer.parseInt(parts[4]); } catch (NumberFormatException e) { exp = 0; }

            return new Teacher(
                    parts[0],  // id
                    parts[1],  // name
                    parts[8],  // status
                    subjects,  // teacherSubjects
                    parts[3],  // degree
                    exp,       // experience
                    parts[5],  // email
                    parts[6],  // phone
                    parts[7]   // homeroom
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return id + "," + name + "," + teacherSubjects + "," + teacherDegree + "," + teacherExperience + "," +
                teacherEmail + "," + teacherPhone + "," + teacherHomeroom + "," + status;
    }

    @Override
    public boolean validate() {
        return id != null && !id.trim().isEmpty()
                && name != null && !name.trim().isEmpty();
    }
}
