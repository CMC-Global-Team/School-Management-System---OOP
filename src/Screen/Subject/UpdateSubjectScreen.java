package Screen.Subject;


import Models.Subject;
import Screen.AbstractScreen;
import Services.SubjectService;
import Services.TeacherService;
import java.util.*;


public class UpdateSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;
    private final TeacherService teacherService;


    public UpdateSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
        this.teacherService = TeacherService.getInstance();
    }


    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           CẬP NHẬT MÔN HỌC               │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│            HƯỚNG DẪN CẬP NHẬT             │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ FORMAT MÃ MÔN HỌC:                        │");
        System.out.println("│ - Bắt đầu bằng chữ cái (VD: TOAN, LY)    │");
        System.out.println("│ - Theo sau là số (VD: TOAN01, LY02)       │");
        System.out.println("│ - Không có khoảng trắng hoặc ký tự đặc biệt│");
        System.out.println("│ - Ví dụ hợp lệ: TOAN01, LY02, HOA03       │");
        System.out.println("│                                          │");
        System.out.println("│ QUY TRÌNH CẬP NHẬT:                       │");
        System.out.println("│ 1. Nhập mã môn học cần cập nhật           │");
        System.out.println("│ 2. Xem thông tin hiện tại                 │");
        System.out.println("│ 3. Nhập giá trị mới cho từng trường       │");
        System.out.println("│    - Nhấn Enter để giữ nguyên             │");
        System.out.println("│    - Nhập giá trị mới để thay đổi         │");
        System.out.println("│                                          │");
        System.out.println("│ CÁC TRƯỜNG CÓ THỂ CẬP NHẬT:               │");
        System.out.println("│ - Tên môn học: Không được trùng lặp       │");
        System.out.println("│ - Số tiết: Số nguyên dương (VD: 45, 60)    │");
        System.out.println("│ - Hệ số: Số thập phân (VD: 1.0, 2.0)      │");
        System.out.println("│ - Loại môn: Bắt buộc hoặc Tự chọn         │");
        System.out.println("│ - Mô tả: Có thể để trống                   │");
        System.out.println("│ - Giáo viên: Mã GV cách nhau bởi dấu ,   │");
        System.out.println("│ - Trạng thái: Đang dạy hoặc Ngừng         │");
        System.out.println("└──────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        System.out.println("\n=== CẬP NHẬT THÔNG TIN MÔN HỌC ===");
        
        // Nhập mã môn học cần cập nhật
        System.out.println("\n1. MÃ MÔN HỌC CẦN CẬP NHẬT:");
        System.out.println("   - Format: Chữ cái + số (VD: TOAN01, LY02)");
        System.out.println("   - Phải tồn tại trong hệ thống");
        String id = input("   Nhập mã môn học: ");
        Optional<Subject> opt = subjectService.findById(id);

        if (opt.isEmpty()) {
            System.out.println("   ❌ Không tìm thấy môn học với mã: " + id);
            pause();
            return;
        }

        Subject s = opt.get();
        System.out.println("   ✅ Tìm thấy môn học!");
        System.out.println("\n=== THÔNG TIN HIỆN TẠI ===");
        System.out.println(s);

        System.out.println("\n=== NHẬP THÔNG TIN MỚI ===");
        System.out.println("(Nhấn Enter để giữ nguyên giá trị hiện tại)");

        // Cập nhật tên môn học
        System.out.println("\n2. TÊN MÔN HỌC:");
        System.out.println("   - Hiện tại: " + s.getSubjectName());
        System.out.println("   - Không được trùng lặp với môn học khác");
        String name = input("   Nhập tên mới: ");
        if (!name.isEmpty()) {
            if (SubjectService.getInstance().isSubjectNameExists(name) && !name.equals(s.getSubjectName())) {
                System.out.println("   ❌ Tên môn học đã tồn tại! Giữ nguyên tên cũ.");
            } else {
                s.setSubjectName(name);
                System.out.println("   ✅ Đã cập nhật tên môn học!");
            }
        }

        // Cập nhật số tiết
        System.out.println("\n3. SỐ TIẾT HỌC:");
        System.out.println("   - Hiện tại: " + s.getLessonCount());
        System.out.println("   - Nhập số nguyên dương (VD: 45, 60)");
        String lessonStr = input("   Nhập số tiết mới: ");
        if (!lessonStr.isEmpty()) {
            try {
                int newLessonCount = Integer.parseInt(lessonStr);
                if (newLessonCount > 0) {
                    s.setLessonCount(newLessonCount);
                    System.out.println("   ✅ Đã cập nhật số tiết học!");
                } else {
                    System.out.println("   ❌ Số tiết phải lớn hơn 0! Giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("   ❌ Dữ liệu không hợp lệ! Giữ nguyên giá trị cũ.");
            }
        }

        // Cập nhật hệ số
        System.out.println("\n4. HỆ SỐ:");
        System.out.println("   - Hiện tại: " + s.getConfficient());
        System.out.println("   - Nhập số thập phân (VD: 1.0, 2.0)");
        String coefStr = input("   Nhập hệ số mới: ");
        if (!coefStr.isEmpty()) {
            try {
                double newCoefficient = Double.parseDouble(coefStr);
                if (newCoefficient > 0) {
                    s.setConfficient(newCoefficient);
                    System.out.println("   ✅ Đã cập nhật hệ số!");
                } else {
                    System.out.println("   ❌ Hệ số phải lớn hơn 0! Giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("   ❌ Dữ liệu không hợp lệ! Giữ nguyên giá trị cũ.");
            }
        }

        // Cập nhật loại môn
        System.out.println("\n5. LOẠI MÔN HỌC:");
        System.out.println("   - Hiện tại: " + s.getSubjectType());
        System.out.println("   - Nhập: Bắt buộc hoặc Tự chọn");
        String type = input("   Nhập loại môn mới: ");
        if (!type.isEmpty()) {
            if (type.equals("Bắt buộc") || type.equals("Tự chọn")) {
                s.setSubjectType(type);
                System.out.println("   ✅ Đã cập nhật loại môn học!");
            } else {
                System.out.println("   ❌ Loại môn không hợp lệ! Giữ nguyên giá trị cũ.");
            }
        }

        // Cập nhật mô tả
        System.out.println("\n6. MÔ TẢ:");
        System.out.println("   - Hiện tại: " + (s.getDescription().isEmpty() ? "Không có" : s.getDescription()));
        System.out.println("   - Có thể để trống");
        String desc = input("   Nhập mô tả mới: ");
        if (!desc.isEmpty()) {
            s.setDescription(desc);
            System.out.println("   ✅ Đã cập nhật mô tả!");
        }

        // Cập nhật giáo viên phụ trách
        System.out.println("\n7. GIÁO VIÊN PHỤ TRÁCH:");
        System.out.println("   - Hiện tại: " + String.join(", ", s.getTeachersInCharge()));
        System.out.println("   - Nhập mã GV cách nhau bởi dấu phẩy");
        System.out.println("   - Ví dụ: GV001,GV002");
        
        // Hiển thị danh sách giáo viên
        System.out.println("\n   DANH SÁCH GIÁO VIÊN HIỆN CÓ:");
        teacherService.displayAllTeachers();
        
        String teacherInput = input("   Nhập danh sách giáo viên mới: ");
        if (!teacherInput.isEmpty()) {
            String[] teacherIDs = teacherInput.split("\\s*,\\s*");
            s.setTeachersInCharge(Arrays.asList(teacherIDs));
            System.out.println("   ✅ Đã cập nhật danh sách giáo viên!");
        }

        // Cập nhật trạng thái
        System.out.println("\n8. TRẠNG THÁI:");
        System.out.println("   - Hiện tại: " + s.getStatus());
        System.out.println("   - Nhập: Đang dạy hoặc Ngừng");
        String status = input("   Nhập trạng thái mới: ");
        if (!status.isEmpty()) {
            if (status.equals("Đang dạy") || status.equals("Ngừng")) {
                s.setStatus(status);
                System.out.println("   ✅ Đã cập nhật trạng thái!");
            } else {
                System.out.println("   ❌ Trạng thái không hợp lệ! Giữ nguyên giá trị cũ.");
            }
        }

        // Xác nhận và lưu
        System.out.println("\n=== XÁC NHẬN CẬP NHẬT ===");
        if (subjectService.updateSubject(s)) {
            System.out.println("🎉 Cập nhật thành công!");
            System.out.println("Thông tin môn học sau khi cập nhật:");
            subjectService.findById(id).ifPresent(System.out::println);
        } else {
            System.out.println("❌ Có lỗi xảy ra khi cập nhật!");
        }

        pause();
    }
}
