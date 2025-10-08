package Screen.Student;

import Models.Student;
import Screen.AbstractScreen;
import Services.StudentService;
import java.util.List;

public class FilterStudentScreen extends AbstractScreen {
    private final StudentService studentService;

    public FilterStudentScreen() {
        super();
        this.studentService = StudentService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           LỌC HỌC SINH                    │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Lọc theo lớp                         │");
        System.out.println("│  2. Lọc theo trạng thái                   │");
        System.out.println("│  3. Lọc theo giới tính                   │");
        System.out.println("│  4. Lọc theo nhiều tiêu chí               │");
        System.out.println("│  0. Quay lại menu học sinh                │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        boolean running = true;
        while (running) {
            clearScreen();
            display();
            int choice = inputInt("Nhập lựa chọn của bạn: ");

            switch (choice) {
                case 1:
                    filterByClass();
                    break;
                case 2:
                    filterByStatus();
                    break;
                case 3:
                    filterByGender();
                    break;
                case 4:
                    filterByMultipleCriteria();
                    break;
                case 0:
                    System.out.println("\nĐang quay lại menu học sinh...");
                    running = false;
                    break;
                default:
                    System.out.println("\nLựa chọn không hợp lệ. Vui lòng thử lại.");
                    pause();
            }
        }
    }

    private void filterByClass() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           LỌC THEO LỚP                   │");
        System.out.println("└──────────────────────────────────────────┘");

        String className = input("Nhập tên lớp cần lọc: ");
        if (className.trim().isEmpty()) {
            System.out.println("Tên lớp không được để trống!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByClass(className);
        studentService.displayFilterResults(filteredStudents, "THEO LỚP: " + className);

        if (!filteredStudents.isEmpty()) {
            System.out.println("\nBạn có muốn xuất danh sách này ra file không? (y/n): ");
            String exportChoice = scanner.nextLine().trim().toLowerCase();
            if (exportChoice.equals("y") || exportChoice.equals("yes")) {
                String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
                if (studentService.exportStudentsToFile(filename, filteredStudents)) {
                    System.out.println("Xuất file thành công: " + filename);
                } else {
                    System.out.println("Lỗi khi xuất file!");
                }
            }
        }

        pause();
    }

    private void filterByStatus() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         LỌC THEO TRẠNG THÁI              │");
        System.out.println("└──────────────────────────────────────────┘");

        System.out.println("Các trạng thái có thể có:");
        System.out.println("- Đang học");
        System.out.println("- Nghỉ học");
        System.out.println("- Chuyển lớp");
        System.out.println("- Tốt nghiệp");

        String status = input("Nhập trạng thái cần lọc: ");
        if (status.trim().isEmpty()) {
            System.out.println("Trạng thái không được để trống!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByStatus(status);
        studentService.displayFilterResults(filteredStudents, "THEO TRẠNG THÁI: " + status);

        if (!filteredStudents.isEmpty()) {
            System.out.println("\nBạn có muốn xuất danh sách này ra file không? (y/n): ");
            String exportChoice = scanner.nextLine().trim().toLowerCase();
            if (exportChoice.equals("y") || exportChoice.equals("yes")) {
                String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
                if (studentService.exportStudentsToFile(filename, filteredStudents)) {
                    System.out.println("Xuất file thành công: " + filename);
                } else {
                    System.out.println("Lỗi khi xuất file!");
                }
            }
        }

        pause();
    }

    private void filterByGender() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         LỌC THEO GIỚI TÍNH               │");
        System.out.println("└──────────────────────────────────────────┘");

        System.out.println("Các giới tính có thể có:");
        System.out.println("- Nam");
        System.out.println("- Nữ");

        String gender = input("Nhập giới tính cần lọc: ");
        if (gender.trim().isEmpty()) {
            System.out.println("Giới tính không được để trống!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByGender(gender);
        studentService.displayFilterResults(filteredStudents, "THEO GIỚI TÍNH: " + gender);

        if (!filteredStudents.isEmpty()) {
            System.out.println("\nBạn có muốn xuất danh sách này ra file không? (y/n): ");
            String exportChoice = scanner.nextLine().trim().toLowerCase();
            if (exportChoice.equals("y") || exportChoice.equals("yes")) {
                String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
                if (studentService.exportStudentsToFile(filename, filteredStudents)) {
                    System.out.println("Xuất file thành công: " + filename);
                } else {
                    System.out.println("Lỗi khi xuất file!");
                }
            }
        }

        pause();
    }

    private void filterByMultipleCriteria() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│      LỌC THEO NHIỀU TIÊU CHÍ              │");
        System.out.println("└──────────────────────────────────────────┘");

        System.out.println("Nhập thông tin lọc (để trống nếu không muốn lọc theo tiêu chí đó):");
        
        String className = input("Tên lớp: ");
        String status = input("Trạng thái: ");
        String gender = input("Giới tính: ");

        // Kiểm tra ít nhất một tiêu chí được nhập
        if (className.trim().isEmpty() && status.trim().isEmpty() && gender.trim().isEmpty()) {
            System.out.println("Bạn phải nhập ít nhất một tiêu chí lọc!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterStudents(className, status, gender);
        
        String filterDescription = "THEO NHIỀU TIÊU CHÍ";
        if (!className.trim().isEmpty()) filterDescription += " - Lớp: " + className;
        if (!status.trim().isEmpty()) filterDescription += " - Trạng thái: " + status;
        if (!gender.trim().isEmpty()) filterDescription += " - Giới tính: " + gender;
        
        studentService.displayFilterResults(filteredStudents, filterDescription);

        if (!filteredStudents.isEmpty()) {
            System.out.println("\nBạn có muốn xuất danh sách này ra file không? (y/n): ");
            String exportChoice = scanner.nextLine().trim().toLowerCase();
            if (exportChoice.equals("y") || exportChoice.equals("yes")) {
                String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
                if (studentService.exportStudentsToFile(filename, filteredStudents)) {
                    System.out.println("Xuất file thành công: " + filename);
                } else {
                    System.out.println("Lỗi khi xuất file!");
                }
            }
        }

        pause();
    }
}
