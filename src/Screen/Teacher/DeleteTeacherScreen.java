package Screen.Teacher;

import Screen.AbstractScreen;
import Services.TeacherService;
import Utils.InputUtil;

public class DeleteTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService = TeacherService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│                 XÓA GIÁO VIÊN                │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String id = InputUtil.getString("Nhập mã giáo viên cần xóa: ").trim();

        // Kiểm tra mã tồn tại
        if (!teacherService.isTeacherIdExists(id)) {
            System.out.println("Không tìm thấy giáo viên có mã '" + id + "'.");
            return;
        }

        // Xác nhận xóa
        System.out.print("Bạn có chắc chắn muốn xóa giáo viên này? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("y")) {
            System.out.println("⏳ Hủy thao tác xóa.");
            return;
        }

        // Gọi service để xóa
        boolean success = teacherService.deleteTeacher(id);

        if (success) {
            System.out.println("Xóa giáo viên thành công!");
        } else {
            System.out.println("Xóa giáo viên thất bại. Vui lòng thử lại!");
        }

        System.out.println("\nNhấn Enter để quay lại menu...");
        scanner.nextLine();
    }
}
