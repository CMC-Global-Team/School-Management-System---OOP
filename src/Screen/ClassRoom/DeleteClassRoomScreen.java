package Screen.ClassRoom;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.ClassroomService;
import Utils.InputUtil;
import java.util.Optional;

public class DeleteClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public DeleteClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│               Xóa Lớp Học                │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã lớp: Mã lớp học cần xóa (VD: Lớp001) - Phải tồn tại trong hệ thống   │");
        System.out.println("│                                                                         │");
        System.out.println("│ CẢNH BÁO QUAN TRỌNG:                                                    │");
        System.out.println("│ - Hệ thống sẽ hiển thị thông tin lớp học trước khi xóa                  │");
        System.out.println("│ - Bạn phải xác nhận trước khi thực hiện xóa                             │");
        System.out.println("│ - Thao tác này KHÔNG THỂ HOÀN TÁC                                       │");
        System.out.println("│ - Hãy kiểm tra kỹ thông tin trước khi xác nhận                          │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý:                                                                   │");
        System.out.println("│ - Xóa lớp học có thể ảnh hưởng đến dữ liệu học sinh liên quan            │");
        System.out.println("│ - Hãy đảm bảo không còn học sinh nào thuộc lớp này                     │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    XÓA LỚP HỌC");
        System.out.println("=".repeat(50));
        
        if (classroomService.getTotalClasses() == 0) {
            System.out.println("Không có lớp học nào để xóa!");
            pause();
            return;
        }

        // Hiển thị danh sách lớp học hiện tại
        System.out.println("\nDANH SÁCH LỚP HỌC HIỆN TẠI:");
        System.out.println("Mã lớp    | Tên lớp           | Năm học      | Niên khóa");
        System.out.println("----------|-------------------|--------------|----------");
        
        classroomService.getAllClasses().forEach(classroom -> {
            System.out.printf("%-9s | %-17s | %-12s | %-8s%n", 
                classroom.getClassId(), classroom.getClassName(), 
                classroom.getSchoolYear(), classroom.getCourse());
        });

        // Nhập mã lớp với validation
        String classId = null;
        Optional<Classroom> classroomOpt = Optional.empty();
        
        while (classroomOpt.isEmpty()) {
            if (classId != null) {
                System.out.println("Không tìm thấy lớp học có mã '" + classId + "'!");
            }
            classId = InputUtil.getNonEmptyString("\nNhập mã lớp cần xóa (VD: Lớp001): ");
            classroomOpt = classroomService.findById(classId);
        }

        Classroom classroom = classroomOpt.get();
        
        // Hiển thị thông tin lớp học sẽ bị xóa
        System.out.println("\nTHÔNG TIN LỚP HỌC SẼ BỊ XÓA:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CHI TIẾT LỚP HỌC                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã lớp: %-50s │%n", classroom.getClassId());
        System.out.printf("│ Tên lớp: %-50s │%n", classroom.getClassName());
        System.out.printf("│ Năm học: %-50s │%n", classroom.getSchoolYear());
        System.out.printf("│ Niên khóa: %-50s │%n", classroom.getCourse());
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        
        // Xác nhận xóa
        System.out.println("\nCẢNH BÁO: Thao tác này không thể hoàn tác!");
        System.out.println("Bạn có chắc chắn muốn xóa lớp học này?");
        System.out.println("1 - Có, xóa lớp học");
        System.out.println("0 - Không, hủy bỏ");
        
        int confirm = InputUtil.getInt("Lựa chọn (0/1): ");
        
        if (confirm == 1) {
            boolean success = classroomService.deleteClass(classId);
            if (success) {
                System.out.println("\nXóa lớp học thành công!");
            } else {
                System.out.println("\nXóa lớp học thất bại!");
            }
        } else {
            System.out.println("\nĐã hủy bỏ việc xóa lớp học.");
        }
        
        pause();
    }
}