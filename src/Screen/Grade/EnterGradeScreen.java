package Screen.Grade;

import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
import Services.SubjectService;
import Utils.InputUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EnterGradeScreen extends AbstractScreen {
    
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Nhập Điểm Cho Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã điểm: Dxxxx (VD: D0001, D0002...) - Phải duy nhất trong hệ thống    │");
        System.out.println("│ Mã học sinh: BSx (VD: BS2, BS3...) - Phải tồn tại trong hệ thống      │");
        System.out.println("│ Mã môn học: Math01, Phys01... - Phải tồn tại trong hệ thống            │");
        System.out.println("│ Loại điểm: 1-Thường xuyên, 2-Giữa kỳ, 3-Cuối kỳ                       │");
        System.out.println("│ Điểm số: 0.0-10.0 (VD: 8.5, 9.0)                                      │");
        System.out.println("│ Học kỳ: 1 hoặc 2                                                        │");
        System.out.println("│ Năm học: YYYY-YYYY (VD: 2024-2025)                                      │");
        System.out.println("│ Ngày nhập: dd/MM/yyyy (VD: 15/10/2024) - Mặc định hôm nay              │");
        System.out.println("│ Ghi chú: Tùy chọn, có thể để trống                                      │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    NHẬP ĐIỂM CHO HỌC SINH");
        System.out.println("=".repeat(50));
        
        GradeServices gradeService = GradeServices.getInstance();
        StudentService studentService = StudentService.getInstance();
        SubjectService subjectService = SubjectService.getInstance();
        
        // Kiểm tra có dữ liệu không
        if (studentService.getTotalStudents() == 0) {
            System.out.println("Không có dữ liệu học sinh để nhập điểm!");
            pause();
            return;
        }
        
        if (subjectService.getTotalSubjects() == 0) {
            System.out.println("Không có dữ liệu môn học để nhập điểm!");
            pause();
            return;
        }
        
        // Hiển thị danh sách học sinh và môn học để tham khảo
        displayStudentList();
        displaySubjectList();
        
        // Nhập mã điểm với validation
        String gradeID = null;
        while (gradeID == null || gradeID.trim().isEmpty() || !gradeID.matches("^D\\d{4}$")) {
            if (gradeID != null && !gradeID.trim().isEmpty() && !gradeID.matches("^D\\d{4}$")) {
                System.out.println("Mã điểm sai định dạng! (VD: D0001)");
            }
            gradeID = InputUtil.getNonEmptyString("Mã điểm (Dxxxx): ");
        }
        
        // Kiểm tra mã điểm đã tồn tại chưa
        while (gradeService.isGradeIdExists(gradeID)) {
            System.out.println("Mã điểm '" + gradeID + "' đã tồn tại trong hệ thống!");
            gradeID = InputUtil.getNonEmptyString("Nhập mã điểm khác (Dxxxx): ");
        }
        
        // Nhập mã học sinh với validation
        String studentID = null;
        while (studentID == null || studentID.trim().isEmpty() || !studentService.isStudentIdExists(studentID)) {
            if (studentID != null && !studentID.trim().isEmpty() && !studentService.isStudentIdExists(studentID)) {
                System.out.println("Không tìm thấy học sinh có mã '" + studentID + "' trong hệ thống!");
            }
            studentID = InputUtil.getNonEmptyString("Mã học sinh: ");
        }
        
        // Nhập mã môn học với validation
        String subjectID = null;
        while (subjectID == null || subjectID.trim().isEmpty() || !subjectService.isSubjectIdExists(subjectID)) {
            if (subjectID != null && !subjectID.trim().isEmpty() && !subjectService.isSubjectIdExists(subjectID)) {
                System.out.println("Không tìm thấy môn học có mã '" + subjectID + "' trong hệ thống!");
            }
            subjectID = InputUtil.getNonEmptyString("Mã môn học: ");
        }
        
        // Nhập loại điểm với validation
        int gradeTypeChoice = 0;
        while (gradeTypeChoice < 1 || gradeTypeChoice > 3) {
            if (gradeTypeChoice != 0) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn 1, 2 hoặc 3.");
            }
            System.out.println("\nLoại điểm:");
            System.out.println("1 - Thường xuyên");
            System.out.println("2 - Giữa kỳ");
            System.out.println("3 - Cuối kỳ");
            gradeTypeChoice = InputUtil.getInt("Chọn loại điểm (1-3): ");
        }
        
        String gradeType = "";
        switch (gradeTypeChoice) {
            case 1 -> gradeType = "thuong xuyen";
            case 2 -> gradeType = "giua ky";
            case 3 -> gradeType = "cuoi ky";
        }
        
        // Nhập điểm số với validation
        double score = -1;
        while (score < 0 || score > 10) {
            if (score != -1) {
                System.out.println("Điểm số phải trong khoảng 0.0 - 10.0!");
            }
            score = InputUtil.getDouble("Điểm số (0.0-10.0): ");
        }
        
        // Nhập học kỳ với validation
        int semester = 0;
        while (semester < 1 || semester > 2) {
            if (semester != 0) {
                System.out.println("Học kỳ phải là 1 hoặc 2!");
            }
            semester = InputUtil.getInt("Học kỳ (1 hoặc 2): ");
        }
        
        // Nhập năm học với validation
        String schoolYear = null;
        while (schoolYear == null || schoolYear.trim().isEmpty() || !schoolYear.matches("^\\d{4}-\\d{4}$")) {
            if (schoolYear != null && !schoolYear.trim().isEmpty() && !schoolYear.matches("^\\d{4}-\\d{4}$")) {
                System.out.println("Năm học sai định dạng! (VD: 2024-2025)");
            }
            schoolYear = InputUtil.getNonEmptyString("Năm học (YYYY-YYYY): ");
        }
        
        // Kiểm tra năm học hợp lệ
        String[] parts = schoolYear.split("-");
        int startYear = Integer.parseInt(parts[0]);
        int endYear = Integer.parseInt(parts[1]);
        while (startYear > endYear) {
            System.out.println("Năm học không hợp lệ! Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc");
            schoolYear = InputUtil.getNonEmptyString("Nhập lại năm học (YYYY-YYYY): ");
            parts = schoolYear.split("-");
            startYear = Integer.parseInt(parts[0]);
            endYear = Integer.parseInt(parts[1]);
        }
        
        // Nhập ngày nhập với validation
        LocalDate inputDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            String dateStr = InputUtil.getString("Ngày nhập (dd/MM/yyyy, Enter để dùng hôm nay): ");
            if (!dateStr.trim().isEmpty()) {
                inputDate = LocalDate.parse(dateStr.trim(), formatter);
            }
        } catch (Exception e) {
            System.out.println("Định dạng ngày không hợp lệ! Sử dụng ngày hôm nay.");
        }
        
        String note = InputUtil.getString("Ghi chú (nếu có): ");
        
        // Gọi service để thêm điểm
        boolean success = gradeService.addGrade(gradeID, studentID, subjectID, gradeType, score, semester, schoolYear, inputDate, note);
        
        if (success) {
            System.out.println("\nThêm điểm thành công!");
        } else {
            System.out.println("\nThêm điểm thất bại!");
        }
        
        pause();
    }
    
    private void displayStudentList() {
        System.out.println("\nDANH SÁCH HỌC SINH:");
        System.out.println("Mã HS    | Tên học sinh           | Lớp");
        System.out.println("---------|-------------------------|--------");
        
        StudentService.getInstance().getAllStudents().forEach(student -> {
            System.out.printf("%-8s | %-23s | %-8s%n", 
                student.getId(), student.getName(), student.getClassName());
        });
    }
    
    private void displaySubjectList() {
        System.out.println("\nDANH SÁCH MÔN HỌC:");
        System.out.println("Mã môn   | Tên môn học            | Hệ số");
        System.out.println("---------|-------------------------|--------");
        
        SubjectService.getInstance().getAllSubjects().forEach(subject -> {
            System.out.printf("%-8s | %-23s | %-8.1f%n", 
                subject.getSubjectID(), subject.getSubjectName(), subject.getConfficient());
        });
    }
}