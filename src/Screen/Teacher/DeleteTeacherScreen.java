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
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CẢNH BÁO QUAN TRỌNG                           │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Nhập mã giáo viên cần xóa (VD: GV0001)                              │");
        System.out.println("│ 2. Hệ thống sẽ hiển thị thông tin giáo viên                             │");
        System.out.println("│ 3. Xác nhận việc xóa bằng cách nhập 'y' hoặc 'yes'                      │");
        System.out.println("│                                                                         │");
        System.out.println("│ ⚠️  CẢNH BÁO: Việc xóa giáo viên sẽ không thể hoàn tác!                 │");
        System.out.println("│    Tất cả thông tin liên quan đến giáo viên sẽ bị xóa vĩnh viễn        │");
        System.out.println("│    Bao gồm: Thông tin cá nhân, môn dạy, lớp chủ nhiệm                   │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
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
