package Screen.Subject;

import Screen.AbstractScreen;
import Services.SubjectService;
import Services.TeacherService;
import Utils.InputUtil;
import java.util.Arrays;
import java.util.List;

public class AddSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public AddSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
        this.teacherService = TeacherService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           THÊM MÔN HỌC MỚI               │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│              HƯỚNG DẪN THÊM MÔN HỌC        │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ FORMAT MÃ MÔN HỌC:                        │");
        System.out.println("│ - Bắt đầu bằng chữ cái (VD: TOAN, LY)    │");
        System.out.println("│ - Theo sau là số (VD: TOAN01, LY02)       │");
        System.out.println("│ - Không có khoảng trắng hoặc ký tự đặc biệt│");
        System.out.println("│ - Ví dụ hợp lệ: TOAN01, LY02, HOA03       │");
        System.out.println("│                                          │");
        System.out.println("│ CÁC TRƯỜNG THÔNG TIN:                    │");
        System.out.println("│ - Mã môn học: Duy nhất trong hệ thống    │");
        System.out.println("│ - Tên môn học: Không được trùng lặp       │");
        System.out.println("│ - Số tiết: Số nguyên dương (VD: 45, 60)   │");
        System.out.println("│ - Hệ số: Số thập phân (VD: 1.0, 2.0)      │");
        System.out.println("│ - Loại môn: 0=Bắt buộc, 1=Tự chọn        │");
        System.out.println("│ - Mô tả: Có thể để trống                  │");
        System.out.println("│ - Giáo viên: Mã GV cách nhau bởi dấu ,   │");
        System.out.println("│ - Trạng thái: 0=Đang dạy, 1=Ngừng        │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n=== NHẬP THÔNG TIN MÔN HỌC ===");
        
        // Nhập mã môn học
        String id;
        while(true){
            System.out.println("\n1. MÃ MÔN HỌC:");
            System.out.println("   - Format: Chữ cái + số (VD: TOAN01, LY02)");
            System.out.println("   - Phải duy nhất trong hệ thống");
            id = InputUtil.getNonEmptyString("   Nhập mã môn học: ");
            if(SubjectService.getInstance().isSubjectIdExists(id)){
                System.out.println("   ❌ Mã môn học " + id + " đã tồn tại! Vui lòng nhập mã khác.");
            }
            else{
                System.out.println("   ✅ Mã môn học hợp lệ!");
                break;
            }
        }
        
        // Nhập tên môn học
        String name;
        while (true) {
            System.out.println("\n2. TÊN MÔN HỌC:");
            System.out.println("   - Không được trùng lặp với môn học khác");
            System.out.println("   - Ví dụ: Toán học, Vật lý, Hóa học");
            name = InputUtil.getNonEmptyString("   Nhập tên môn học: ");
            if (SubjectService.getInstance().isSubjectNameExists(name)) {
                System.out.println("   ❌ Tên môn học \"" + name + "\" đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                System.out.println("   ✅ Tên môn học hợp lệ!");
                break;
            }
        }

        // Nhập số tiết học
        System.out.println("\n3. SỐ TIẾT HỌC:");
        System.out.println("   - Nhập số nguyên dương (VD: 45, 60, 90)");
        int lessonCount = InputUtil.getInt("   Nhập số tiết học: ");
        
        // Nhập hệ số
        System.out.println("\n4. HỆ SỐ:");
        System.out.println("   - Nhập số thập phân (VD: 1.0, 2.0, 1.5)");
        double confficient = InputUtil.getDouble("   Nhập hệ số: ");

        // Chọn loại môn
        int typeChoice;
        String type = "";
        while (true) {
            System.out.println("\n5. LOẠI MÔN HỌC:");
            System.out.println("   - 0: Bắt buộc (học sinh phải học)");
            System.out.println("   - 1: Tự chọn (học sinh có thể chọn)");
            typeChoice = InputUtil.getInt("   Chọn loại môn (0 hoặc 1): ");
            if(typeChoice == 0) {
                type = "Bắt buộc";
                System.out.println("   ✅ Đã chọn: Môn bắt buộc");
                break;
            } else if(typeChoice == 1) {
                type = "Tự chọn";
                System.out.println("   ✅ Đã chọn: Môn tự chọn");
                break;
            } else {
                System.out.println("   ❌ Lựa chọn không hợp lệ! Vui lòng nhập 0 hoặc 1.");
            }
        }
        
        // Nhập mô tả
        System.out.println("\n6. MÔ TẢ MÔN HỌC:");
        System.out.println("   - Có thể để trống nếu không cần");
        System.out.println("   - Ví dụ: Môn học cơ bản về toán học");
        String description = input("   Nhập mô tả (Enter để bỏ qua): ");
        
        // Nhập giáo viên phụ trách
        System.out.println("\n7. GIÁO VIÊN PHỤ TRÁCH:");
        System.out.println("   - Nhập mã giáo viên cách nhau bởi dấu phẩy");
        System.out.println("   - Ví dụ: GV001,GV002 hoặc GV001");
        
        // Hiển thị danh sách giáo viên
        System.out.println("\n   DANH SÁCH GIÁO VIÊN HIỆN CÓ:");
        teacherService.displayAllTeachers();
        
        String teacherInput = input("   Nhập mã giáo viên: ");
        List<String> teachersList = Arrays.asList(teacherInput.split(","));

        // Chọn trạng thái
        int statusChoice;
        String status = "";
        while (true) {
            System.out.println("\n8. TRẠNG THÁI MÔN HỌC:");
            System.out.println("   - 0: Đang dạy (môn học đang được giảng dạy)");
            System.out.println("   - 1: Ngừng (môn học tạm ngừng)");
            statusChoice = InputUtil.getInt("   Chọn trạng thái (0 hoặc 1): ");
            if(statusChoice == 0) {
                status = "Đang dạy";
                System.out.println("   ✅ Đã chọn: Đang dạy");
                break;
            } else if(statusChoice == 1) {
                status = "Ngừng";
                System.out.println("   ✅ Đã chọn: Ngừng");
                break;
            } else {
                System.out.println("   ❌ Lựa chọn không hợp lệ! Vui lòng nhập 0 hoặc 1.");
            }
        }

        // Xác nhận và lưu
        System.out.println("\n=== XÁC NHẬN THÔNG TIN ===");
        System.out.println("Mã môn học: " + id);
        System.out.println("Tên môn học: " + name);
        System.out.println("Số tiết: " + lessonCount);
        System.out.println("Hệ số: " + confficient);
        System.out.println("Loại môn: " + type);
        System.out.println("Mô tả: " + (description.isEmpty() ? "Không có" : description));
        System.out.println("Giáo viên: " + String.join(", ", teachersList));
        System.out.println("Trạng thái: " + status);

        if (subjectService.addSubject(id, name, lessonCount, confficient, type, description, teachersList, status)) {
            System.out.println("\n🎉 Thêm môn học thành công!");
            System.out.println("Thông tin môn học đã được lưu:");
            subjectService.findById(id).ifPresent(System.out::println);
        } else {
            System.out.println("\n❌ Có lỗi xảy ra khi thêm môn học!");
        }

        pause();
    }
}
