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
            System.out.println("   Không tìm thấy môn học với mã '" + subjectId + "'!");
            InputUtil.pressEnterToContinue();
            return;
        }

        Subject s = optionalSubject.get();
        System.out.println("   Tìm thấy môn học!");
        
        // Hiển thị menu chỉnh sửa theo số
        displayEditMenu(s);
    }
    
    private void displayEditMenu(Subject s) {
        boolean continueEditing = true;
        
        while (continueEditing) {
            clearScreen();
            System.out.println("┌──────────────────────────────────────────────┐");
            System.out.println("│          CHỈNH SỬA THÔNG TIN MÔN HỌC        │");
            System.out.println("└──────────────────────────────────────────────┘");
            
            System.out.println("\n=== THÔNG TIN HIỆN TẠI ===");
            System.out.println("Mã môn học: " + s.getId());
            System.out.println("Tên môn học: " + s.getSubjectName());
            System.out.println("Số tiết: " + s.getLessonCount());
            System.out.println("Hệ số: " + s.getConfficient());
            System.out.println("Loại môn: " + s.getSubjectType());
            System.out.println("Mô tả: " + (s.getDescription().isEmpty() ? "Không có" : s.getDescription()));
            System.out.println("Giáo viên phụ trách: " + String.join(", ", s.getTeachersInCharge()));
            System.out.println("Trạng thái: " + s.getStatus());
            
            System.out.println("\n┌──────────────────────────────────────────┐");
            System.out.println("│            MENU CHỈNH SỬA                 │");
            System.out.println("├──────────────────────────────────────────┤");
            System.out.println("│  1. Sửa Tên Môn Học                       │");
            System.out.println("│  2. Sửa Số Tiết Học                       │");
            System.out.println("│  3. Sửa Hệ Số                            │");
            System.out.println("│  4. Sửa Loại Môn Học                      │");
            System.out.println("│  5. Sửa Mô Tả                            │");
            System.out.println("│  6. Sửa Giáo Viên Phụ Trách              │");
            System.out.println("│  7. Sửa Trạng Thái                        │");
            System.out.println("│  8. Lưu Thay Đổi                         │");
            System.out.println("│  0. Quay Lại (Không Lưu)                 │");
            System.out.println("└──────────────────────────────────────────┘");
            
            System.out.println("\n┌──────────────────────────────────────────┐");
            System.out.println("│              HƯỚNG DẪN SỬ DỤNG             │");
            System.out.println("├──────────────────────────────────────────┤");
            System.out.println("│ Chọn số tương ứng với trường muốn sửa     │");
            System.out.println("│ Ví dụ: Nhập 1 để sửa tên môn học          │");
            System.out.println("│ Nhập 8 để lưu tất cả thay đổi            │");
            System.out.println("│ Nhập 0 để quay lại mà không lưu           │");
            System.out.println("└──────────────────────────────────────────┘");
            
            int choice = InputUtil.getInt("Nhập lựa chọn của bạn: ");
            
            switch (choice) {
                case 1:
                    editSubjectName(s);
                    break;
                case 2:
                    editLessonCount(s);
                    break;
                case 3:
                    editCoefficient(s);
                    break;
                case 4:
                    editSubjectType(s);
                    break;
                case 5:
                    editDescription(s);
                    break;
                case 6:
                    editTeachers(s);
                    break;
                case 7:
                    editStatus(s);
                    break;
                case 8:
                    saveChanges(s);
                    continueEditing = false;
                    break;
                case 0:
                    System.out.println("\nBạn đã chọn quay lại mà không lưu thay đổi.");
                    InputUtil.pressEnterToContinue();
                    continueEditing = false;
                    break;
                default:
                    System.out.println("\nLựa chọn không hợp lệ. Vui lòng chọn từ 0-8.");
                    InputUtil.pressEnterToContinue();
            }
        }
    }
    
    private void editSubjectName(Subject s) {
        System.out.println("\n=== SỬA TÊN MÔN HỌC ===");
        System.out.println("Tên hiện tại: " + s.getSubjectName());
        System.out.println("Lưu ý: Tên môn học không được trùng lặp với môn học khác");
        
        String newName = InputUtil.getString("Nhập tên mới (Enter để bỏ qua): ");
        if (!newName.isEmpty()) {
            if (SubjectService.getInstance().isSubjectNameExists(newName) && !newName.equals(s.getSubjectName())) {
                System.out.println("Tên môn học đã tồn tại! Giữ nguyên tên cũ.");
            } else {
                s.setSubjectName(newName);
                System.out.println("Đã cập nhật tên môn học thành công!");
            }
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void editLessonCount(Subject s) {
        System.out.println("\n=== SỬA SỐ TIẾT HỌC ===");
        System.out.println("Số tiết hiện tại: " + s.getLessonCount());
        System.out.println("Lưu ý: Nhập số nguyên dương (VD: 45, 60)");
        
        String newLessons = InputUtil.getString("Nhập số tiết mới (Enter để bỏ qua): ");
        if (!newLessons.isEmpty()) {
            try {
                int newLessonCount = Integer.parseInt(newLessons);
                if (newLessonCount > 0) {
                    s.setLessonCount(newLessonCount);
                    System.out.println("Đã cập nhật số tiết học thành công!");
                } else {
                    System.out.println("Số tiết phải lớn hơn 0! Giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu không hợp lệ! Giữ nguyên giá trị cũ.");
            }
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void editCoefficient(Subject s) {
        System.out.println("\n=== SỬA HỆ SỐ ===");
        System.out.println("Hệ số hiện tại: " + s.getConfficient());
        System.out.println("Lưu ý: Nhập số thập phân (VD: 1.0, 2.0)");
        
        String newCoeff = InputUtil.getString("Nhập hệ số mới (Enter để bỏ qua): ");
        if (!newCoeff.isEmpty()) {
            try {
                double newCoefficient = Double.parseDouble(newCoeff);
                if (newCoefficient > 0) {
                    s.setConfficient(newCoefficient);
                    System.out.println("Đã cập nhật hệ số thành công!");
                } else {
                    System.out.println("Hệ số phải lớn hơn 0! Giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu không hợp lệ! Giữ nguyên giá trị cũ.");
            }
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void editSubjectType(Subject s) {
        System.out.println("\n=== SỬA LOẠI MÔN HỌC ===");
        System.out.println("Loại hiện tại: " + s.getSubjectType());
        System.out.println("Lựa chọn:");
        System.out.println("  0: Bắt buộc (học sinh phải học)");
        System.out.println("  1: Tự chọn (học sinh có thể chọn)");
        
        while (true) {
            String typeInput = InputUtil.getString("Nhập lựa chọn (0/1/Enter để bỏ qua): ");
            if (typeInput.isEmpty()) break;
            if (typeInput.equals("0")) {
                s.setSubjectType("Bắt buộc");
                System.out.println("Đã cập nhật: Môn bắt buộc");
                break;
            } else if (typeInput.equals("1")) {
                s.setSubjectType("Tự chọn");
                System.out.println("Đã cập nhật: Môn tự chọn");
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Nhập 0 hoặc 1.");
            }
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void editDescription(Subject s) {
        System.out.println("\n=== SỬA MÔ TẢ ===");
        System.out.println("Mô tả hiện tại: " + (s.getDescription().isEmpty() ? "Không có" : s.getDescription()));
        System.out.println("Lưu ý: Có thể để trống");
        
        String newDesc = InputUtil.getString("Nhập mô tả mới (Enter để bỏ qua): ");
        if (!newDesc.isEmpty()) {
            s.setDescription(newDesc);
            System.out.println("Đã cập nhật mô tả thành công!");
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void editTeachers(Subject s) {
        System.out.println("\n=== SỬA GIÁO VIÊN PHỤ TRÁCH ===");
        System.out.println("Giáo viên hiện tại: " + String.join(", ", s.getTeachersInCharge()));
        System.out.println("Lưu ý: Nhập mã GV cách nhau bởi dấu phẩy (VD: GV001,GV002)");
        
        // Hiển thị danh sách giáo viên
        System.out.println("\nDANH SÁCH GIÁO VIÊN HIỆN CÓ:");
        teacherService.displayAllTeachers();
        
        String newTeachers = InputUtil.getString("Nhập danh sách giáo viên mới (Enter để bỏ qua): ");
        if (!newTeachers.isEmpty()) {
            List<String> teachers = Arrays.stream(newTeachers.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            s.setTeachersInCharge(new ArrayList<>(teachers));
            System.out.println("Đã cập nhật danh sách giáo viên thành công!");
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void editStatus(Subject s) {
        System.out.println("\n=== SỬA TRẠNG THÁI ===");
        System.out.println("Trạng thái hiện tại: " + s.getStatus());
        System.out.println("Lựa chọn:");
        System.out.println("  0: Đang dạy (môn học đang được giảng dạy)");
        System.out.println("  1: Ngừng (môn học tạm ngừng)");
        
        while (true) {
            String statusInput = InputUtil.getString("Nhập lựa chọn (0/1/Enter để bỏ qua): ");
            if (statusInput.isEmpty()) break;
            if (statusInput.equals("0")) {
                s.setStatus("Đang dạy");
                System.out.println("Đã cập nhật: Đang dạy");
                break;
            } else if (statusInput.equals("1")) {
                s.setStatus("Ngừng");
                System.out.println("Đã cập nhật: Ngừng");
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ! Nhập 0 hoặc 1.");
            }
        }
        InputUtil.pressEnterToContinue();
    }
    
    private void saveChanges(Subject s) {
        System.out.println("\n=== XÁC NHẬN LƯU THAY ĐỔI ===");
        System.out.println("Thông tin môn học sau khi chỉnh sửa:");
        System.out.println("Mã môn học: " + s.getId());
        System.out.println("Tên môn học: " + s.getSubjectName());
        System.out.println("Số tiết: " + s.getLessonCount());
        System.out.println("Hệ số: " + s.getConfficient());
        System.out.println("Loại môn: " + s.getSubjectType());
        System.out.println("Mô tả: " + (s.getDescription().isEmpty() ? "Không có" : s.getDescription()));
        System.out.println("Giáo viên phụ trách: " + String.join(", ", s.getTeachersInCharge()));
        System.out.println("Trạng thái: " + s.getStatus());
        
        if (subjectService.updateSubject(s)) {
            System.out.println("\nĐã cập nhật thông tin môn học thành công!");
        } else {
            System.out.println("\nCập nhật thất bại!");
        }
        InputUtil.pressEnterToContinue();
    }
}
