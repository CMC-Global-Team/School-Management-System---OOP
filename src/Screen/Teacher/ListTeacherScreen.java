package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.TeacherService;
import java.util.List;

/**
 * Màn hình hiển thị danh sách giáo viên
 * - Chỉ hiển thị giao diện và dữ liệu
 * - Toàn bộ logic lấy dữ liệu nằm ở TeacherService
 */
public class ListTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService;

    public ListTeacherScreen() {
        this.teacherService = TeacherService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│          DANH SÁCH GIÁO VIÊN             │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN HIỂN THỊ                           │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Màn hình này hiển thị danh sách tất cả giáo viên trong hệ thống        │");
        System.out.println("│ Bao gồm: Mã GV, Tên, Giới tính, Ngày sinh, Địa chỉ, SĐT, Email        │");
        System.out.println("│          Môn giảng dạy, Lớp chủ nhiệm                                   │");
        System.out.println("│                                                                         │");
        System.out.println("│ Nhấn Enter để tiếp tục sau khi xem xong danh sách                       │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");

        List<Teacher> teachers = teacherService.getAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("Hiện chưa có giáo viên nào trong hệ thống.");
        } else {
            teacherService.displayAllTeachers();
        }

        pause();
    }
    @Override
    public void handleInput() {
        System.out.println("\nNhấn Enter để quay lại...");
        scanner.nextLine(); // đợi người dùng nhấn Enter
    }
}