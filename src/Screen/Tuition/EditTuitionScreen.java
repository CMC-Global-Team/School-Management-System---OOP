package Screen.Tuition;
import Models.Tuition;
import Screen.AbstractScreen;
import Utils.InputUtil;

/**
 * EditTuitionScreen - Màn hình chỉnh sửa thông tin học phí
 *
 */
public class EditTuitionScreen extends AbstractScreen {


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         Sửa Thông Tin Học Phí            │");
        System.out.println("└──────────────────────────────────────────┘");
    }
    public void handleInput() {
        String tuitionId = InputUtil.getNonEmptyString("Nhập mã học phí cần chỉnh sửa (TFxx): ");
        System.out.println("\n--- Nhập thông tin mới (bỏ trống nếu không thay đổi) ---");
        String studentIdInput = InputUtil.getString("Mã sinh viên mới: ");
        String semesterInput = InputUtil.getString("Học kỳ mới: ");
        String schoolYearInput = InputUtil.getString("Năm học mới (VD: 2024-2028): ");
        String amountInput = InputUtil.getString("Số tiền thu mới: ");
        String paymentDateInput = InputUtil.getString("Ngày thu mới (dd/MM/yyyy, bỏ trống nếu không thay đổi): ");
        String statusInput = InputUtil.getString("Trạng thái mới (Đã thu/Chưa thu): ");
        String methodInput = InputUtil.getString("Phương thức mới (Tiền mặt/Chuyển khoản): ");
        String noteInput = InputUtil.getString("Ghi chú mới: ");

    }
}
