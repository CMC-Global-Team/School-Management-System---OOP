package Screen.Subject;


import Models.Subject;
import Screen.AbstractScreen;
import Services.SubjectService;
import Services.TeacherService;
import Utils.InputUtil;
import java.util.*;
import java.util.stream.Collectors;


public class EditSubjectScreen extends AbstractScreen {


    private final SubjectService subjectService;
    private final TeacherService teacherService;


    public EditSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
        this.teacherService = TeacherService.getInstance();
    }


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────┐");
        System.out.println("│          CHỈNH SỬA THÔNG TIN MÔN HỌC        │");
        System.out.println("└──────────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│         HƯỚNG DẪN CHỈNH SỬA MÔN HỌC      │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ FORMAT MÃ MÔN HỌC:                        │");
        System.out.println("│ - Bắt đầu bằng chữ cái (VD: TOAN, LY)    │");
        System.out.println("│ - Theo sau là số (VD: TOAN01, LY02)       │");
        System.out.println("│ - Không có khoảng trắng hoặc ký tự đặc biệt│");
        System.out.println("│ - Ví dụ hợp lệ: TOAN01, LY02, HOA03       │");
        System.out.println("│                                          │");
        System.out.println("│ QUY TRÌNH CHỈNH SỬA:                     │");
        System.out.println("│ 1. Nhập mã môn học cần chỉnh sửa          │");
        System.out.println("│ 2. Xem thông tin hiện tại                │");
        System.out.println("│ 3. Nhập giá trị mới cho từng trường       │");
        System.out.println("│    - Nhấn Enter để giữ nguyên             │");
        System.out.println("│    - Nhập giá trị mới để thay đổi        │");
        System.out.println("│                                          │");
        System.out.println("│ CÁC TRƯỜNG CÓ THỂ CHỈNH SỬA:             │");
        System.out.println("│ - Tên môn học: Không được trùng lặp      │");
        System.out.println("│ - Số tiết: Số nguyên dương (VD: 45, 60)  │");
        System.out.println("│ - Hệ số: Số thập phân (VD: 1.0, 2.0)     │");
        System.out.println("│ - Loại môn: 0=Bắt buộc, 1=Tự chọn        │");
        System.out.println("│ - Mô tả: Có thể để trống                 │");
        System.out.println("│ - Giáo viên: Mã GV cách nhau bởi dấu ,   │");
        System.out.println("│ - Trạng thái: 0=Đang dạy, 1=Ngừng        │");
        System.out.println("│                                          │");
        System.out.println("│ Lưu ý: Dữ liệu không hợp lệ sẽ giữ nguyên │");
        System.out.println("└──────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        System.out.println("\n=== CHỈNH SỬA THÔNG TIN MÔN HỌC ===");
        
        // Nhập mã môn học cần chỉnh sửa
        System.out.println("\n1. MÃ MÔN HỌC CẦN CHỈNH SỬA:");
        System.out.println("   - Format: Chữ cái + số (VD: TOAN01, LY02)");
        System.out.println("   - Phải tồn tại trong hệ thống");
        String subjectId = InputUtil.getNonEmptyString("   Nhập mã môn học: ");
        Optional<Subject> optionalSubject = subjectService.findById(subjectId);

        if (optionalSubject.isEmpty()) {
            System.out.println("   ❌ Không tìm thấy môn học với mã '" + subjectId + "'!");
            InputUtil.pressEnterToContinue();
            return;
        }

        Subject s = optionalSubject.get();
        System.out.println("   ✅ Tìm thấy môn học!");
        System.out.println("\n=== THÔNG TIN HIỆN TẠI ===");
        System.out.println("Tên môn học: " + s.getSubjectName());
        System.out.println("Số tiết: " + s.getLessonCount());
        System.out.println("Hệ số: " + s.getConfficient());
        System.out.println("Loại môn: " + s.getSubjectType());
        System.out.println("Mô tả: " + s.getDescription());
        System.out.println("Giáo viên phụ trách: " + String.join(", ", s.getTeachersInCharge()));
        System.out.println("Trạng thái: " + s.getStatus());

        System.out.println("\n=== NHẬP THÔNG TIN MỚI ===");
        System.out.println("(Nhấn Enter để giữ nguyên giá trị hiện tại)");

        // Cập nhật từng trường
        System.out.println("\n2. TÊN MÔN HỌC:");
        System.out.println("   - Hiện tại: " + s.getSubjectName());
        System.out.println("   - Không được trùng lặp với môn học khác");
        String nameInput = InputUtil.getString("   Nhập tên môn học mới: ");
        if (!nameInput.isEmpty()) {
            if (SubjectService.getInstance().isSubjectNameExists(nameInput) && !nameInput.equals(s.getSubjectName())) {
                System.out.println("   ❌ Tên môn học đã tồn tại! Giữ nguyên tên cũ.");
            } else {
                s.setSubjectName(nameInput);
                System.out.println("   ✅ Đã cập nhật tên môn học!");
            }
        }

        System.out.println("\n3. SỐ TIẾT HỌC:");
        System.out.println("   - Hiện tại: " + s.getLessonCount());
        System.out.println("   - Nhập số nguyên dương (VD: 45, 60)");
        String lessonsInput = InputUtil.getString("   Nhập số tiết mới: ");
        if (!lessonsInput.isEmpty()) {
            try {
                int newLessonCount = Integer.parseInt(lessonsInput);
                if (newLessonCount > 0) {
                    s.setLessonCount(newLessonCount);
                    System.out.println("   ✅ Đã cập nhật số tiết học!");
                } else {
                    System.out.println("   ❌ Số tiết phải lớn hơn 0! Giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("   ❌ Dữ liệu không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }

        System.out.println("\n4. HỆ SỐ:");
        System.out.println("   - Hiện tại: " + s.getConfficient());
        System.out.println("   - Nhập số thập phân (VD: 1.0, 2.0)");
        String coeffInput = InputUtil.getString("   Nhập hệ số mới: ");
        if (!coeffInput.isEmpty()) {
            try {
                double newCoefficient = Double.parseDouble(coeffInput);
                if (newCoefficient > 0) {
                    s.setConfficient(newCoefficient);
                    System.out.println("   ✅ Đã cập nhật hệ số!");
                } else {
                    System.out.println("   ❌ Hệ số phải lớn hơn 0! Giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("   ❌ Dữ liệu không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }

        System.out.println("\n5. LOẠI MÔN HỌC:");
        System.out.println("   - Hiện tại: " + s.getSubjectType());
        System.out.println("   - 0: Bắt buộc, 1: Tự chọn");
        while (true) {
            String typeInput = InputUtil.getString("   Nhập loại môn mới (0/1/Enter để giữ nguyên): ");
            if (typeInput.isEmpty()) break; // không thay đổi
            if (typeInput.equals("0")) {
                s.setSubjectType("Bắt buộc");
                System.out.println("   ✅ Đã cập nhật: Môn bắt buộc");
                break;
            } else if (typeInput.equals("1")) {
                s.setSubjectType("Tự chọn");
                System.out.println("   ✅ Đã cập nhật: Môn tự chọn");
                break;
            } else {
                System.out.println("   ❌ Lựa chọn không hợp lệ! Nhập 0 hoặc 1 hoặc Enter để bỏ qua.");
            }
        }

        System.out.println("\n6. MÔ TẢ:");
        System.out.println("   - Hiện tại: " + (s.getDescription().isEmpty() ? "Không có" : s.getDescription()));
        System.out.println("   - Có thể để trống");
        String descInput = InputUtil.getString("   Nhập mô tả mới: ");
        if (!descInput.isEmpty()) {
            s.setDescription(descInput);
            System.out.println("   ✅ Đã cập nhật mô tả!");
        }

        System.out.println("\n7. GIÁO VIÊN PHỤ TRÁCH:");
        System.out.println("   - Hiện tại: " + String.join(", ", s.getTeachersInCharge()));
        System.out.println("   - Nhập mã GV cách nhau bởi dấu phẩy");
        System.out.println("   - Ví dụ: GV001,GV002");
        
        // Hiển thị danh sách giáo viên
        System.out.println("\n   DANH SÁCH GIÁO VIÊN HIỆN CÓ:");
        teacherService.displayAllTeachers();
        
        String teacherInput = InputUtil.getString("   Nhập danh sách giáo viên mới: ");
        if (!teacherInput.isEmpty()) {
            List<String> teachers = Arrays.stream(teacherInput.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            s.setTeachersInCharge(new ArrayList<>(teachers)); // đảm bảo mutable list
            System.out.println("   ✅ Đã cập nhật danh sách giáo viên!");
        }

        System.out.println("\n8. TRẠNG THÁI:");
        System.out.println("   - Hiện tại: " + s.getStatus());
        System.out.println("   - 0: Đang dạy, 1: Ngừng");
        while (true) {
            String statusInput = InputUtil.getString("   Nhập trạng thái mới (0/1/Enter để giữ nguyên): ");
            if (statusInput.isEmpty()) break; // không thay đổi
            if (statusInput.equals("0")) {
                s.setStatus("Đang dạy");
                System.out.println("   ✅ Đã cập nhật: Đang dạy");
                break;
            } else if (statusInput.equals("1")) {
                s.setStatus("Ngừng");
                System.out.println("   ✅ Đã cập nhật: Ngừng");
                break;
            } else {
                System.out.println("   ❌ Lựa chọn không hợp lệ! Nhập 0 hoặc 1 hoặc Enter để bỏ qua.");
            }
        }

        // Cập nhật qua service
        System.out.println("\n=== XÁC NHẬN CHỈNH SỬA ===");
        if (subjectService.updateSubject(s)) {
            System.out.println("🎉 Đã cập nhật thông tin môn học thành công!");
            System.out.println("Thông tin môn học sau khi chỉnh sửa:");
            subjectService.findById(subjectId).ifPresent(System.out::println);
        } else {
            System.out.println("❌ Cập nhật thất bại!");
        }

        InputUtil.pressEnterToContinue();
    }
}
