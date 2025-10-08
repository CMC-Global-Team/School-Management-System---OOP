package Screen.Student;

import Screen.AbstractScreen;
import Services.StudentService;
import Utils.InputUtil;

public class DeleteStudentScreen extends AbstractScreen {

    private final StudentService studentService = StudentService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│               XÓA HỌC SINH                   │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String id = InputUtil.getString("Nhập mã học sinh cần xóa: ").trim();

        // Kiểm tra mã tồn tại
        if (!studentService.isStudentIdExists(id)) {
            System.out.println("Không tìm thấy học sinh có mã '" + id + "'.");
            return;
        }

        // Xác nhận xóa (bạn có thể bỏ qua bước này nếu muốn xóa ngay)
        System.out.print("Bạn có chắc chắn muốn xóa học sinh này? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("y")) {
            System.out.println("Hủy thao tác xóa.");
            return;
        }

        // Gọi service để xóa
        boolean success = studentService.deleteStudent(id);

        if (success) {
            System.out.println("Xóa học sinh thành công!");
        } else {
            System.out.println("Xóa học sinh thất bại. Vui lòng thử lại!");
        }

        System.out.println("\nNhấn Enter để quay lại menu...");
        scanner.nextLine();
    }
}