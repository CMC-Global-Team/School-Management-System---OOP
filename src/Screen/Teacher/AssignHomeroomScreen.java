package Screen.Teacher;

import Models.TeachingAssignment;
import Screen.AbstractScreen;
import Utils.FileUtil;
import Utils.InputUtil;

import java.util.List;

public class AssignHomeroomScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│                DANH SÁCH PHÂN CÔNG GIẢNG DẠY                 │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
    }
    public void handleInput(){
        try {
            // Đọc file phân công
            List<String> lines = FileUtil.readLines("src/Data/teacher_assignments.txt");

            if (lines.isEmpty()) {
                System.out.println("Hiện chưa có dữ liệu phân công giảng dạy nào!");
                InputUtil.pressEnterToContinue();
                return;
            }

            // In tiêu đề bảng
            System.out.printf("%-10s %-20s %-20s %-10s%n",
                    "Mã GV", "Tên Giáo Viên", "Môn Giảng Dạy", "Lớp");
            System.out.println("----------------------------------------------------------------");

            // Duyệt từng dòng và hiển thị
            for (String line : lines) {
                TeachingAssignment ta = TeachingAssignment.fromString(line);
                if (ta != null) {
                    System.out.printf("%-10s %-20s %-20s %-10s%n",
                            ta.getTeacherId(),
                            ta.getTeacherName(),
                            ta.getSubject(),
                            ta.getClassName());
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file phân công: " + e.getMessage());
        }

        System.out.println("----------------------------------------------------------------");
        InputUtil.pressEnterToContinue();
    }
}
