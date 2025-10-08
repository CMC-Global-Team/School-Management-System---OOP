package Screen.Teacher;

import Models.Teacher;
import Models.Subject;
import Screen.AbstractScreen;
import Services.TeacherService;
import Services.SubjectService;
import Utils.InputUtil;

import java.util.*;
import java.util.stream.Collectors;

public class AddTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService = TeacherService.getInstance();
    private final SubjectService subjectService = SubjectService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           THÊM GIÁO VIÊN MỚI             │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập thông tin giáo viên mới:");

        // --- Nhập mã giáo viên ---
        String id;
        while (true) {
            id = InputUtil.getString("Mã giáo viên: ");
            if (teacherService.isTeacherIdExists(id)) {
                System.out.println("Mã giáo viên đã tồn tại! Vui lòng nhập lại.");
            } else break;
        }

        String name = InputUtil.getString("Họ và tên: ");

        // --- Hiển thị danh sách môn học ---
        List<Subject> allSubjects = subjectService.getAllSubjects();
        if (allSubjects.isEmpty()) {
            System.out.println("\nKhông có môn học nào trong hệ thống. Vui lòng thêm môn trước!");
            InputUtil.pressEnterToContinue();
            return;
        }

        System.out.println("\nDanh sách môn học:");
        System.out.println("─────────────────────────────────────────────");
        System.out.printf("%-10s %-25s %-10s%n", "Mã môn", "Tên môn học", "Số tiết");
        System.out.println("─────────────────────────────────────────────");
        for (Subject s : allSubjects) {
            System.out.printf("%-10s %-25s %-10d%n",
                    s.getSubjectID(), s.getSubjectName(), s.getLessonCount());
        }

        // --- Nhập mã môn học ---
        List<String> teacherSubjects = new ArrayList<>();
        while (teacherSubjects.isEmpty()) {
            String subjectInput = InputUtil.getString("\nNhập mã môn (có thể nhập nhiều, cách nhau bằng dấu ','): ");
            if (subjectInput.trim().isEmpty()) {
                System.out.println("Không được để trống. Vui lòng nhập lại!");
                continue;
            }

            String[] ids = subjectInput.split(",");
            for (String sid : ids) {
                sid = sid.trim();
                try {
                    Optional<Subject> subjectOpt = subjectService.findById(sid);
                    if (subjectOpt.isPresent()) {
                        teacherSubjects.add(subjectOpt.get().getSubjectName());
                    } else {
                        System.out.println("Mã môn không tồn tại: " + sid);
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi khi kiểm tra mã môn '" + sid + "': " + e.getMessage());
                }
            }

            if (teacherSubjects.isEmpty()) {
                System.out.println("Không có mã môn hợp lệ. Vui lòng nhập lại!");
            }
        }

        // --- Trình độ ---
        int degreeCode = -1;
        String degree = "";
        while (degreeCode < 0 || degreeCode > 2) {
            System.out.println("\nChọn trình độ:");
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

        // --- Kinh nghiệm, liên hệ ---
        int experience = InputUtil.getInt("Số năm kinh nghiệm: ");
        String email = InputUtil.getEmail("Email: ");
        String phone = InputUtil.getPhoneNumber("Số điện thoại: ");
        String homeroom = InputUtil.getString("Lớp chủ nhiệm (Enter nếu chưa có): ");

        // --- Trạng thái ---
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

        // --- Thêm vào danh sách ---
        boolean added = teacherService.addTeacher(
                id, name, status, teacherSubjects, degree, experience, email, phone, homeroom
        );

        if (added) {
            System.out.println("\nĐã thêm giáo viên thành công!");
        } else {
            System.out.println("\nThêm giáo viên thất bại!");
        }

        InputUtil.pressEnterToContinue();
    }
}
