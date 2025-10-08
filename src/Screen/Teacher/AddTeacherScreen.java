package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.TeacherService;
import Utils.InputUtil;

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
            id = InputUtil.getString("Mã giáo viên: ");
            if (teacherService.isTeacherIdExists(id)) {
                System.out.println("Mã giáo viên đã tồn tại! Vui lòng nhập lại.");
            } else break;
        }

        String name = InputUtil.getString("Họ và tên: ");
        String subject = InputUtil.getString("Môn giảng dạy (Enter nếu chưa có): ");

        //Nhập trình độ theo số
        int degreeCode = -1;
        String degree = "";
        while (degreeCode < 0 || degreeCode > 2) {
            System.out.println("\nChọn trình đọ cho giáo viên:");
            System.out.println("0 - Cử nhân");
            System.out.println("1 - Thạc sĩ");
            System.out.println("2 - Tiến sĩ");
            degreeCode = InputUtil.getInt("Nhập lựa chọn (0/1/2): ");

            switch (degreeCode) {
                case 0 -> degree = "Cử nhân";
                case 1 -> degree = "Thạc sĩ";
                case 2 -> degree = "Tiến sĩ";
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        int experience = InputUtil.getInt("Số năm kinh nghiệm: ");
        String email = InputUtil.getEmail("Email: ");
        String phone = InputUtil.getPhoneNumber("Số điện thoại: ");
        String homeroom = InputUtil.getString("Lớp chủ nhiệm (Enter nếu chưa có): ");

        //Nhập trạng thái theo số
        int statusCode = -1;
        String status = "";
        while (statusCode < 0 || statusCode > 2) {
            System.out.println("\nChọn trạng thái giáo viên:");
            System.out.println("0 - Đang dạy");
            System.out.println("1 - Nghỉ hưu");
            System.out.println("2 - Công tác");
            statusCode = InputUtil.getInt("Nhập lựa chọn (0/1/2): ");


            switch (statusCode) {
                case 0 -> status = "Đang dạy";
                case 1 -> status = "Nghỉ hưu";
                case 2 -> status = "Công tác";
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        boolean added = teacherService.addTeacher(
                id, name, status, subject, degree, experience, email, phone, homeroom
        );

        if (added) {
            System.out.println("\nĐã thêm giáo viên thành công!");
        } else {
            System.out.println("\nThêm giáo viên thất bại!");
        }

        InputUtil.pressEnterToContinue();
    }
}