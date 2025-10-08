package Screen.Student;

import Models.Classroom;
import Models.Student;
import Screen.AbstractScreen;
import Services.StudentService;
import Utils.FileUtil;
import Utils.InputUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpdateStudentScreen extends AbstractScreen {

    private final StudentService studentService = StudentService.getInstance();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│           CẬP NHẬT THÔNG TIN HỌC SINH        │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String id = InputUtil.getString("Nhập mã học sinh cần cập nhật: ").trim();

        // Tìm học sinh theo mã
        Optional<Student> optionalStudent = studentService.findById(id);
        if (optionalStudent.isEmpty()) {
            System.out.println("Không tìm thấy học sinh có mã '" + id + "'.");
            System.out.println("\nNhấn Enter để quay lại menu...");
            scanner.nextLine();
            return;
        }

        Student student = optionalStudent.get();

        // Hiển thị thông tin hiện tại
        System.out.println("\n--- Thông tin hiện tại ---");
        showCurrentInfo(student);

        System.out.println("\n--- Nhập thông tin mới (bỏ trống nếu giữ nguyên) ---");

        // ===== Họ tên =====
        String name = InputUtil.getString("Họ và tên [" + student.getName() + "]: ").trim();
        if (!name.isEmpty()) student.setName(name);

        // ===== Ngày sinh =====
        String dateStr = InputUtil.getString("Ngày sinh [" +
                student.getDateOfBirth().format(dateFormatter) + "] (dd/MM/yyyy): ").trim();
        if (!dateStr.isEmpty()) {
            try {
                LocalDate newDate = LocalDate.parse(dateStr, dateFormatter);
                student.setDateOfBirth(newDate);
            } catch (DateTimeParseException e) {
                System.out.println("Ngày sinh không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }

        // ===== Giới tính =====
        String gender = InputUtil.getString("Giới tính [" + student.getGender() + "]: ").trim();
        if (!gender.isEmpty()) student.setGender(gender);

        // ===== Cập nhật lớp =====
        System.out.println("\nDanh sách lớp học hiện có:");
        List<Classroom> classrooms = new ArrayList<>();
        try {
            if (FileUtil.fileExists("data/classrooms.txt")) {
                List<String> lines = FileUtil.readLines("data/classrooms.txt");
                for (String line : lines) {
                    Classroom c = Classroom.fromString(line);
                    if (c != null) classrooms.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file lớp học: " + e.getMessage());
        }

        if (!classrooms.isEmpty()) {
            System.out.println("──────────────────────────────────────────────");
            for (Classroom c : classrooms) {
                System.out.printf("• %s - %s (Niên khóa: %s)\n",
                        c.getClassId(), c.getClassName(), c.getCourse());
            }
            System.out.println("──────────────────────────────────────────────");

            String classId = InputUtil.getString("Mã lớp mới (Enter để giữ nguyên " + student.getClassName() + "): ").trim();
            if (!classId.isEmpty()) {
                Classroom selected = classrooms.stream()
                        .filter(c -> c.getClassId().equalsIgnoreCase(classId))
                        .findFirst().orElse(null);
                if (selected != null) {
                    student.setClassName(selected.getClassName());
                    student.setCourse(selected.getCourse());
                } else {
                    System.out.println("Không tìm thấy lớp có mã '" + classId + "', giữ nguyên giá trị cũ.");
                }
            }
        } else {
            System.out.println("Không có dữ liệu lớp học trong hệ thống!");
        }

        // ===== Số điện thoại phụ huynh =====
        String parentPhone = InputUtil.getString("SĐT phụ huynh [" + student.getParentPhone() + "]: ").trim();
        if (!parentPhone.isEmpty()) student.setParentPhone(parentPhone);

        // ===== Địa chỉ =====
        String address = InputUtil.getString("Địa chỉ [" + student.getAddress() + "]: ").trim();
        if (!address.isEmpty()) student.setAddress(address);

        // ===== Trạng thái =====
        System.out.println("\nChọn trạng thái học sinh (Enter để giữ nguyên " + student.getStatus() + "):");
        System.out.println("0 - Đang học");
        System.out.println("1 - Tạm nghỉ");
        System.out.println("2 - Đã tốt nghiệp");

        String statusInput = InputUtil.getString("Nhập lựa chọn (0/1/2): ").trim();
        if (!statusInput.isEmpty()) {
            try {
                int statusCode = Integer.parseInt(statusInput);
                switch (statusCode) {
                    case 0 -> student.setStatus("Đang học");
                    case 1 -> student.setStatus("Tạm nghỉ");
                    case 2 -> student.setStatus("Đã tốt nghiệp");
                    default -> System.out.println("Lựa chọn không hợp lệ, giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhập không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }

        // ===== Gọi service cập nhật =====
        boolean success = studentService.updateStudent(student);

        if (success) {
            System.out.println("\nCập nhật học sinh thành công!");
        } else {
            System.out.println("\nCập nhật thất bại. Vui lòng thử lại!");
        }

        System.out.println("\nNhấn Enter để quay lại menu...");
        scanner.nextLine();
    }

    private void showCurrentInfo(Student student) {
        System.out.println("------------------------------------------");
        System.out.println("Mã học sinh : " + student.getId());
        System.out.println("Họ và tên   : " + student.getName());
        System.out.println("Ngày sinh   : " + student.getDateOfBirth().format(dateFormatter));
        System.out.println("Giới tính   : " + student.getGender());
        System.out.println("Lớp         : " + student.getClassName());
        System.out.println("Niên khóa   : " + student.getCourse());
        System.out.println("SĐT phụ huynh: " + student.getParentPhone());
        System.out.println("Địa chỉ     : " + student.getAddress());
        System.out.println("Trạng thái  : " + student.getStatus());
        System.out.println("------------------------------------------");
    }
}
