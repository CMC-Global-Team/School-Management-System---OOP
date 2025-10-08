package Screen.Student;

import Models.Student;
import Screen.AbstractScreen;
import Services.StudentService;
import java.util.List;

public class ExportStudentScreen extends AbstractScreen {
    private final StudentService studentService;

    public ExportStudentScreen() {
        super();
        this.studentService = StudentService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         XUẤT DANH SÁCH HỌC SINH            │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Xuất tất cả học sinh                 │");
        System.out.println("│  2. Xuất học sinh theo lớp                │");
        System.out.println("│  3. Xuất học sinh theo trạng thái         │");
        System.out.println("│  4. Xuất học sinh theo giới tính          │");
        System.out.println("│  5. Xuất học sinh theo nhiều tiêu chí     │");
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
                    exportAllStudents();
                    break;
                case 2:
                    exportByClass();
                    break;
                case 3:
                    exportByStatus();
                    break;
                case 4:
                    exportByGender();
                    break;
                case 5:
                    exportByMultipleCriteria();
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

    private void exportAllStudents() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       XUẤT TẤT CẢ HỌC SINH               │");
        System.out.println("└──────────────────────────────────────────┘");

        List<Student> allStudents = studentService.getAllStudents();
        
        if (allStudents.isEmpty()) {
            System.out.println("Không có học sinh nào trong hệ thống!");
            pause();
            return;
        }

        System.out.println("Tổng số học sinh: " + allStudents.size());
        System.out.println("Bạn có muốn xuất tất cả học sinh ra file không? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Hủy xuất file.");
            pause();
            return;
        }

        String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
        
        if (studentService.exportAllStudentsToFile(filename)) {
            System.out.println("Xuất file thành công: " + filename);
            System.out.println("File chứa " + allStudents.size() + " học sinh.");
        } else {
            System.out.println("Lỗi khi xuất file!");
        }

        pause();
    }

    private void exportByClass() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       XUẤT THEO LỚP                      │");
        System.out.println("└──────────────────────────────────────────┘");

        String className = input("Nhập tên lớp cần xuất: ");
        if (className.trim().isEmpty()) {
            System.out.println("Tên lớp không được để trống!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByClass(className);
        
        if (filteredStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            pause();
            return;
        }

        System.out.println("Tìm thấy " + filteredStudents.size() + " học sinh trong lớp: " + className);
        System.out.println("Bạn có muốn xuất danh sách này ra file không? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Hủy xuất file.");
            pause();
            return;
        }

        String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
        
        if (studentService.exportStudentsToFile(filename, filteredStudents)) {
            System.out.println("Xuất file thành công: " + filename);
            System.out.println("File chứa " + filteredStudents.size() + " học sinh của lớp: " + className);
        } else {
            System.out.println("Lỗi khi xuất file!");
        }

        pause();
    }

    private void exportByStatus() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       XUẤT THEO TRẠNG THÁI               │");
        System.out.println("└──────────────────────────────────────────┘");

        System.out.println("Các trạng thái có thể có:");
        System.out.println("- Đang học");
        System.out.println("- Nghỉ học");
        System.out.println("- Chuyển lớp");
        System.out.println("- Tốt nghiệp");

        String status = input("Nhập trạng thái cần xuất: ");
        if (status.trim().isEmpty()) {
            System.out.println("Trạng thái không được để trống!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByStatus(status);
        
        if (filteredStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào với trạng thái: " + status);
            pause();
            return;
        }

        System.out.println("Tìm thấy " + filteredStudents.size() + " học sinh với trạng thái: " + status);
        System.out.println("Bạn có muốn xuất danh sách này ra file không? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Hủy xuất file.");
            pause();
            return;
        }

        String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
        
        if (studentService.exportStudentsToFile(filename, filteredStudents)) {
            System.out.println("Xuất file thành công: " + filename);
            System.out.println("File chứa " + filteredStudents.size() + " học sinh với trạng thái: " + status);
        } else {
            System.out.println("Lỗi khi xuất file!");
        }

        pause();
    }

    private void exportByGender() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       XUẤT THEO GIỚI TÍNH               │");
        System.out.println("└──────────────────────────────────────────┘");

        System.out.println("Các giới tính có thể có:");
        System.out.println("- Nam");
        System.out.println("- Nữ");

        String gender = input("Nhập giới tính cần xuất: ");
        if (gender.trim().isEmpty()) {
            System.out.println("Giới tính không được để trống!");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByGender(gender);
        
        if (filteredStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào với giới tính: " + gender);
            pause();
            return;
        }

        System.out.println("Tìm thấy " + filteredStudents.size() + " học sinh với giới tính: " + gender);
        System.out.println("Bạn có muốn xuất danh sách này ra file không? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Hủy xuất file.");
            pause();
            return;
        }

        String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
        
        if (studentService.exportStudentsToFile(filename, filteredStudents)) {
            System.out.println("Xuất file thành công: " + filename);
            System.out.println("File chứa " + filteredStudents.size() + " học sinh với giới tính: " + gender);
        } else {
            System.out.println("Lỗi khi xuất file!");
        }

        pause();
    }

    private void exportByMultipleCriteria() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│    XUẤT THEO NHIỀU TIÊU CHÍ               │");
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
        
        if (filteredStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào phù hợp với tiêu chí đã nhập!");
            pause();
            return;
        }

        String filterDescription = "theo tiêu chí";
        if (!className.trim().isEmpty()) filterDescription += " - Lớp: " + className;
        if (!status.trim().isEmpty()) filterDescription += " - Trạng thái: " + status;
        if (!gender.trim().isEmpty()) filterDescription += " - Giới tính: " + gender;

        System.out.println("Tìm thấy " + filteredStudents.size() + " học sinh " + filterDescription);
        System.out.println("Bạn có muốn xuất danh sách này ra file không? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Hủy xuất file.");
            pause();
            return;
        }

        String filename = input("Nhập tên file (không cần .txt): ") + ".txt";
        
        if (studentService.exportStudentsToFile(filename, filteredStudents)) {
            System.out.println("Xuất file thành công: " + filename);
            System.out.println("File chứa " + filteredStudents.size() + " học sinh " + filterDescription);
        } else {
            System.out.println("Lỗi khi xuất file!");
        }

        pause();
    }
}
