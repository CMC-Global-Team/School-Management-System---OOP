package Models;

import java.time.LocalDate;

public class Grade implements IEntity {
    private String gradeId;
    private String studentId;
    private String subjectId;
    private String gradeType;
    private double score;
    private int semester;
    private String schoolYear;
    private LocalDate inputDate;
    private String note;

    public Grade() {
    }


    public Grade(String gradeId, String studentId, String subjectId, String gradeType,
                 double score, int semester, String schoolYear, LocalDate inputDate, String note) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.gradeType = gradeType;
        this.score = score;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.inputDate = inputDate;
        this.note = note;
    }


    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public LocalDate getInputDate() {
        return inputDate;
    }

    public void setInputDate(LocalDate inputDate) {
        this.inputDate = inputDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public static Grade fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = line.split(",");
        if (parts.length != 9) {
            System.err.println("Lỗi parse Grade: Số phần tử không đúng. Cần 9 phần tử, nhận được " + parts.length);
            return null;
        }
        
        try {
            return new Grade(
                    parts[0].trim(),
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    Double.parseDouble(parts[4].trim()),
                    Integer.parseInt(parts[5].trim()),
                    parts[6].trim(),
                    LocalDate.parse(parts[7].trim()),
                    parts[8].trim()
            );
        } catch (Exception e) {
            System.err.println("Lỗi parse Grade: " + e.getMessage());
            return null;
        }
    }

    public String toString(){
        return "Mã điểm: " + gradeId + ", Học sinh: " + studentId + ", Môn học: " + subjectId +
                ", Loại điểm: " + gradeType + ", Điểm: " + score + ", Kỳ học: " + semester + "Năm học: " + schoolYear;
    }
    @Override
    public String getId() {
        return gradeId;
    }

    @Override
    public String toFileString() {
        // Format: gradeId,studentId,subjectId,gradeType,score,semester,schoolYear,inputDate,note
        return gradeId + "," + studentId + "," + subjectId + "," + gradeType + "," +
                score + "," + semester + "," + schoolYear + "," + inputDate + "," + (note != null ? note : "");
    }

    @Override
    public boolean validate() {
        if (gradeId == null || gradeId.trim().isEmpty()) {
            return false;
        }
        if (subjectId == null || subjectId.trim().isEmpty()) {
            return false;
        }
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
