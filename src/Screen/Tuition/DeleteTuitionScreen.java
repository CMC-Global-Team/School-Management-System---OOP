package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;
import Services.TuitionService;
import Utils.InputUtil;
import java.util.Optional;

/**
 * DeleteTuitionScreen - Màn hình xóa thông tin học phí
 */
public class DeleteTuitionScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         XÓA THÔNG TIN HỌC PHÍ            │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã học phí: TFxxxx (VD: TF0001) - Phải tồn tại trong hệ thống          │");
        System.out.println("│                                                                         │");
        System.out.println("│ CẢNH BÁO QUAN TRỌNG:                                                │");
        System.out.println("│ - Hệ thống sẽ hiển thị thông tin học phí trước khi xóa                  │");
        System.out.println("│ - Bạn phải xác nhận trước khi thực hiện xóa                             │");
        System.out.println("│ - Thao tác này KHÔNG THỂ HOÀN TÁC                                       │");
        System.out.println("│ - Hãy kiểm tra kỹ thông tin trước khi xác nhận                          │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("XÓA THÔNG TIN HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        TuitionService service = TuitionService.getInstance();
        
        // Kiểm tra có dữ liệu không
        if (service.getTotalTuitions() == 0) {
            System.out.println("Không có dữ liệu học phí để xóa!");
            pause();
            return;
        }
        
        // Nhập mã học phí
        String tuitionId = null;
        Optional<Tuition> tuitionOpt = Optional.empty();
        
        while (tuitionOpt.isEmpty()) {
            if (tuitionId != null) {
                System.out.println("Không tìm thấy học phí với mã '" + tuitionId + "'!");
            }
            tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí cần xóa (VD: TF0001): ");
            tuitionOpt = service.findById(tuitionId);
        }
        
        Tuition tuition = tuitionOpt.get();
        
        // Hiển thị thông tin học phí sẽ bị xóa
        System.out.println("\nTHÔNG TIN HỌC PHÍ SẼ BỊ XÓA:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
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
        
        // Xác nhận xóa
        System.out.println("\nCẢNH BÁO: Thao tác này không thể hoàn tác!");
        System.out.println("Bạn có chắc chắn muốn xóa học phí này?");
        System.out.println("1 - Có, xóa học phí");
        System.out.println("0 - Không, hủy bỏ");
        
        int confirm = InputUtil.getInt("Lựa chọn (0/1): ");
        
        if (confirm == 1) {
            boolean success = service.deleteTuition(tuitionId);
            if (success) {
                System.out.println("\nXóa học phí thành công!");
            } else {
                System.out.println("\nXóa học phí thất bại!");
            }
        } else {
            System.out.println("\nĐã hủy bỏ việc xóa học phí.");
        }
        
        pause();
    }
}
