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
        System.out.println("│  6. Xem thông tin chi tiết các lớp        │");
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
                case 6:
                    displayClassDetails();
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

        // Hiển thị danh sách các lớp có sẵn từ cả hai nguồn
        System.out.println("\n=== DANH SÁCH CÁC LỚP HIỆN CÓ ===");
        System.out.println("(Lấy từ cả classrooms.txt và students.txt)");
        studentService.displayAllClasses();
        
        System.out.println("\n=== CHỌN LỚP CẦN XUẤT ===");
        System.out.println("Nhập tên lớp từ danh sách trên:");
        String className = input("Tên lớp: ");
        
        if (className.trim().isEmpty()) {
            System.out.println("Tên lớp không được để trống!");
            pause();
            return;
        }

        // Kiểm tra lớp có tồn tại không (từ cả hai nguồn)
        List<String> availableClasses = studentService.getAllClassNamesFromBothSources();
        boolean classExists = availableClasses.stream()
                .anyMatch(cls -> cls.equalsIgnoreCase(className.trim()));
        
        if (!classExists) {
            System.out.println("Lớp '" + className + "' không tồn tại trong hệ thống!");
            System.out.println("Vui lòng chọn lớp từ danh sách trên.");
            pause();
            return;
        }

        List<Student> filteredStudents = studentService.filterByClass(className.trim());
        
        if (filteredStudents.isEmpty()) {
            System.out.println("Không có học sinh nào trong lớp: " + className);
            pause();
            return;
        }

        // Hiển thị danh sách học sinh sẽ được xuất
        System.out.println("\n=== DANH SÁCH HỌC SINH SẼ XUẤT ===");
        studentService.displayStudentsByClass(className.trim());
        
        System.out.println("\nBạn có muốn xuất danh sách này ra file không? (y/n): ");
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
        
        // Hiển thị danh sách lớp để tham khảo từ cả hai nguồn
        System.out.println("\n=== DANH SÁCH CÁC LỚP HIỆN CÓ ===");
        System.out.println("(Lấy từ cả classrooms.txt và students.txt)");
        studentService.displayAllClasses();
        
        System.out.println("\n=== DANH SÁCH TRẠNG THÁI CÓ THỂ CÓ ===");
        System.out.println("- Đang học");
        System.out.println("- Nghỉ học");
        System.out.println("- Chuyển lớp");
        System.out.println("- Tốt nghiệp");
        
        System.out.println("\n=== DANH SÁCH GIỚI TÍNH CÓ THỂ CÓ ===");
        System.out.println("- Nam");
        System.out.println("- Nữ");
        
        System.out.println("\n=== NHẬP TIÊU CHÍ LỌC ===");
        String className = input("Tên lớp (từ danh sách trên): ");
        String status = input("Trạng thái: ");
        String gender = input("Giới tính: ");

        // Kiểm tra ít nhất một tiêu chí được nhập
        if (className.trim().isEmpty() && status.trim().isEmpty() && gender.trim().isEmpty()) {
            System.out.println("Bạn phải nhập ít nhất một tiêu chí lọc!");
            pause();
            return;
        }

        // Kiểm tra lớp có tồn tại không (nếu được nhập) - từ cả hai nguồn
        if (!className.trim().isEmpty()) {
            List<String> availableClasses = studentService.getAllClassNamesFromBothSources();
            boolean classExists = availableClasses.stream()
                    .anyMatch(cls -> cls.equalsIgnoreCase(className.trim()));
            
            if (!classExists) {
                System.out.println("Lớp '" + className + "' không tồn tại trong hệ thống!");
                System.out.println("Vui lòng chọn lớp từ danh sách trên.");
                pause();
                return;
            }
        }

        List<Student> filteredStudents = studentService.filterStudents(className.trim(), status.trim(), gender.trim());
        
        if (filteredStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào phù hợp với tiêu chí đã nhập!");
            pause();
            return;
        }

        String filterDescription = "theo tiêu chí";
        if (!className.trim().isEmpty()) filterDescription += " - Lớp: " + className.trim();
        if (!status.trim().isEmpty()) filterDescription += " - Trạng thái: " + status.trim();
        if (!gender.trim().isEmpty()) filterDescription += " - Giới tính: " + gender.trim();

        System.out.println("\n=== DANH SÁCH HỌC SINH SẼ XUẤT ===");
        System.out.println("Tìm thấy " + filteredStudents.size() + " học sinh " + filterDescription);
        
        // Hiển thị danh sách học sinh sẽ được xuất
        studentService.displayFilterResults(filteredStudents, "xuất file");
        
        System.out.println("\nBạn có muốn xuất danh sách này ra file không? (y/n): ");
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

    /**
     * Hiển thị thông tin chi tiết về các lớp
     */
    private void displayClassDetails() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       THÔNG TIN CHI TIẾT CÁC LỚP          │");
        System.out.println("└──────────────────────────────────────────┘");

        System.out.println("\n=== THÔNG TIN CHI TIẾT CÁC LỚP ===");
        System.out.println("Hiển thị thông tin từ cả classrooms.txt và students.txt:");
        
        studentService.displayAllClassesDetailed();
        
        System.out.println("\n=== GIẢI THÍCH ===");
        System.out.println("- Có trong Classroom: Lớp được định nghĩa trong file classrooms.txt");
        System.out.println("- Có trong Student: Lớp có học sinh trong file students.txt");
        System.out.println("- Số HS: Số lượng học sinh hiện tại trong lớp");
        
        pause();
    }
}
