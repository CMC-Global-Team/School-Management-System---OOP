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