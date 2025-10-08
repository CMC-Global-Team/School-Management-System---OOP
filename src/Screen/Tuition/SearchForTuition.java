package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;
import Services.StudentService;
import Services.TuitionService;
import Utils.InputUtil;
import java.util.List;

/**
 * SearchForTuition - Màn hình tra cứu thông tin học phí
 */
public class SearchForTuition extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         TRA CỨU THÔNG TIN HỌC PHÍ        │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\n📋 HƯỚNG DẪN TÌM KIẾM:");
        System.out.println("• Tìm theo mã học phí: TF0001");
        System.out.println("• Tìm theo mã học sinh: BS2");
        System.out.println("• Tìm theo năm học: 2024-2025");
        System.out.println("• Tìm theo trạng thái: đã thu, chưa thu");
        System.out.println("• Tìm theo phương thức: tiền mặt, chuyển khoản");
        System.out.println("• Để trống để xem tất cả học phí");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TÌM KIẾM HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        System.out.println("\nChọn cách tìm kiếm:");
        System.out.println("1. Tìm theo mã học phí");
        System.out.println("2. Tìm theo mã học sinh");
        System.out.println("3. Tìm theo năm học");
        System.out.println("4. Tìm theo trạng thái");
        System.out.println("5. Tìm theo từ khóa chung");
        System.out.println("6. Xem tất cả học phí");
        
        int choice = InputUtil.getInt("Lựa chọn (1-6): ");
        
        TuitionService service = TuitionService.getInstance();
        
        switch (choice) {
            case 1:
                searchByTuitionId(service);
                break;
            case 2:
                searchByStudentId(service);
                break;
            case 3:
                searchBySchoolYear(service);
                break;
            case 4:
                searchByStatus(service);
                break;
            case 5:
                searchByKeyword(service);
                break;
            case 6:
                displayAllTuitions(service);
                break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
        
        pause();
    }
    
    private void searchByTuitionId(TuitionService service) {
        String tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí: ");
        
        var tuitionOpt = service.findById(tuitionId);
        if (tuitionOpt.isPresent()) {
            displayTuitionDetails(tuitionOpt.get());
        } else {
            System.out.println("❌ Không tìm thấy học phí với mã: " + tuitionId);
        }
    }
    
    private void searchByStudentId(TuitionService service) {
        String studentId = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
        
        // Kiểm tra học sinh có tồn tại không
        StudentService studentService = StudentService.getInstance();
        if (!studentService.isStudentIdExists(studentId)) {
            System.out.println("❌ Không tìm thấy học sinh với mã: " + studentId);
            return;
        }
        
        List<Tuition> tuitions = service.searchTuitions(studentId);
        displayTuitionList(tuitions, "Học phí của học sinh " + studentId);
    }
    
    private void searchBySchoolYear(TuitionService service) {
        String schoolYear = InputUtil.getNonEmptyString("Nhập năm học (YYYY-YYYY): ");
        
        List<Tuition> tuitions = service.searchTuitions(schoolYear);
        displayTuitionList(tuitions, "Học phí năm học " + schoolYear);
    }
    
    private void searchByStatus(TuitionService service) {
        System.out.println("\nChọn trạng thái:");
        System.out.println("1. Đã thu");
        System.out.println("2. Chưa thu");
        
        int statusChoice = InputUtil.getInt("Lựa chọn (1-2): ");
        String status = (statusChoice == 1) ? "đã thu" : "chưa thu";
        
        List<Tuition> tuitions = service.searchTuitions(status);
        displayTuitionList(tuitions, "Học phí " + status);
    }
    
    private void searchByKeyword(TuitionService service) {
        String keyword = InputUtil.getString("Nhập từ khóa tìm kiếm: ");
        
        if (keyword.trim().isEmpty()) {
            displayAllTuitions(service);
        } else {
            List<Tuition> tuitions = service.searchTuitions(keyword);
            displayTuitionList(tuitions, "Kết quả tìm kiếm: '" + keyword + "'");
        }
    }
    
    private void displayAllTuitions(TuitionService service) {
        List<Tuition> tuitions = service.getAllTuitions();
        displayTuitionList(tuitions, "Tất cả học phí");
    }
    
    private void displayTuitionDetails(Tuition tuition) {
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CHI TIẾT HỌC PHÍ                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã học phí: %-50s │%n", tuition.getTuitionId());
        System.out.printf("│ Mã học sinh: %-50s │%n", tuition.getStudentId());
        System.out.printf("│ Học kỳ: %-50d │%n", tuition.getSemester());
        System.out.printf("│ Năm học: %-50s │%n", tuition.getSchoolYear());
        System.out.printf("│ Số tiền: %-50.0f VND │%n", tuition.getAmount());
        System.out.printf("│ Ngày thu: %-50s │%n", tuition.getPaymentDate());
        System.out.printf("│ Phương thức: %-50s │%n", tuition.getMethod());
        System.out.printf("│ Trạng thái: %-50s │%n", tuition.getStatus());
        System.out.printf("│ Ghi chú: %-50s │%n", tuition.getNote());
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    private void displayTuitionList(List<Tuition> tuitions, String title) {
        if (tuitions.isEmpty()) {
            System.out.println("\n❌ Không tìm thấy học phí nào!");
            return;
        }
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                    " + title.toUpperCase() + "                                    │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-12s │ %-10s │ %-6s │ %-12s │ %-12s │ %-12s │ %-12s │ %-10s │%n", 
            "Mã HP", "Mã HS", "Kỳ", "Năm học", "Số tiền", "Ngày thu", "Phương thức", "Trạng thái");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        
        for (Tuition tuition : tuitions) {
            System.out.printf("│ %-12s │ %-10s │ %-6d │ %-12s │ %-12.0f │ %-12s │ %-12s │ %-10s │%n",
                tuition.getTuitionId(),
                tuition.getStudentId(),
                tuition.getSemester(),
                tuition.getSchoolYear(),
                tuition.getAmount(),
                tuition.getPaymentDate(),
                tuition.getMethod(),
                tuition.getStatus()
            );
        }
        
        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("📊 Tổng số: " + tuitions.size() + " học phí");
    }
}
