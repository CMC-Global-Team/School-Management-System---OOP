package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.FileManager;
import Utils.FileUtil;
import Utils.InputUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignTeacherScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│       PHÂN CÔNG GIẢNG DẠY CHO GIÁO VIÊN   │");
        System.out.println("└────────────────────────────────────────────┘");
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
        System.out.println("\nDanh sách giáo viên:");
        for (String line : teacherLines) {
            Teacher t = Teacher.fromString(line);
            if (t != null)
                System.out.printf("ID: %-8s | Tên: %-20s | Môn: %-30s | Lớp CN: %s%n",
                        t.getId(), t.getName(), String.join(", ", t.getTeacherSubjects()), t.getTeacherHomeroom());
        }

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
        String subjectsInput = InputUtil.getNonEmptyString("Nhập các môn giảng dạy (ngăn cách bằng dấu ,): ");
        List<String> newSubjects = new ArrayList<>();
        for (String s : subjectsInput.split(",")) {
            String sub = s.trim();
            if (!sub.isEmpty() && !selectedTeacher.getTeacherSubjects().contains(sub)) {
                newSubjects.add(sub);
            }
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
