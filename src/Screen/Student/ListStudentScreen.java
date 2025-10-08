package Screen.Student;

import Models.Student;
import Screen.AbstractScreen;
import Services.StudentService;

import java.util.List;

public class ListStudentScreen extends AbstractScreen {

    private final StudentService studentService = StudentService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│         DANH SÁCH TOÀN BỘ HỌC SINH          │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("Hiện chưa có học sinh nào trong hệ thống.");
        } else {
            System.out.printf("%-10s %-20s %-12s %-8s %-10s %-15s %-15s %-20s %-15s%n",
                    "Mã HS", "Họ và tên", "Ngày sinh", "Giới", "Lớp", "Niên khóa",
                    "SĐT PH", "Địa chỉ", "Trạng thái");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");

            for (Student s : students) {
                System.out.printf("%-10s %-20s %-12s %-8s %-10s %-15s %-15s %-20s %-15s%n",
                        s.getId(),
                        s.getName(),
                        s.getDateOfBirth().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
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