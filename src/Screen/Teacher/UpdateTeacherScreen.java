package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Utils.FileUtil;
import Utils.InputUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateTeacherScreen extends AbstractScreen {

    private static final String FILE_PATH = "src/Data/teachers.txt";

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       CẬP NHẬT THÔNG TIN GIÁO VIÊN      │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        try {
            List<String> lines = FileUtil.readLines(FILE_PATH);
            List<Teacher> teachers = lines.stream()
                    .map(Teacher::fromString)
                    .collect(Collectors.toList());

            if (teachers.isEmpty()) {
                System.out.println("Hiện chưa có giáo viên nào trong hệ thống!");
                pause();
                return;
            }

            String id = InputUtil.getNonEmptyString("Nhập mã giáo viên cần cập nhật: ");
            Teacher t = teachers.stream()
                    .filter(x -> x.getId().equalsIgnoreCase(id))
                    .findFirst()
                    .orElse(null);

            if (t == null) {
                System.out.println("Không tìm thấy giáo viên với mã này!");
                pause();
                return;
            }

            System.out.println("Thông tin hiện tại: " + t);

            String name = InputUtil.getString("Tên mới (" + t.getName() + "): ");
            if (!name.isEmpty()) t.setName(name);

            String subjectInput = InputUtil.getString("Môn dạy (" + String.join(", ", t.getTeacherSubjects()) + "): ");
            if (!subjectInput.isEmpty()) {
                List<String> subjects =
                        List.of(subjectInput.split(","))
                                .stream()
                                .map(String::trim)
                                .collect(Collectors.toList());
                t.setTeacherSubjects(subjects);
            }


            String degree = InputUtil.getString("Học vị (" + t.getTeacherDegree() + "): ");
            if (!degree.isEmpty()) t.setTeacherDegree(degree);

            String expStr = InputUtil.getString("Kinh nghiệm (" + t.getTeacherExperience() + "): ");
            if (!expStr.isEmpty()) {
                try {
                    t.setTeacherExperience(Integer.parseInt(expStr));
                } catch (NumberFormatException e) {
                    System.out.println("Kinh nghiệm không hợp lệ, giữ nguyên giá trị cũ!");
                }
            }

            String email = InputUtil.getString("Email (" + t.getTeacherEmail() + "): ");
            if (!email.isEmpty()) t.setTeacherEmail(email);

            String phone = InputUtil.getString("SĐT (" + t.getTeacherPhone() + "): ");
            if (!phone.isEmpty()) t.setTeacherPhone(phone);

            String homeroom = InputUtil.getString("Lớp CN (" + t.getTeacherHomeroom() + "): ");
            if (!homeroom.isEmpty()) t.setTeacherHomeroom(homeroom);

            // Lưu lại file
            List<String> newLines = teachers.stream()
                    .map(Teacher::toFileString)
                    .collect(Collectors.toList());
            FileUtil.writeLines(FILE_PATH, newLines);

            System.out.println("Cập nhật thông tin giáo viên thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc/ghi file: " + e.getMessage());
        }

        pause();
    }
}
