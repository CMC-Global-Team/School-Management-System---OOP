package Screen.Student;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.StudentService;
import Utils.FileUtil;
import Utils.InputUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AddStudentScreen extends AbstractScreen {

    private final StudentService studentService = StudentService.getInstance();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│             THÊM HỌC SINH MỚI               │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        // === Nhập mã học sinh ===
        String id = InputUtil.getString("Nhập mã học sinh: ").trim();

        // Kiểm tra ID trùng
        if (studentService.isStudentIdExists(id)) {
            System.out.println("Mã học sinh đã tồn tại! Vui lòng nhập mã khác.");
            return;
        }

        String name = InputUtil.getString("Nhập họ và tên: ");
        LocalDate dateOfBirth = null;

        // === Nhập và kiểm tra ngày sinh ===
        while (dateOfBirth == null) {
            String dateStr = InputUtil.getString("Nhập ngày sinh (dd/MM/yyyy): ");
            try {
                dateOfBirth = LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập lại (dd/MM/yyyy).");
            }
        }

        String gender = InputUtil.getString("Nhập giới tính (Nam/Nữ): ");

        // === Đọc danh sách lớp học từ file classrooms.txt ===
        List<Classroom> classrooms = new ArrayList<>();
        try {
            if (FileUtil.fileExists("data/classrooms.txt")) {
                List<String> lines = FileUtil.readLines("data/classrooms.txt");
                for (String line : lines) {
                    Classroom c = Classroom.fromString(line);
                    if (c != null) classrooms.add(c);
                }
            } else {
                System.out.println("Không tìm thấy file classrooms.txt trong thư mục data!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file lớp học: " + e.getMessage());
            return;
        }

        // === Hiển thị danh sách lớp học hiện có ===
        if (classrooms.isEmpty()) {
            System.out.println("Hiện chưa có lớp học nào trong hệ thống! Hãy thêm lớp trước.");
            return;
        }

        System.out.println("\nDanh sách lớp học hiện có:");
        System.out.println("──────────────────────────────────────────────");
        for (Classroom c : classrooms) {
            System.out.printf("• %s - %s (Niên khóa: %s)\n",
                    c.getClassId(), c.getClassName(), c.getCourse());
        }
        System.out.println("──────────────────────────────────────────────");

        // === Nhập mã lớp học đã tồn tại ===
        Classroom selectedClass = null;
        while (selectedClass == null) {
            String classId = InputUtil.getString("Nhập mã lớp học: ").trim();
            for (Classroom c : classrooms) {
                if (c.getClassId().equalsIgnoreCase(classId)) {
                    selectedClass = c;
                    break;
                }
            }
            if (selectedClass == null) {
                System.out.println("Không tìm thấy lớp có mã '" + classId + "'. Vui lòng nhập lại!");
            }
        }

        // === Tự động lấy tên lớp & niên khóa từ file ===
        String className = selectedClass.getClassName();
        String course = selectedClass.getCourse();

        String parentPhone = InputUtil.getString("Nhập số điện thoại phụ huynh: ");
        String address = InputUtil.getString("Nhập địa chỉ: ");

        // Nhập trạng thái học sinh bằng số
        int statusCode = -1;
        String status = "";
        while (statusCode < 0 || statusCode > 2) {
            System.out.println("\nChọn trạng thái học sinh:");
            System.out.println("0 - Đang học");
            System.out.println("1 - Tạm nghỉ");
            System.out.println("2 - Đã tốt nghiệp");
            statusCode = InputUtil.getInt("Nhập lựa chọn (0/1/2): ");

            switch (statusCode) {
                case 0 -> status = "Đang học";
                case 1 -> status = "Tạm nghỉ";
                case 2 -> status = "Đã tốt nghiệp";
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        // === Gọi service để thêm học sinh ===
        boolean success = studentService.addStudent(
                id, name, dateOfBirth, gender, className, course,
                parentPhone, address, status
        );

        if (success) {
            System.out.println("Học sinh đã được thêm vào hệ thống.");
        } else {
            System.out.println("Thêm học sinh thất bại. Vui lòng thử lại!");
        }

        System.out.println("\nNhấn Enter để quay lại menu...");
        scanner.nextLine();
    }
}
