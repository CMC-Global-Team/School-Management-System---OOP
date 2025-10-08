package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.TeacherService;
import Utils.InputUtil;
import java.util.*;
public class AddTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService = TeacherService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           THÊM GIÁO VIÊN MỚI             │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập thông tin giáo viên mới:");

        String id;
        while (true) {
            id = InputUtil.getNonEmptyString("Mã giáo viên: ");
            if (teacherService.isTeacherIdExists(id)) {
                System.out.println("Mã giáo viên đã tồn tại! Vui lòng nhập lại.");
            } else break;
        }

        String name = InputUtil.getNonEmptyString("Họ và tên: ");
        String subjectInput = InputUtil.getNonEmptyString("Môn giảng dạy (nếu nhiều, ngăn cách bằng dấu ','): ");
        List<String> subjects = Arrays.stream(subjectInput.split(","))
                .map(String::trim)
                .toList();
        String degree = InputUtil.getNonEmptyString("Trình độ (Cử nhân/Thạc sĩ/Tiến sĩ): ");
        int experience = InputUtil.getInt("Số năm kinh nghiệm: ");
        String email = InputUtil.getNonEmptyString("Email: ");
        String phone = InputUtil.getNonEmptyString("Số điện thoại: ");
        String homeroom = InputUtil.getString("Lớp chủ nhiệm (Enter nếu chưa có): ");
        String status = InputUtil.getNonEmptyString("Trạng thái (Đang dạy/Nghỉ hưu/Công tác): ");

        boolean added = teacherService.addTeacher(
                id, name, status, subjects, degree, experience, email, phone, homeroom
        );

        if (added) {
            System.out.println("\nĐã thêm giáo viên thành công!");
        } else {
            System.out.println("\nThêm giáo viên thất bại!");
        }

        InputUtil.pressEnterToContinue();
    }
}
