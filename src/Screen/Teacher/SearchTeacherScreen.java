package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.TeacherService;
import Utils.InputUtil;
import java.util.List;
import java.util.stream.Collectors;

public class SearchTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService = TeacherService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│           TÌM KIẾM THÔNG TIN GIÁO VIÊN       │");
        System.out.println("└──────────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CÁC TIÊU CHÍ TÌM KIẾM                         │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Theo mã giáo viên: Nhập mã GV (VD: GV0001)                          │");
        System.out.println("│ 2. Theo họ tên: Nhập tên hoặc một phần tên giáo viên                   │");
        System.out.println("│ 3. Theo môn dạy: Nhập tên môn học mà giáo viên đang dạy                │");
        System.out.println("│ 4. Theo lớp chủ nhiệm: Nhập tên lớp mà giáo viên làm chủ nhiệm         │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý: Tìm kiếm không phân biệt hoa thường                             │");
        System.out.println("│         Có thể tìm kiếm một phần từ khóa                               │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("Chọn tiêu chí tìm kiếm:");
        System.out.println("1. Theo mã giáo viên");
        System.out.println("2. Theo họ tên");
        System.out.println("3. Theo môn dạy");
        System.out.println("4. Theo lớp chủ nhiệm");
        System.out.println("0. Quay lại");
        System.out.println("--------------------------------------------");

        int choice = InputUtil.getInt("Lựa chọn của bạn: ");
        List<Teacher> results = null;

        List<Teacher> teachers = teacherService.getAllTeachers();

        switch (choice) {
            case 1 -> {
                String id = InputUtil.getString("Nhập mã giáo viên: ").trim().toLowerCase();
                results = teachers.stream()
                        .filter(t -> t.getId().toLowerCase().contains(id))
                        .collect(Collectors.toList());
            }
            case 2 -> {
                String name = InputUtil.getString("Nhập họ tên giáo viên: ").trim().toLowerCase();
                results = teachers.stream()
                        .filter(t -> t.getName().toLowerCase().contains(name))
                        .collect(Collectors.toList());
            }
            case 3 -> {
                String subject = InputUtil.getString("Nhập môn dạy: ").trim().toLowerCase();
                results = teachers.stream()
                        .filter(t -> t.getTeacherSubjects().stream() // lặp qua từng môn
                                .anyMatch(sub -> sub.toLowerCase().contains(subject))) // kiểm tra có match không
                        .collect(Collectors.toList());
            }
            case 4 -> {
                String homeroom = InputUtil.getString("Nhập lớp chủ nhiệm: ").trim().toLowerCase();
                results = teachers.stream()
                        .filter(t -> t.getTeacherHomeroom().toLowerCase().contains(homeroom))
                        .collect(Collectors.toList());
            }
            case 0 -> {
                System.out.println("Quay lại menu...");
                return;
            }
            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
                return;
            }
        }

        if (results == null || results.isEmpty()) {
            System.out.println("\nKhông tìm thấy giáo viên nào phù hợp!");
        } else {
            System.out.println("\nKết quả tìm kiếm (" + results.size() + " giáo viên):");
            System.out.printf("%-8s %-20s %-15s %-12s %-10s %-25s %-15s %-15s %-15s%n",
                    "Mã GV", "Họ tên", "Trạng thái", "Môn dạy", "Trình độ", "Email", "SĐT", "Lớp CN", "Kinh nghiệm");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");

            for (Teacher t : results) {
                String subject = String.join(", ", t.getTeacherSubjects());
                System.out.printf("%-8s %-20s %-15s %-12s %-10s %-25s %-15s %-15s %-15d%n",
                        t.getId(),
                        t.getName(),
                        t.getStatus(),
                        subject,
                        t.getTeacherDegree(),
                        t.getTeacherEmail(),
                        t.getTeacherPhone(),
                        t.getTeacherHomeroom(),
                        t.getTeacherExperience());
            }
        }

        System.out.println("\nNhấn Enter để quay lại menu...");
        scanner.nextLine();
    }
}
