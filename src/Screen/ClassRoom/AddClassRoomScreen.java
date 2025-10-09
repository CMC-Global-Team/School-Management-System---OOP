package Screen.ClassRoom;

import Screen.AbstractScreen;
import Services.ClassroomService;
import Utils.InputUtil;

public class AddClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public AddClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            Thêm Lớp Học Mới               │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã lớp: Lớpxxxx (VD: Lớp001, Lớp002...) - Phải duy nhất trong hệ thống │");
        System.out.println("│ Tên lớp: Tên lớp học (VD: Lớp A, Lớp B...)                              │");
        System.out.println("│ Năm học: YYYY-YYYY (VD: 2024-2025) - Năm học hiện tại                 │");
        System.out.println("│ Niên khóa: YYYY-YYYY (VD: 2020-2024) - Khóa học của học sinh           │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý:                                                                   │");
        System.out.println("│ - Mã lớp phải duy nhất, không được trùng với lớp khác                   │");
        System.out.println("│ - Tên lớp nên mô tả rõ ràng (VD: Lớp A, Lớp B, Lớp C...)                │");
        System.out.println("│ - Năm học là năm học hiện tại của lớp                                   │");
        System.out.println("│ - Niên khóa là khóa học của học sinh trong lớp                          │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    THÊM LỚP HỌC MỚI");
        System.out.println("=".repeat(50));
        
        // Kiểm tra có dữ liệu không
        if (classroomService.getTotalClasses() > 0) {
            System.out.println("\nDANH SÁCH LỚP HỌC HIỆN TẠI:");
            System.out.println("Mã lớp    | Tên lớp           | Năm học      | Niên khóa");
            System.out.println("----------|-------------------|--------------|----------");
            
            classroomService.getAllClasses().forEach(classroom -> {
                System.out.printf("%-9s | %-17s | %-12s | %-8s%n", 
                    classroom.getClassId(), classroom.getClassName(), 
                    classroom.getSchoolYear(), classroom.getCourse());
            });
        }
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("NHẬP THÔNG TIN LỚP HỌC MỚI");
        System.out.println("-".repeat(50));
        
        // Nhập mã lớp với validation
        String classId = null;
        while (classId == null || classId.trim().isEmpty()) {
            if (classId != null && classId.trim().isEmpty()) {
                System.out.println("Mã lớp không được để trống!");
            }
            classId = InputUtil.getNonEmptyString("Mã lớp (VD: Lớp001): ");
        }
        
        // Kiểm tra mã lớp đã tồn tại chưa
        while (classroomService.isClassIdExists(classId)) {
            System.out.println("Mã lớp '" + classId + "' đã tồn tại trong hệ thống!");
            classId = InputUtil.getNonEmptyString("Nhập mã lớp khác (VD: Lớp002): ");
        }
        
        // Nhập tên lớp với validation
        String className = null;
        while (className == null || className.trim().isEmpty()) {
            if (className != null && className.trim().isEmpty()) {
                System.out.println("Tên lớp không được để trống!");
            }
            className = InputUtil.getNonEmptyString("Tên lớp (VD: Lớp A): ");
        }
        
        // Nhập năm học với validation
        String schoolYear = null;
        while (schoolYear == null || schoolYear.trim().isEmpty() || !schoolYear.matches("^\\d{4}-\\d{4}$")) {
            if (schoolYear != null && !schoolYear.trim().isEmpty() && !schoolYear.matches("^\\d{4}-\\d{4}$")) {
                System.out.println("Năm học sai định dạng! (VD: 2024-2025)");
            }
            schoolYear = InputUtil.getNonEmptyString("Năm học (YYYY-YYYY): ");
        }
        
        // Kiểm tra năm học hợp lệ
        String[] parts = schoolYear.split("-");
        int startYear = Integer.parseInt(parts[0]);
        int endYear = Integer.parseInt(parts[1]);
        while (startYear > endYear) {
            System.out.println("Năm học không hợp lệ! Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc");
            schoolYear = InputUtil.getNonEmptyString("Nhập lại năm học (YYYY-YYYY): ");
            parts = schoolYear.split("-");
            startYear = Integer.parseInt(parts[0]);
            endYear = Integer.parseInt(parts[1]);
        }
        
        // Nhập niên khóa với validation
        String course = null;
        while (course == null || course.trim().isEmpty() || !course.matches("^\\d{4}-\\d{4}$")) {
            if (course != null && !course.trim().isEmpty() && !course.matches("^\\d{4}-\\d{4}$")) {
                System.out.println("Niên khóa sai định dạng! (VD: 2020-2024)");
            }
            course = InputUtil.getNonEmptyString("Niên khóa (YYYY-YYYY): ");
        }
        
        // Kiểm tra niên khóa hợp lệ
        String[] courseParts = course.split("-");
        int courseStartYear = Integer.parseInt(courseParts[0]);
        int courseEndYear = Integer.parseInt(courseParts[1]);
        while (courseStartYear > courseEndYear) {
            System.out.println("Niên khóa không hợp lệ! Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc");
            course = InputUtil.getNonEmptyString("Nhập lại niên khóa (YYYY-YYYY): ");
            courseParts = course.split("-");
            courseStartYear = Integer.parseInt(courseParts[0]);
            courseEndYear = Integer.parseInt(courseParts[1]);
        }
        
        // Sử dụng service để thêm lớp học
        boolean success = classroomService.addClass(classId, className, schoolYear, course);
        
        if (success) {
            System.out.println("\nThêm lớp học thành công!");
            System.out.println("\nTHÔNG TIN LỚP HỌC VỪA THÊM:");
            System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                           CHI TIẾT LỚP HỌC                              │");
            System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
            System.out.printf("│ Mã lớp: %-50s │%n", classId);
            System.out.printf("│ Tên lớp: %-50s │%n", className);
            System.out.printf("│ Năm học: %-50s │%n", schoolYear);
            System.out.printf("│ Niên khóa: %-50s │%n", course);
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        } else {
            System.out.println("\nThêm lớp học thất bại!");
        }
        
        pause();
    }
}