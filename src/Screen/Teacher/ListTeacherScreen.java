package Screen.Teacher;

import Screen.AbstractScreen;
import Models.Teacher;
import Utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTeacherScreen extends AbstractScreen {

    private static final String FILE_PATH = "data/teachers.txt";
    private List<Teacher> teacherList;

    public ListTeacherScreen() {
        loadTeachers();
    }

    public void loadTeachers() {
        teacherList = new ArrayList<>();
        if (FileUtil.fileExists(FILE_PATH)) {
            try {
                List<String> lines = FileUtil.readLines(FILE_PATH);
                for (String line : lines) {
                    Teacher t = Teacher.fromString(line);
                    if (t != null) teacherList.add(t);
                }
            } catch (IOException e) {
                System.err.println("Lỗi khi đọc file giáo viên: " + e.getMessage());
            }
        }
    }

    @Override
    public void display() {
        loadTeachers();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│          DANH SÁCH GIÁO VIÊN             │");
        System.out.println("└──────────────────────────────────────────┘");

        if (teacherList.isEmpty()) {
            System.out.println("Chưa có giáo viên nào trong hệ thống.");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-10s %-5s %-20s %-15s %-10s %-10s%n",
                "Mã GV", "Họ Tên", "Môn", "Học Vị", "KN", "Email", "SĐT", "Lớp CN", "Trạng thái");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (Teacher t : teacherList) {
            String subjects = String.join(", ", t.getTeacherSubjects());
            System.out.printf("%-10s %-20s %-15s %-10s %-5d %-20s %-15s %-10s %-10s%n",
                    t.getId(), t.getName(), subjects, t.getTeacherDegree(),
                    t.getTeacherExperience(), t.getTeacherEmail(), t.getTeacherPhone(),
                    t.getTeacherHomeroom(), t.getStatus());
        }
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhấn Enter để quay lại...");
        scanner.nextLine();
    }
}
