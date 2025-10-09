package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;
import Services.TuitionService;
import Utils.InputUtil;
import java.util.Optional;

/**
 * TuitionDiscountScreen - Màn hình miễn giảm học phí
 */
public class TuitionDiscountScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         MIỄN GIẢM HỌC PHÍ                  │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã học phí: TFxxxx (VD: TF0001) - Phải tồn tại trong hệ thống          │");
        System.out.println("│ Phần trăm miễn giảm: 0-100 (VD: 10 = 10%, 50 = 50%)                   │");
        System.out.println("│                                                                         │");
        System.out.println("│ QUY TRÌNH MIỄN GIẢM:                                                   │");
        System.out.println("│ 1. Nhập mã học phí cần miễn giảm                                       │");
        System.out.println("│ 2. Hệ thống hiển thị thông tin học phí hiện tại                         │");
        System.out.println("│ 3. Nhập phần trăm miễn giảm (0-100%)                                   │");
        System.out.println("│ 4. Xác nhận thông tin miễn giảm                                        │");
        System.out.println("│ 5. Hệ thống cập nhật số tiền và ghi chú                                │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MIỄN GIẢM HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        TuitionService service = TuitionService.getInstance();
        
        // Nhập mã học phí
        String tuitionId = null;
        Optional<Tuition> tuitionOpt = Optional.empty();
        
        while (tuitionOpt.isEmpty()) {
            if (tuitionId != null) {
                System.out.println("Không tìm thấy học phí với mã '" + tuitionId + "'!");
            }
            tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí cần miễn giảm: ");
            tuitionOpt = service.findById(tuitionId);
        }
        
        Tuition tuition = tuitionOpt.get();
        
        // Hiển thị thông tin học phí hiện tại
        System.out.println("\nTHÔNG TIN HỌC PHÍ HIỆN TẠI:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CHI TIẾT HỌC PHÍ                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã học phí: %-50s │%n", tuition.getTuitionId());
        System.out.printf("│ Mã học sinh: %-50s │%n", tuition.getStudentId());
        System.out.printf("│ Học kỳ: %-50d │%n", tuition.getSemester());
        System.out.printf("│ Năm học: %-50s │%n", tuition.getSchoolYear());
        System.out.printf("│ Số tiền hiện tại: %-50.0f VND │%n", tuition.getAmount());
        System.out.printf("│ Ngày thu: %-50s │%n", tuition.getPaymentDate());
        System.out.printf("│ Phương thức: %-50s │%n", tuition.getMethod());
        System.out.printf("│ Trạng thái: %-50s │%n", tuition.getStatus());
        System.out.printf("│ Ghi chú: %-50s │%n", tuition.getNote());
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        
        // Nhập phần trăm miễn giảm
        double discountPercent = -1;
        while (discountPercent < 0 || discountPercent > 100) {
            if (discountPercent != -1) {
                System.out.println("Phần trăm miễn giảm phải trong khoảng 0 - 100!");
            }
            discountPercent = InputUtil.getDouble("Nhập phần trăm miễn giảm (0-100%): ");
        }
        
        // Xác nhận miễn giảm
        System.out.println("\nXÁC NHẬN MIỄN GIẢM:");
        double discountAmount = tuition.getAmount() * (discountPercent / 100);
        double newAmount = tuition.getAmount() - discountAmount;
        
        System.out.printf("Số tiền gốc: %,.0f VND%n", tuition.getAmount());
        System.out.printf("Phần trăm giảm: %.1f%%%n", discountPercent);
        System.out.printf("Số tiền được giảm: %,.0f VND%n", discountAmount);
        System.out.printf("Số tiền sau giảm: %,.0f VND%n", newAmount);
        
        System.out.println("\nBạn có chắc chắn muốn áp dụng miễn giảm này?");
        System.out.println("1 - Có, áp dụng miễn giảm");
        System.out.println("0 - Không, hủy bỏ");
        
        int confirm = InputUtil.getInt("Lựa chọn (0/1): ");
        
        if (confirm == 1) {
            boolean success = service.applyDiscount(tuitionId, discountPercent);
            if (success) {
                System.out.println("\nMiễn giảm học phí thành công!");
            } else {
                System.out.println("\nMiễn giảm học phí thất bại!");
            }
        } else {
            System.out.println("\nĐã hủy bỏ miễn giảm học phí.");
        }
        
        pause();
    }
}
