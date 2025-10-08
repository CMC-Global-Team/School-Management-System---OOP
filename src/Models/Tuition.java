package Models;

import java.time.LocalDate;

/**
 * Tuition - Mô hình hóa dữ liệu học phí cho hệ thống quản lý trường học
 * Implement IEntity để tích hợp với Repository pattern
 */
public class Tuition implements IEntity {
    private String tuitionId;
    private String studentId;
    private int semester;
    private String schoolYear;
    private double amount;
    private LocalDate paymentDate;
    private String method;
    private String status;
    private String note;

    public Tuition() {
    }

    public Tuition(String tuitionId, String studentId, int semester, String schoolYear,
                   double amount, LocalDate paymentDate, String method, String status, String note) {
        this.tuitionId = tuitionId;
        this.studentId = studentId;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = status;
        this.note = note;
    }

    // Getter và Setter
    public String getTuitionId() {
        return tuitionId;
    }

    public void setTuitionId(String tuitionId) {
        this.tuitionId = tuitionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Implement IEntity
    @Override
    public String getId() {
        return this.tuitionId;
    }

    @Override
    public String toFileString() {
        return tuitionId + "," + studentId + "," + semester + "," + schoolYear + "," +
                amount + "," + paymentDate + "," + method + "," + status + "," + note;
    }

    @Override
    public boolean validate() {
        // Validate cơ bản: ID không được để trống, semester > 0, amount >= 0, paymentDate không null
        if (tuitionId == null || tuitionId.isEmpty()) return false;
        if (studentId == null || studentId.isEmpty()) return false;
        if (semester <= 0) return false;
        if (amount < 0) return false;
        if (paymentDate == null) return false;
        return true;
    }

    // Phương thức tiện ích từString
    public static Tuition fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 9) return null;
        try {
            return new Tuition(
                    parts[0],
                    parts[1],
                    Integer.parseInt(parts[2]),
                    parts[3],
                    Double.parseDouble(parts[4]),
                    LocalDate.parse(parts[5]),
                    parts[6],
                    parts[7],
                    parts[8]
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Tuition{" +
                "ID='" + tuitionId + '\'' +
                ", StudentID='" + studentId + '\'' +
                ", Semester=" + semester +
                ", SchoolYear='" + schoolYear + '\'' +
                ", Amount=" + amount +
                ", PaymentDate=" + paymentDate +
                ", Method='" + method + '\'' +
                ", Status='" + status + '\'' +
                ", Note='" + note + '\'' +
                '}';
    }
}