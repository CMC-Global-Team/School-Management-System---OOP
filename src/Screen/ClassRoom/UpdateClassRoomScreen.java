package Screen.ClassRoom;

import Models.Classroom;
import Screen.AbstractScreen;
import Services.ClassroomService;
import Utils.InputUtil;
import java.util.Optional;

public class UpdateClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public UpdateClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            Cập Nhật Lớp Học               │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã lớp: Mã lớp học cần cập nhật (VD: Lớp001) - Phải tồn tại trong hệ thống│");
        System.out.println("│                                                                         │");
        System.out.println("│ TÙY CHỌN CẬP NHẬT:                                                      │");
        System.out.println("│ 1. Tên lớp: Cập nhật tên lớp học                                         │");
        System.out.println("│ 2. Năm học: Cập nhật năm học (YYYY-YYYY)                                │");
        System.out.println("│ 3. Niên khóa: Cập nhật niên khóa (YYYY-YYYY)                            │");
        System.out.println("│ 4. Cập nhật tất cả: Cập nhật toàn bộ thông tin                          │");
        System.out.println("│ 0. Hủy: Thoát khỏi chức năng cập nhật                                   │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý:                                                                   │");
        System.out.println("│ - Hệ thống sẽ hiển thị thông tin hiện tại trước khi cập nhật             │");
        System.out.println("│ - Có thể để trống các trường không muốn thay đổi                         │");
        System.out.println("│ - Định dạng năm học và niên khóa: YYYY-YYYY                              │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    CẬP NHẬT LỚP HỌC");
        System.out.println("=".repeat(50));
        
        if (classroomService.getTotalClasses() == 0) {
            System.out.println("Không có lớp học nào để cập nhật!");
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
            classId = InputUtil.getNonEmptyString("\nNhập mã lớp cần cập nhật (VD: Lớp001): ");
            classroomOpt = classroomService.findById(classId);
        }
        
        Classroom classroom = classroomOpt.get();

        // Hiển thị thông tin hiện tại
        System.out.println("\nTHÔNG TIN LỚP HỌC HIỆN TẠI:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CHI TIẾT LỚP HỌC                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã lớp: %-50s │%n", classroom.getClassId());
        System.out.printf("│ Tên lớp: %-50s │%n", classroom.getClassName());
        System.out.printf("│ Năm học: %-50s │%n", classroom.getSchoolYear());
        System.out.printf("│ Niên khóa: %-50s │%n", classroom.getCourse());
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("CHỌN THÔNG TIN CẦN CẬP NHẬT");
        System.out.println("-".repeat(50));
        System.out.println("1 - Tên lớp");
        System.out.println("2 - Năm học");
        System.out.println("3 - Niên khóa");
        System.out.println("4 - Cập nhật tất cả");
        System.out.println("0 - Hủy bỏ");
        
        int choice = -1;
        while (choice < 0 || choice > 4) {
            if (choice != -1) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 4.");
            }
            choice = InputUtil.getInt("\nNhập lựa chọn của bạn (0-4): ");
        }
        
        boolean hasChanges = false;
        
        switch (choice) {
            case 1:
                hasChanges = updateClassName(classroom);
                break;
            case 2:
                hasChanges = updateSchoolYear(classroom);
                break;
            case 3:
                hasChanges = updateCourse(classroom);
                break;
            case 4:
                hasChanges = updateAll(classroom);
                break;
            case 0:
                System.out.println("\nĐã hủy bỏ cập nhật.");
                pause();
                return;
        }
        
        // Sử dụng service để cập nhật
        if (hasChanges) {
            boolean success = classroomService.updateClass(classroom);
            if (success) {
                System.out.println("\nCập nhật lớp học thành công!");
                System.out.println("\nTHÔNG TIN LỚP HỌC SAU KHI CẬP NHẬT:");
                System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│                           CHI TIẾT LỚP HỌC                              │");
                System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
                System.out.printf("│ Mã lớp: %-50s │%n", classroom.getClassId());
                System.out.printf("│ Tên lớp: %-50s │%n", classroom.getClassName());
                System.out.printf("│ Năm học: %-50s │%n", classroom.getSchoolYear());
                System.out.printf("│ Niên khóa: %-50s │%n", classroom.getCourse());
                System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
            } else {
                System.out.println("\nCập nhật lớp học thất bại!");
            }
        } else {
            System.out.println("\nKhông có thay đổi nào được thực hiện.");
        }
        
        pause();
    }

    private boolean updateClassName(Classroom classroom) {
        System.out.println("\nCẬP NHẬT TÊN LỚP:");
        System.out.println("Tên lớp hiện tại: " + classroom.getClassName());
        
        String newName = InputUtil.getString("Nhập tên lớp mới (Enter để giữ nguyên): ");
        if (!newName.trim().isEmpty()) {
            classroom.setClassName(newName.trim());
            System.out.println("Đã cập nhật tên lớp thành: " + newName.trim());
            return true;
        }
        return false;
    }

    private boolean updateSchoolYear(Classroom classroom) {
        System.out.println("\nCẬP NHẬT NĂM HỌC:");
        System.out.println("Năm học hiện tại: " + classroom.getSchoolYear());
        
        String newYear = null;
        while (newYear == null || (!newYear.trim().isEmpty() && !newYear.matches("^\\d{4}-\\d{4}$"))) {
            if (newYear != null && !newYear.trim().isEmpty() && !newYear.matches("^\\d{4}-\\d{4}$")) {
                System.out.println("Năm học sai định dạng! (VD: 2024-2025)");
            }
            newYear = InputUtil.getString("Nhập năm học mới (YYYY-YYYY, Enter để giữ nguyên): ");
            
            if (!newYear.trim().isEmpty() && newYear.matches("^\\d{4}-\\d{4}$")) {
                // Kiểm tra năm học hợp lệ
                String[] parts = newYear.split("-");
                int startYear = Integer.parseInt(parts[0]);
                int endYear = Integer.parseInt(parts[1]);
                if (startYear > endYear) {
                    System.out.println("Năm học không hợp lệ! Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc");
                    newYear = null;
                }
            }
        }
        
        if (!newYear.trim().isEmpty()) {
            classroom.setSchoolYear(newYear.trim());
            System.out.println("Đã cập nhật năm học thành: " + newYear.trim());
            return true;
        }
        return false;
    }

    private boolean updateCourse(Classroom classroom) {
        System.out.println("\nCẬP NHẬT NIÊN KHÓA:");
        System.out.println("Niên khóa hiện tại: " + classroom.getCourse());
        
        String newCourse = null;
        while (newCourse == null || (!newCourse.trim().isEmpty() && !newCourse.matches("^\\d{4}-\\d{4}$"))) {
            if (newCourse != null && !newCourse.trim().isEmpty() && !newCourse.matches("^\\d{4}-\\d{4}$")) {
                System.out.println("Niên khóa sai định dạng! (VD: 2020-2024)");
            }
            newCourse = InputUtil.getString("Nhập niên khóa mới (YYYY-YYYY, Enter để giữ nguyên): ");
            
            if (!newCourse.trim().isEmpty() && newCourse.matches("^\\d{4}-\\d{4}$")) {
                // Kiểm tra niên khóa hợp lệ
                String[] parts = newCourse.split("-");
                int startYear = Integer.parseInt(parts[0]);
                int endYear = Integer.parseInt(parts[1]);
                if (startYear > endYear) {
                    System.out.println("Niên khóa không hợp lệ! Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc");
                    newCourse = null;
                }
            }
        }
        
        if (!newCourse.trim().isEmpty()) {
            classroom.setCourse(newCourse.trim());
            System.out.println("Đã cập nhật niên khóa thành: " + newCourse.trim());
            return true;
        }
        return false;
    }

    private boolean updateAll(Classroom classroom) {
        System.out.println("\nCẬP NHẬT TẤT CẢ THÔNG TIN:");
        boolean hasChanges = false;
        
        hasChanges |= updateClassName(classroom);
        hasChanges |= updateSchoolYear(classroom);
        hasChanges |= updateCourse(classroom);
        
        return hasChanges;
    }
}