package Screen.Student;

import Screen.AbstractScreen;
import Models.Student;
import Services.StudentService;
import Utils.InputUtil;

import java.util.List;
import java.util.stream.Collectors;

public class SearchStudentScreen extends AbstractScreen {

    private final StudentService studentService = StudentService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│           TÌM KIẾM THÔNG TIN HỌC SINH        │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("Chọn tiêu chí tìm kiếm:");
        System.out.println("1. Theo mã học sinh");
        System.out.println("2. Theo họ tên");
        System.out.println("3. Theo lớp");
        System.out.println("4. Theo niên khóa");
        System.out.println("0. Quay lại");
        System.out.println("--------------------------------------------");

        int choice = InputUtil.getInt("Lựa chọn của bạn: ");
        List<Student> results = null;

        List<Student> students = studentService.getAllStudents();

        switch (choice) {
            case 1 -> {
                String id = InputUtil.getString("Nhập mã học sinh: ").trim().toLowerCase();
                results = students.stream()
                        .filter(s -> s.getId().toLowerCase().contains(id))
                        .collect(Collectors.toList());
            }
            case 2 -> {
                String name = InputUtil.getString("Nhập họ tên học sinh: ").trim().toLowerCase();
                results = students.stream()
                        .filter(s -> s.getName().toLowerCase().contains(name))
                        .collect(Collectors.toList());
            }
            case 3 -> {
                String className = InputUtil.getString("Nhập tên lớp: ").trim().toLowerCase();
                results = students.stream()
                        .filter(s -> s.getClassName().toLowerCase().contains(className))
                        .collect(Collectors.toList());
            }
            case 4 -> {
                String course = InputUtil.getString("Nhập niên khóa (VD: 2023–2027): ").trim().toLowerCase();
                results = students.stream()
                        .filter(s -> s.getCourse().toLowerCase().contains(course))
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
            System.out.println("\nKhông tìm thấy học sinh nào phù hợp!");
        } else {
            System.out.println("\nKết quả tìm kiếm (" + results.size() + " học sinh):");
            System.out.printf("%-8s %-20s %-12s %-8s %-8s %-10s %-15s %-15s %-10s%n",
                    "Mã HS", "Họ tên", "Ngày sinh", "GT", "Lớp", "Niên khóa", "Phụ huynh", "Địa chỉ", "Trạng thái");
            System.out.println("-------------------------------------------------------------------------------------------------------------");

            for (Student s : results) {
                System.out.printf("%-8s %-20s %-12s %-8s %-8s %-10s %-15s %-15s %-10s%n",
                        s.getId(),
                        s.getName(),
                        s.getDateOfBirth(),
                        s.getGender(),
                        s.getClassName(),
                        s.getCourse(),
                        s.getParentPhone(),
                        s.getAddress(),
                        s.getStatus());
            }
        }

        System.out.println("\nNhấn Enter để quay lại menu...");
        scanner.nextLine();
    }
}