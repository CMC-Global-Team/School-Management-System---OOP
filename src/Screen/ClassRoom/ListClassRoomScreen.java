package Screen.ClassRoom;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.ClassroomService;
import Utils.InputUtil;
import java.util.Optional;

public class ListClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public ListClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        Danh Sách Tất Cả Lớp Học           │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CHỨC NĂNG CUNG CẤP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Hiển thị danh sách: Tất cả lớp học trong hệ thống                      │");
        System.out.println("│ Xem chi tiết: Thông tin đầy đủ của một lớp học cụ thể                   │");
        System.out.println("│                                                                         │");
        System.out.println("│ THÔNG TIN HIỂN THỊ:                                                     │");
        System.out.println("│ - Mã lớp: Định danh duy nhất của lớp học                                │");
        System.out.println("│ - Tên lớp: Tên gọi của lớp học                                           │");
        System.out.println("│ - Năm học: Năm học hiện tại của lớp                                     │");
        System.out.println("│ - Niên khóa: Khóa học của học sinh trong lớp                            │");
        System.out.println("│                                                                         │");
        System.out.println("│ TÙY CHỌN:                                                                │");
        System.out.println("│ 1 - Xem thông tin chi tiết của một lớp học                               │");
        System.out.println("│ 0 - Quay lại menu trước                                                  │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    DANH SÁCH LỚP HỌC");
        System.out.println("=".repeat(50));
        
        // Kiểm tra có dữ liệu không
        if (classroomService.getTotalClasses() == 0) {
            System.out.println("Không có lớp học nào trong hệ thống!");
            pause();
            return;
        }
        
        // Sử dụng service để hiển thị danh sách
        System.out.println("\nDANH SÁCH TẤT CẢ LỚP HỌC:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           DANH SÁCH LỚP HỌC                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s │ %-20s │ %-15s │ %-15s │%n", "Mã lớp", "Tên lớp", "Năm học", "Niên khóa");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        classroomService.getAllClasses().forEach(classroom -> {
            System.out.printf("│ %-10s │ %-20s │ %-15s │ %-15s │%n", 
                classroom.getClassId(), classroom.getClassName(), 
                classroom.getSchoolYear(), classroom.getCourse());
        });
        
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        
        System.out.println("\nTổng số lớp học: " + classroomService.getTotalClasses());
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("TÙY CHỌN:");
        System.out.println("-".repeat(50));
        System.out.println("1 - Xem thông tin chi tiết của một lớp học");
        System.out.println("0 - Quay lại menu trước");
        
        int choice = -1;
        while (choice < 0 || choice > 1) {
            if (choice != -1) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn 0 hoặc 1.");
            }
            choice = InputUtil.getInt("\nNhập lựa chọn của bạn (0-1): ");
        }
        
        if (choice == 1) {
            viewDetails();
        } else {
            System.out.println("\nQuay lại menu trước...");
        }
        
        pause();
    }

    private void viewDetails() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    XEM CHI TIẾT LỚP HỌC");
        System.out.println("=".repeat(50));
        
        // Hiển thị danh sách mã lớp để tham khảo
        System.out.println("\nDANH SÁCH MÃ LỚP:");
        classroomService.getAllClasses().forEach(classroom -> {
            System.out.println("- " + classroom.getClassId() + " (" + classroom.getClassName() + ")");
        });
        
        // Nhập mã lớp với validation
        String classId = null;
        Optional<Classroom> classroomOpt = Optional.empty();
        
        while (classroomOpt.isEmpty()) {
            if (classId != null) {
                System.out.println("Không tìm thấy lớp học có mã '" + classId + "'!");
            }
            classId = InputUtil.getNonEmptyString("\nNhập mã lớp để xem chi tiết (VD: Lớp001): ");
            classroomOpt = classroomService.findById(classId);
        }
        
        Classroom classroom = classroomOpt.get();
        
        // Hiển thị thông tin chi tiết
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                           CHI TIẾT LỚP HỌC");
        System.out.println("=".repeat(80));
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CHI TIẾT                             │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã lớp: %-50s │%n", classroom.getClassId());
        System.out.printf("│ Tên lớp: %-50s │%n", classroom.getClassName());
        System.out.printf("│ Năm học: %-50s │%n", classroom.getSchoolYear());
        System.out.printf("│ Niên khóa: %-50s │%n", classroom.getCourse());
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        System.out.println("=".repeat(80));
        
        pause();
    }
}