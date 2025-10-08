package Screen.Student;

import Screen.AbstractScreen;
import Services.StudentService;
import Utils.InputUtil;
import java.util.List;
import java.util.Optional;

/**
 * TransferStudentScreen - Màn hình chuyển lớp cho học sinh
 * Cho phép người dùng chuyển học sinh từ lớp này sang lớp khác
 */
public class TransferStudentScreen extends AbstractScreen {
    private final StudentService studentService;

    public TransferStudentScreen() {
        this.studentService = StudentService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            CHUYỂN LỚP HỌC SINH            │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│              HƯỚNG DẪN CHUYỂN LỚP        │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ Bước 1: Nhập mã học sinh cần chuyển lớp  │");
        System.out.println("│ Bước 2: Xem thông tin học sinh hiện tại  │");
        System.out.println("│ Bước 3: Chọn lớp mới từ danh sách        │");
        System.out.println("│ Bước 4: Xác nhận và thực hiện chuyển lớp │");
        System.out.println("│                                          │");
        System.out.println("│ Lưu ý:                                    │");
        System.out.println("│ - Học sinh phải tồn tại trong hệ thống   │");
        System.out.println("│ - Lớp mới phải khác lớp hiện tại         │");
        System.out.println("│ - Thông tin sẽ được cập nhật tự động     │");
        System.out.println("│ - Dữ liệu được đồng nhất trong file       │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n=== CHUYỂN LỚP HỌC SINH ===");
        
        // Bước 1: Nhập mã học sinh
        System.out.println("\n1. MÃ HỌC SINH CẦN CHUYỂN LỚP:");
        System.out.println("   - Format: Mã học sinh (VD: HS001, HS002)");
        System.out.println("   - Phải tồn tại trong hệ thống");
        String studentId = InputUtil.getNonEmptyString("   Nhập mã học sinh: ");
        
        // Kiểm tra học sinh có tồn tại không
        Optional<Models.Student> studentOpt = studentService.findById(studentId);
        if (studentOpt.isEmpty()) {
            System.out.println("   Không tìm thấy học sinh với mã '" + studentId + "'!");
            InputUtil.pressEnterToContinue();
            return;
        }

        Models.Student student = studentOpt.get();
        System.out.println("   Tìm thấy học sinh!");
        
        // Hiển thị thông tin học sinh hiện tại
        System.out.println("\n=== THÔNG TIN HỌC SINH HIỆN TẠI ===");
        System.out.println("Mã học sinh: " + student.getId());
        System.out.println("Họ tên: " + student.getName());
        System.out.println("Ngày sinh: " + student.getDateOfBirth());
        System.out.println("Giới tính: " + student.getGender());
        System.out.println("Lớp hiện tại: " + student.getClassName());
        System.out.println("Khóa: " + student.getCourse());
        System.out.println("Trạng thái: " + student.getStatus());

        // Bước 2: Hiển thị danh sách các lớp
        System.out.println("\n2. DANH SÁCH CÁC LỚP HIỆN CÓ:");
        studentService.displayAllClasses();

        // Bước 3: Chọn lớp mới
        System.out.println("\n3. CHỌN LỚP MỚI:");
        System.out.println("   - Lớp hiện tại: " + student.getClassName());
        System.out.println("   - Nhập tên lớp mới từ danh sách trên");
        
        String newClassName = InputUtil.getNonEmptyString("   Nhập tên lớp mới: ");
        
        // Kiểm tra lớp mới có khác lớp cũ không
        if (student.getClassName().equals(newClassName)) {
            System.out.println("   Lỗi: Học sinh đã ở lớp '" + newClassName + "' rồi!");
            InputUtil.pressEnterToContinue();
            return;
        }

        // Bước 4: Xác nhận chuyển lớp
        System.out.println("\n=== XÁC NHẬN CHUYỂN LỚP ===");
        System.out.println("Học sinh: " + student.getName() + " (" + studentId + ")");
        System.out.println("Từ lớp: " + student.getClassName());
        System.out.println("Sang lớp: " + newClassName);
        
        System.out.println("\nBạn có chắc chắn muốn chuyển lớp cho học sinh này?");
        System.out.println("1. Có - Thực hiện chuyển lớp");
        System.out.println("0. Không - Hủy bỏ");
        
        int confirmChoice = InputUtil.getInt("Nhập lựa chọn (0 hoặc 1): ");
        
        if (confirmChoice == 1) {
            // Thực hiện chuyển lớp
            if (studentService.transferStudent(studentId, newClassName)) {
                System.out.println("\nChuyển lớp thành công!");
                
                // Hiển thị thông tin sau khi chuyển
                System.out.println("\n=== THÔNG TIN SAU KHI CHUYỂN LỚP ===");
                studentService.findById(studentId).ifPresent(updatedStudent -> {
                    System.out.println("Mã học sinh: " + updatedStudent.getId());
                    System.out.println("Họ tên: " + updatedStudent.getName());
                    System.out.println("Lớp mới: " + updatedStudent.getClassName());
                    System.out.println("Trạng thái: " + updatedStudent.getStatus());
                });
                
                // Hiển thị số học sinh trong lớp cũ và mới
                System.out.println("\n=== THỐNG KÊ LỚP ===");
                int oldClassCount = studentService.getStudentsByClass(student.getClassName()).size();
                int newClassCount = studentService.getStudentsByClass(newClassName).size();
                
                System.out.println("Lớp " + student.getClassName() + ": " + oldClassCount + " học sinh");
                System.out.println("Lớp " + newClassName + ": " + newClassCount + " học sinh");
                
            } else {
                System.out.println("\nChuyển lớp thất bại!");
            }
        } else {
            System.out.println("\nĐã hủy bỏ việc chuyển lớp.");
        }
        
        InputUtil.pressEnterToContinue();
    }
    
    /**
     * Hiển thị menu chuyển lớp hàng loạt
     */
    public void displayBatchTransferMenu() {
        System.out.println("\n=== CHUYỂN LỚP HÀNG LOẠT ===");
        System.out.println("Chức năng này cho phép chuyển nhiều học sinh cùng lúc.");
        System.out.println("1. Chuyển tất cả học sinh từ lớp A sang lớp B");
        System.out.println("2. Chuyển học sinh theo điều kiện");
        System.out.println("0. Quay lại");
        
        int choice = InputUtil.getInt("Nhập lựa chọn: ");
        
        switch (choice) {
            case 1:
                batchTransferByClass();
                break;
            case 2:
                batchTransferByCondition();
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    /**
     * Chuyển tất cả học sinh từ lớp này sang lớp khác
     */
    private void batchTransferByClass() {
        System.out.println("\n=== CHUYỂN TẤT CẢ HỌC SINH THEO LỚP ===");
        
        // Hiển thị danh sách lớp
        studentService.displayAllClasses();
        
        String fromClass = InputUtil.getNonEmptyString("Nhập tên lớp nguồn: ");
        String toClass = InputUtil.getNonEmptyString("Nhập tên lớp đích: ");
        
        if (fromClass.equals(toClass)) {
            System.out.println("Lỗi: Lớp nguồn và lớp đích không được giống nhau!");
            return;
        }
        
        // Lấy danh sách học sinh trong lớp nguồn
        List<Models.Student> studentsToTransfer = studentService.getStudentsByClass(fromClass);
        
        if (studentsToTransfer.isEmpty()) {
            System.out.println("Không có học sinh nào trong lớp '" + fromClass + "'!");
            return;
        }
        
        System.out.println("\nSẽ chuyển " + studentsToTransfer.size() + " học sinh từ lớp '" + fromClass + "' sang lớp '" + toClass + "'");
        System.out.println("Bạn có chắc chắn? (1: Có, 0: Không)");
        
        int confirm = InputUtil.getInt("Nhập lựa chọn: ");
        if (confirm == 1) {
            int successCount = 0;
            for (Models.Student student : studentsToTransfer) {
                if (studentService.transferStudent(student.getId(), toClass)) {
                    successCount++;
                }
            }
            System.out.println("Chuyển lớp hoàn thành: " + successCount + "/" + studentsToTransfer.size() + " học sinh");
        }
    }
    
    /**
     * Chuyển học sinh theo điều kiện
     */
    private void batchTransferByCondition() {
        System.out.println("\n=== CHUYỂN HỌC SINH THEO ĐIỀU KIỆN ===");
        System.out.println("Chức năng này sẽ được phát triển trong phiên bản tiếp theo.");
        System.out.println("Hiện tại chỉ hỗ trợ chuyển lớp từng học sinh một.");
    }
}
