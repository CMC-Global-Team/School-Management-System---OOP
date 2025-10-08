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
        System.out.println("│       GHI NHẬN THANH TOÁN HỌC PHÍ        │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN SỬ DỤNG:");
        System.out.println("- Mã học phí: Định dạng TFxxxx (VD: TF0001)");
        System.out.println("- Mã học sinh: Phải tồn tại trong hệ thống");
        System.out.println("- Năm học: Định dạng YYYY-YYYY (VD: 2024-2025)");
        System.out.println("- Số tiền: Nhập số nguyên (VD: 5000000 = 5,000,000 VND)");
        System.out.println("- Ngày thu: Định dạng dd/MM/yyyy (VD: 09/10/2024)");
        System.out.println("- Trạng thái: 0=Chưa thu, 1=Đã thu");
        System.out.println("- Phương thức: 0=Tiền mặt, 1=Chuyển khoản");
    }
    
    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("NHẬP THÔNG TIN HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        // Hiển thị danh sách học sinh để tham khảo
        displayStudentList();
        
        String tuitionId = InputUtil.getNonEmptyString("Mã học phí (TFxxxx): ");
        String studentId = InputUtil.getNonEmptyString("Mã học sinh: ");
        int semester = InputUtil.getInt("Học kỳ (1 hoặc 2): ");
        String schoolYear = InputUtil.getNonEmptyString("Năm học (YYYY-YYYY): ");
        double amount = InputUtil.getDouble("Số tiền (VND, VD: 5000000): ");
        
        // Nhập ngày thu
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
                System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập lại.");
            }
        }
        
        String method = "";
        String status = "";
        String note = InputUtil.getString("Ghi chú (nếu có): ");
        
        // Gọi service để thêm học phí
        TuitionService service = TuitionService.getInstance();
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

