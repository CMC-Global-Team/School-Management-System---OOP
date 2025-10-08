package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Utils.FileUtil;
import Utils.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class AssignHomeroomScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│      GÁN (CẬP NHẬT) LỚP CHỦ NHIỆM         │");
        System.out.println("└────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<String> teacherLines = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        try {
            if (FileUtil.fileExists("src/Data/teachers.txt")) {
                teacherLines = FileUtil.readLines("src/Data/teachers.txt");
                for (String line : teacherLines) {
                    Teacher t = Teacher.fromString(line);
                    if (t != null) teachers.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file giáo viên: " + e.getMessage());
            return;
        }

        if (teachers.isEmpty()) {
            System.out.println("Chưa có giáo viên nào trong hệ thống!");
            return;
        }

        System.out.println("\nDanh sách giáo viên:");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            System.out.printf("%d. %-10s | %-25s | CN: %s\n",
                    i + 1,
                    t.getId(),
                    t.getName(),
                    (t.getTeacherHomeroom() == null || t.getTeacherHomeroom().isEmpty()) ? "Chưa có" : t.getTeacherHomeroom());
        }

        int choice = InputUtil.getInt("Chọn số thứ tự giáo viên để gán lớp: ");
        if (choice < 1 || choice > teachers.size()) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }

        Teacher selectedTeacher = teachers.get(choice - 1);
        System.out.println("\nGiáo viên được chọn: " + selectedTeacher.getName());
        System.out.println("Lớp hiện tại: " +
                (selectedTeacher.getTeacherHomeroom() == null || selectedTeacher.getTeacherHomeroom().isEmpty()
                        ? "Chưa có" : selectedTeacher.getTeacherHomeroom()));

        String newHomeroom = InputUtil.getNonEmptyString("Nhập lớp chủ nhiệm mới: ");

        // Ghi đè lớp chủ nhiệm
        selectedTeacher.setTeacherHomeroom(newHomeroom);

        // Ghi lại file
        List<String> updatedLines = new ArrayList<>();
        for (Teacher t : teachers) {
            updatedLines.add(t.toString());
        }

        try {
            FileUtil.writeLines("src/Data/teachers.txt", updatedLines);
            System.out.println("\nĐã cập nhật lớp chủ nhiệm cho giáo viên!");
        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }
}
