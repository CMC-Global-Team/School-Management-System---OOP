package Screen.Tuition;

import Models.Student;
import Screen.AbstractScreen;
import Services.StudentService;
import Services.TuitionService;
import Utils.InputUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordTuitionScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Ghi Nhận Thanh Toán Học Phí       │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã học phí: TFxxxx (VD: TF0001, TF0002...)                              │");
        System.out.println("│ Mã học sinh: BSx (VD: BS2, BS3...) - Phải tồn tại trong hệ thống        │");
        System.out.println("│ Học kỳ: 1 hoặc 2                                                        │");
        System.out.println("│ Năm học: YYYY-YYYY (VD: 2024-2025)                                      │");
        System.out.println("│ Số tiền: Nhập số nguyên (VD: 5000000 = 5,000,000 VND)                  │");
        System.out.println("│ Ngày thu: dd/MM/yyyy (VD: 15/10/2024) - Không được vượt quá hôm nay     │");
        System.out.println("│ Trạng thái: 0-Chưa thu, 1-Đã thu                                        │");
        System.out.println("│ Phương thức: 0-Tiền mặt, 1-Chuyển khoản (chỉ khi đã thu)                │");
        System.out.println("│ Ghi chú: Tùy chọn, có thể để trống                                      │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    NHẬP THÔNG TIN HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        // Hiển thị danh sách học sinh để tham khảo
        displayStudentList();
        
        // Nhập mã học phí với validation
        String tuitionId = null;
        while (tuitionId == null || tuitionId.trim().isEmpty() || !tuitionId.matches("^TF\\d{4}$")) {
            if (tuitionId != null && !tuitionId.trim().isEmpty() && !tuitionId.matches("^TF\\d{4}$")) {
                System.out.println("Mã học phí sai định dạng! (VD: TF0001)");
            }
            tuitionId = InputUtil.getNonEmptyString("Mã học phí (TFxxxx): ");
        }
        
        // Kiểm tra mã học phí đã tồn tại chưa
        TuitionService service = TuitionService.getInstance();
        while (service.isTuitionIdExists(tuitionId)) {
            System.out.println("Mã học phí '" + tuitionId + "' đã tồn tại trong hệ thống!");
            tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí khác (TFxxxx): ");
        }
        
        // Nhập mã học sinh với validation
        String studentId = null;
        StudentService studentService = StudentService.getInstance();
        while (studentId == null || studentId.trim().isEmpty() || !studentService.isStudentIdExists(studentId)) {
            if (studentId != null && !studentId.trim().isEmpty() && !studentService.isStudentIdExists(studentId)) {
                System.out.println("Không tìm thấy học sinh có mã '" + studentId + "' trong hệ thống!");
            }
            studentId = InputUtil.getNonEmptyString("Mã học sinh: ");
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
        
        // Nhập số tiền với validation
        double amount = 0;
        while (amount <= 0) {
            if (amount != 0) {
                System.out.println("Số tiền phải lớn hơn 0!");
            }
            amount = InputUtil.getDouble("Số tiền (VND, VD: 5000000): ");
        }
        
        // Nhập ngày thu với validation
        LocalDate paymentDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (paymentDate == null) {
            try {
                String dateStr = InputUtil.getNonEmptyString("Ngày thu (dd/MM/yyyy): ");
                paymentDate = LocalDate.parse(dateStr, formatter);
                
                if (paymentDate.isAfter(LocalDate.now())) {
                    System.out.println("Ngày thu không được vượt quá hôm nay!");
                    paymentDate = null;
                }
            } catch (Exception e) {
                   System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập lại theo định dạng dd/MM/yyyy.");
            }
        }
        
        // Nhập trạng thái
        String status = "";
        int statusChoice = -1;
        while (statusChoice < 0 || statusChoice > 1) {
            if (statusChoice != -1) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn 0 hoặc 1.");
            }
            System.out.println("\nChọn trạng thái học phí:");
            System.out.println("0 - Chưa thu");
            System.out.println("1 - Đã thu");
            statusChoice = InputUtil.getInt("Nhập lựa chọn (0/1): ");
            
            switch (statusChoice) {
                case 0 -> status = "CHƯA THU";
                case 1 -> status = "ĐÃ THU";
            }
        }
        
        // Nhập phương thức thanh toán (chỉ khi đã thu)
        String method = "";
        if (status.equals("ĐÃ THU")) {
            int methodChoice = -1;
            while (methodChoice < 0 || methodChoice > 1) {
                if (methodChoice != -1) {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn 0 hoặc 1.");
                }
                System.out.println("\nChọn phương thức thanh toán:");
                System.out.println("0 - Tiền mặt");
                System.out.println("1 - Chuyển khoản");
                methodChoice = InputUtil.getInt("Nhập lựa chọn (0/1): ");
                
                switch (methodChoice) {
                    case 0 -> method = "TIỀN MẶT";
                    case 1 -> method = "CHUYỂN KHOẢN";
                }
            }
        }
        
        String note = InputUtil.getString("Ghi chú (nếu có): ");
        
        // Gọi service để thêm học phí
        boolean success = service.addTuition(tuitionId, studentId, semester, schoolYear, 
                                           amount, paymentDate, method, status, note);
        
        if (success) {
           System.out.println("\nThêm học phí thành công!");
           } else {
               System.out.println("\nThêm học phí thất bại!");
           }
        
        pause();
    }
    
    private void displayStudentList() {
        System.out.println("\nDANH SÁCH HỌC SINH HIỆN CÓ:");
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-10s │ %-25s │ %-15s │%n", "Mã HS", "Tên học sinh", "Lớp");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        
        StudentService studentService = StudentService.getInstance();
        var students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("│                    Chưa có học sinh nào                │");
        } else {
            for (Student student : students) {
                System.out.printf("│ %-10s │ %-25s │ %-15s │%n", 
                    student.getId(), 
                    student.getName(), 
                    student.getClassName());
            }
        }
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }
}

