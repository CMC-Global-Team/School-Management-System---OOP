package Models;

public class Teacher extends Person implements IEntity {
    private String teacherSubject;
    private String teacherDegree;
    private int teacherExperience;
    private String teacherEmail;
    private String teacherPhone;
    private String teacherHomeroom;

    public Teacher() {}

    public Teacher(String id, String name, String status,
                   String teacherSubject, String teacherDegree, int teacherExperience,
                   String teacherEmail, String teacherPhone, String teacherHomeroom) {
        super(id, name, status);
        this.teacherSubject = teacherSubject;
        this.teacherDegree = teacherDegree;
        this.teacherExperience = teacherExperience;
        this.teacherEmail = teacherEmail;
        this.teacherPhone = teacherPhone;
        this.teacherHomeroom = teacherHomeroom;
    }

    // Getter & Setter
    public String getTeacherSubject() { return teacherSubject; }
    public void setTeacherSubject(String teacherSubject) { this.teacherSubject = teacherSubject; }

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
    public String getId() {
        return id;
    }

    // Xuất ra File
    @Override
    public String toString() {
        return id + "," + name + "," + teacherSubject + "," + teacherDegree + "," +
                teacherExperience + "," + teacherEmail + "," + teacherPhone + "," +
                teacherHomeroom + "," + status;
    }

    // Phân tích từ file về Object
    public static Teacher fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 9) return null;
        try {
            int exp = 0;
            try{
                exp = Integer.parseInt(parts[4]);
            } catch(NumberFormatException e) {
                exp = 0;
            }
            return new Teacher(
                    parts[0], // Teacher's ID
                    parts[1], // Teacher's Name
                    parts[8], // Teacher's Status
                    parts[2], // Teacher's Subject
                    parts[3], // Teacher's degree
                    exp, // Teacher's experience
                    parts[5], // Teacher's email
                    parts[6], // Teacher's phone
                    parts[7]  // Teacher's homeroom
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toFileString() {
        return id + "," + name + "," + teacherSubject + "," + teacherDegree + "," + teacherExperience + "," +
                teacherEmail + "," + teacherPhone + "," + teacherHomeroom + "," + status;
    }

    @Override
    public boolean validate() {
        if (id == null || id.trim().isEmpty()) return false;
        if (name == null || name.trim().isEmpty()) return false;
        return true;
    }
}