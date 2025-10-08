package Screen.Grade;

import Models.Grade;
import Models.Student;
import Models.Subject;
import Screen.AbstractScreen;
import Utils.FileUtil;
import Utils.InputUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnterGradeScreen extends  AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Nhập Điểm Cho Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<String> studentLines = new ArrayList<>();
        List<String> subjectLines = new ArrayList<>();
        List<String> gradeLines = new ArrayList<>();

        try {
            if (FileUtil.fileExists("data/students.txt")) {
                studentLines = FileUtil.readLines("data/students.txt");
            } else {
                System.out.println("Không tìm thấy file học sinh!");
                pause();
                return;
            }
            if (FileUtil.fileExists("data/subjects.txt")){
                subjectLines = FileUtil.readLines("data/subjects.txt");
            } else {
                System.out.println("Không tìm thấy file môn học!");
                pause();
                return;
            }
            if (FileUtil.fileExists("data/grades.txt")) {
                gradeLines = FileUtil.readLines("data/grades.txt");
            } else {
                System.out.println("Không tìm thấy file điểm số!");
                pause();
                return;
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file môn học/điểm số/học sinh: " + e.getMessage());
            pause();
            return;
        }

        String studentID = InputUtil.getNonEmptyString("Mã học sinh: ");
        if (isExistStudentID(studentID,  studentLines)) {
            System.out.println("Không tìm thấy học sinh có mã: " + studentID);
            pause();
            return;
        }
        String subjectID = InputUtil.getNonEmptyString("Mã môn học: ");
        if (!isExistSubjectID(subjectID, subjectLines)) {
            System.out.println("Không tìm thấy môn học có mã: " + subjectID);
            pause();
            return;
        }
        String gradeID = InputUtil.getNonEmptyString("Mã điểm: ");
        if (isExistGradeID(gradeID, gradeLines)) {
            System.out.println("Mã điểm " + subjectID + " đã tồn tại!");
            pause();
            return;
        }
        String gradeType;
        while(true) {
            gradeType = InputUtil.getNonEmptyString("Loại điểm(thuong xuyen/ giua ky/ cuoi ky): ");
            if(gradeType.equalsIgnoreCase("thuong xuyen")|| gradeType.equalsIgnoreCase("giua ky") || gradeType.equalsIgnoreCase("cuoi ky")){
                if(isExistGradeType(gradeType, studentID, gradeLines)) {
                    System.out.println("Loại điểm này đã tồn tại!");
                }
                else {
                    break;
                }
            }
            else {
                System.out.println("Loại điểm không hợp lệ!");
            }
        }
        double score = -1;
        while(score < 0 || score > 10) {
            score = InputUtil.getDouble("Điểm số(0-10): ");
        }
        int gradeSemester = InputUtil.getInt("Học kỳ: ");
        System.out.println("Năm học: ");
        String gradeSchoolYear = schoolYearInput();
        LocalDate inputDate =  LocalDate.now();
        String gradeNote = InputUtil.getString("Ghi chú: ");

        Grade newGrade = new Grade(gradeID, studentID, subjectID, gradeType, score, gradeSemester, gradeSchoolYear, inputDate, gradeNote);
        gradeLines.add(newGrade.toString());
        // Save file
        try {
            FileUtil.writeLines("data/grades.txt", gradeLines);
            System.out.println("\nĐã lưu điểm thành công!");
            pause();
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file: " + e.getMessage());
        }
    }

    public static boolean isExistStudentID(String studentID, List<String> studentLines) {
        for (String line : studentLines) {
            Student student = Student.fromString(line);
            if (student != null && student.getId().equalsIgnoreCase(studentID)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isExistSubjectID(String subjectID, List<String> subjectLines) {
        for (String line : subjectLines) {
            Subject subject = Subject.fromString(line);
            if (subject != null && subject.getSubjectID().equalsIgnoreCase(subjectID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExistGradeID(String gradeID, List<String> gradeLines) {
        for (String line : gradeLines) {
            Grade grade = Grade.fromString(line);
            if (grade != null && grade.getGradeId().equalsIgnoreCase(gradeID)) {
                return true;
            }
        }
        return false;
    }
    public static Grade getGradeByID(String gradeID, List<String> gradeLines) {
        for (String line : gradeLines) {
            Grade grade = Grade.fromString(line);
            if (grade != null && grade.getGradeId().equalsIgnoreCase(gradeID)) {
                return grade;
            }
        }
        return null;
    }
    public static String schoolYearInput(){
        int start = InputUtil.getInt("Năm bắt đầu: ");
        int end = InputUtil.getInt("Năm kết thúc: ");
        return start + " - " + end;
    }
    public static boolean isExistGradeType(String gradeType, String studentID, List<String> gradeLines) {
        for (String line : gradeLines) {
            Grade grade = Grade.fromString(line);
            if (grade != null && grade.getStudentId().equalsIgnoreCase(studentID) && grade.getGradeType().equalsIgnoreCase(gradeType)) {
                return true;
            }
        }
        return false;
    }
}