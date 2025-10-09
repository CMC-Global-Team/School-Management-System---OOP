package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.FileManager;
import Utils.FileUtil;
import Utils.InputUtil;
import java.util.ArrayList;
import java.util.List;

public class AssignTeacherScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│       PHÂN CÔNG GIẢNG DẠY CHO GIÁO VIÊN   │");
        System.out.println("└────────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Xem danh sách giáo viên và môn học hiện tại                         │");
        System.out.println("│ 2. Nhập mã giáo viên cần phân công môn học                             │");
        System.out.println("│ 3. Nhập các môn học mới (ngăn cách bằng dấu phẩy)                      │");
        System.out.println("│ 4. Hệ thống sẽ thêm môn học mới vào danh sách môn của giáo viên         │");
        System.out.println("│                                                                         │");
        System.out.println("│ VÍ DỤ: Toán, Lý, Hóa (các môn cách nhau bằng dấu phẩy)                │");
        System.out.println("│ LƯU Ý: Môn học trùng lặp sẽ được bỏ qua                                  │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {

        // Đọc danh sách giáo viên
        List<String> teacherLines;
        try {
            teacherLines = FileUtil.readLines(FileManager.TEACHER_FILE);
        } catch (Exception e) {
            System.out.println("Lỗi đọc file giáo viên: " + e.getMessage());
            return;
        }

        if (teacherLines.isEmpty()) {
            System.out.println("Chưa có giáo viên nào trong hệ thống!");
            return;
        }

        // Hiển thị danh sách
        System.out.println("\nDANH SÁCH GIÁO VIÊN:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                    DANH SÁCH GIÁO VIÊN                                                │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-8s %-25s %-40s %-15s │%n", "Mã GV", "Tên giáo viên", "Môn giảng dạy", "Lớp CN");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        
        for (String line : teacherLines) {
            Teacher t = Teacher.fromString(line);
            if (t != null) {
                String subjects = t.getTeacherSubjects().isEmpty() ? "Chưa có" : String.join(", ", t.getTeacherSubjects());
                String homeroom = (t.getTeacherHomeroom() == null || t.getTeacherHomeroom().isEmpty()) ? "Chưa có" : t.getTeacherHomeroom();
                System.out.printf("│ %-8s %-25s %-40s %-15s │%n",
                        t.getId(), t.getName(), subjects, homeroom);
            }
        }
        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────────────────────────┘");

        // Chọn giáo viên
        String teacherId = InputUtil.getNonEmptyString("\nNhập mã giáo viên cần phân công: ");
        Teacher selectedTeacher = null;
        int selectedIndex = -1;
        for (int i = 0; i < teacherLines.size(); i++) {
            Teacher t = Teacher.fromString(teacherLines.get(i));
            if (t != null && t.getId().equalsIgnoreCase(teacherId)) {
                selectedTeacher = t;
                selectedIndex = i;
                break;
            }
        }

        if (selectedTeacher == null) {
            System.out.println("Không tìm thấy giáo viên với mã: " + teacherId);
            return;
        }

        // Nhập nhiều môn học (cách nhau bằng dấu ,)
        System.out.println("\nTHÔNG TIN PHÂN CÔNG:");
        System.out.println("Giáo viên: " + selectedTeacher.getName());
        System.out.println("Môn hiện tại: " + (selectedTeacher.getTeacherSubjects().isEmpty() ? "Chưa có" : String.join(", ", selectedTeacher.getTeacherSubjects())));
        
        String subjectsInput = InputUtil.getNonEmptyString("\nNhập các môn giảng dạy mới (ngăn cách bằng dấu phẩy): ");
        List<String> newSubjects = new ArrayList<>();
        for (String s : subjectsInput.split(",")) {
            String sub = s.trim();
            if (!sub.isEmpty() && !selectedTeacher.getTeacherSubjects().contains(sub)) {
                newSubjects.add(sub);
            }
        }

        if (newSubjects.isEmpty()) {
            System.out.println("Không có môn học mới nào được thêm!");
            return;
        }

        System.out.println("\nCác môn học sẽ được thêm:");
        for (int i = 0; i < newSubjects.size(); i++) {
            System.out.println((i + 1) + ". " + newSubjects.get(i));
        }

        // Thêm vào danh sách hiện tại
        selectedTeacher.getTeacherSubjects().addAll(newSubjects);

        // Lưu lại vào file
        teacherLines.set(selectedIndex, selectedTeacher.toFileString());
        try {
            FileUtil.writeLines(FileManager.TEACHER_FILE, teacherLines);
            System.out.println("\nĐã phân công giảng dạy thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }
}
