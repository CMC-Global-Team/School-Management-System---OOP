package Screen.Tuition;

import Models.Tuition;
import Screen.AbstractScreen;
import Services.TuitionService;
import Utils.InputUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * EditTuitionScreen - Màn hình chỉnh sửa thông tin học phí
 */
public class EditTuitionScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Sửa Thông Tin Học Phí             │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã học phí: TFxxxx (VD: TF0001) - Phải tồn tại trong hệ thống          │");
        System.out.println("│ Mã học sinh: BSx (VD: BS2) - Để trống nếu không thay đổi               │");
        System.out.println("│ Học kỳ: 1 hoặc 2 - Để trống nếu không thay đổi                         │");
        System.out.println("│ Năm học: YYYY-YYYY (VD: 2024-2025) - Để trống nếu không thay đổi      │");
        System.out.println("│ Số tiền: Nhập số nguyên (VD: 5000000) - Để trống nếu không thay đổi    │");
        System.out.println("│ Ngày thu: dd/MM/yyyy (VD: 15/10/2024) - Để trống nếu không thay đổi    │");
        System.out.println("│ Trạng thái: Đã thu/Chưa thu - Để trống nếu không thay đổi             │");
        System.out.println("│ Phương thức: Tiền mặt/Chuyển khoản - Để trống nếu không thay đổi       │");
        System.out.println("│ Ghi chú: Tùy chọn - Để trống nếu không thay đổi                        │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    SỬA THÔNG TIN HỌC PHÍ");
        System.out.println("=".repeat(50));
        
        TuitionService service = TuitionService.getInstance();
        
        // Kiểm tra có dữ liệu không
        if (service.getTotalTuitions() == 0) {
            System.out.println("Không có dữ liệu học phí để chỉnh sửa!");
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
            tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí cần chỉnh sửa (VD: TF0001): ");
            tuitionOpt = service.findById(tuitionId);
        }
        
        Tuition tuition = tuitionOpt.get();
        
        // Hiển thị thông tin hiện tại
        System.out.println("\nTHÔNG TIN HỌC PHÍ HIỆN TẠI:");
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
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("NHẬP THÔNG TIN MỚI (Để trống nếu không thay đổi)");
        System.out.println("-".repeat(50));
        
        // Nhập thông tin mới
        String studentIdInput = InputUtil.getString("Mã học sinh mới: ");
        String semesterInput = InputUtil.getString("Học kỳ mới (1 hoặc 2): ");
        String schoolYearInput = InputUtil.getString("Năm học mới (YYYY-YYYY): ");
        String amountInput = InputUtil.getString("Số tiền mới (VND): ");
        String paymentDateInput = InputUtil.getString("Ngày thu mới (dd/MM/yyyy): ");
        String statusInput = InputUtil.getString("Trạng thái mới (Đã thu/Chưa thu): ");
        String methodInput = InputUtil.getString("Phương thức mới (Tiền mặt/Chuyển khoản): ");
        String noteInput = InputUtil.getString("Ghi chú mới: ");
        
        // Cập nhật thông tin
        boolean hasChanges = false;
        
        if (!studentIdInput.trim().isEmpty()) {
            tuition.setStudentId(studentIdInput.trim());
            hasChanges = true;
        }
        
        if (!semesterInput.trim().isEmpty()) {
            try {
                int semester = Integer.parseInt(semesterInput.trim());
                if (semester == 1 || semester == 2) {
                    tuition.setSemester(semester);
                    hasChanges = true;
                } else {
                    System.out.println("Học kỳ phải là 1 hoặc 2!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Học kỳ phải là số nguyên!");
            }
        }
        
        if (!schoolYearInput.trim().isEmpty()) {
            tuition.setSchoolYear(schoolYearInput.trim());
            hasChanges = true;
        }
        
        if (!amountInput.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountInput.trim());
                if (amount > 0) {
                    tuition.setAmount(amount);
                    hasChanges = true;
                } else {
                    System.out.println("Số tiền phải lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Số tiền phải là số hợp lệ!");
            }
        }
        
        if (!paymentDateInput.trim().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate paymentDate = LocalDate.parse(paymentDateInput.trim(), formatter);
                tuition.setPaymentDate(paymentDate);
                hasChanges = true;
            } catch (Exception e) {
                System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy.");
            }
        }
        
        if (!statusInput.trim().isEmpty()) {
            tuition.setStatus(statusInput.trim());
            hasChanges = true;
        }
        
        if (!methodInput.trim().isEmpty()) {
            tuition.setMethod(methodInput.trim());
            hasChanges = true;
        }
        
        if (!noteInput.trim().isEmpty()) {
            tuition.setNote(noteInput.trim());
            hasChanges = true;
        }
        
        // Lưu thay đổi
        if (hasChanges) {
            boolean success = service.updateTuition(tuition);
            if (success) {
                System.out.println("\nCập nhật học phí thành công!");
            } else {
                System.out.println("\nCập nhật học phí thất bại!");
            }
        } else {
            System.out.println("\nKhông có thay đổi nào được thực hiện.");
        }
        
        pause();
    }
}
