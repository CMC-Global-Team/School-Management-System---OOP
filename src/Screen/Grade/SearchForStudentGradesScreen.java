package Screen.Grade;

import Models.Grade;
import Models.Student;
import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
import Utils.InputUtil;
import java.util.List;

public class SearchForStudentGradesScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Tìm Kiếm Điểm Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nChọn cách tìm kiếm:");
        System.out.println("1. Tìm theo mã học sinh");
        System.out.println("2. Tìm theo tên học sinh");
        System.out.println("3. Tìm theo mã môn học");
        System.out.println("4. Tìm theo từ khóa chung");
        
        int choice = InputUtil.getInt("Lựa chọn (1-4): ");
        
        switch (choice) {
            case 1:
                searchByStudentId();
                break;
            case 2:
                searchByStudentName();
                break;
            case 3:
                searchBySubjectId();
                break;
            case 4:
                searchByKeyword();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
        pause();
    }
    
    private void searchByStudentId() {
        String studentId = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
        
        if (!StudentService.getInstance().isStudentIdExists(studentId)) {
            System.out.println("Không tìm thấy học sinh có mã: " + studentId);
            return;
        }
        
        List<Grade> grades = GradeServices.getInstance().getAllGradeByStudentID(studentId);
        displayGradesWithStudentInfo(grades, studentId);
    }
    
    private void searchByStudentName() {
        String studentName = InputUtil.getNonEmptyString("Nhập tên học sinh: ");
        
        List<Student> students = StudentService.getInstance().searchStudents(studentName);
        if (students.isEmpty()) {
            System.out.println("Không tìm thấy học sinh có tên: " + studentName);
            return;
        }
        
        for (Student student : students) {
            List<Grade> grades = GradeServices.getInstance().getAllGradeByStudentID(student.getId());
            displayGradesWithStudentInfo(grades, student.getId());
        }
    }
    
    private void searchBySubjectId() {
        String subjectId = InputUtil.getNonEmptyString("Nhập mã môn học: ");
        String studentId = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
        
        List<Grade> grades = GradeServices.getInstance().getAllGradeBySubjectID(subjectId, studentId);
        displayGradesWithStudentInfo(grades, studentId);
    }
    
    private void searchByKeyword() {
        String keyword = InputUtil.getNonEmptyString("Nhập từ khóa: ");
        GradeServices.getInstance().displaySearchResults(keyword);
    }
    
    private void displayGradesWithStudentInfo(List<Grade> grades, String studentId) {
        if (grades.isEmpty()) {
            System.out.println("Không tìm thấy điểm nào cho học sinh: " + studentId);
            return;
        }
        
        Student student = StudentService.getInstance().findById(studentId).orElse(null);
        if (student != null) {
            System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                           THÔNG TIN HỌC SINH                              │");
            System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã HS: %-10s │ Tên: %-30s │ Lớp: %-15s │%n", 
            student.getId(), student.getName(), student.getClassName());
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        }
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                              DANH SÁCH ĐIỂM                                │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-8s │ %-8s │ %-12s │ %-8s │ %-6s │ %-8s │ %-12s │%n", 
            "Mã điểm", "Mã môn", "Loại điểm", "Điểm", "Kỳ", "Năm học", "Ngày nhập");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        for (Grade grade : grades) {
            System.out.printf("│ %-8s │ %-8s │ %-12s │ %-8.1f │ %-6d │ %-8s │ %-12s │%n",
                grade.getGradeId(),
                grade.getSubjectId(),
                grade.getGradeType(),
                grade.getScore(),
                grade.getSemester(),
                grade.getSchoolYear(),
                grade.getInputDate()
            );
        }
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
}
