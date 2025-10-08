package Screen.Teacher;

import Models.Classroom;
import Models.Teacher;
import Screen.AbstractScreen;
import Services.FileManager;
import Services.TeacherService;
import Utils.FileUtil;
import Utils.InputUtil;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Màn hình cập nhật thông tin giáo viên
 * - Hiển thị giao diện nhập
 * - Gọi TeacherService để xử lý logic cập nhật
 */
public class UpdateTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService;

    public UpdateTeacherScreen() {
        this.teacherService = TeacherService.getInstance(); // sử dụng Singleton
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       CẬP NHẬT THÔNG TIN GIÁO VIÊN      │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Nhập mã giáo viên cần cập nhật (VD: GV0001)                         │");
        System.out.println("│ 2. Xem thông tin hiện tại của giáo viên                                │");
        System.out.println("│ 3. Nhập thông tin mới (để trống nếu không thay đổi)                    │");
        System.out.println("│ 4. Hệ thống sẽ cập nhật các thông tin đã thay đổi                      │");
        System.out.println("│                                                                         │");
        System.out.println("│ CÁC TRƯỜNG CÓ THỂ CẬP NHẬT:                                             │");
        System.out.println("│ - Họ và tên, Giới tính, Ngày sinh, Địa chỉ                             │");
        System.out.println("│ - Số điện thoại, Email, Môn giảng dạy, Lớp chủ nhiệm                  │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String id = InputUtil.getNonEmptyString("Nhập mã giáo viên cần cập nhật: ");

        // Tìm giáo viên theo ID trước
        Teacher existing = teacherService.findById(id).orElse(null);

        if (existing == null) {
            System.out.println("Không tìm thấy giáo viên có mã '" + id + "'.");
            pause();
            return;
        }

        System.out.println("\nThông tin hiện tại:");
        System.out.println(existing);

        System.out.println("\nNhập thông tin mới (Enter để giữ nguyên):");

        String name = InputUtil.getString("Tên mới (" + existing.getName() + "): ");
        if (!name.isEmpty()) existing.setName(name);

        String subject = InputUtil.getString("Môn dạy (" + existing.getTeacherSubjects() + "): ");
        if (!subject.isEmpty()) existing.setTeacherSubjects(Collections.singletonList(subject));

        // --- Cập nhật trình độ ---
        System.out.println("\nChọn trình độ mới (Enter để giữ nguyên):");
        System.out.println("0 - Cử nhân");
        System.out.println("1 - Thạc sĩ");
        System.out.println("2 - Tiến sĩ");
        String degreeInput = InputUtil.getString("Trình độ hiện tại (" + existing.getTeacherDegree() + "): ");
        if (!degreeInput.isEmpty()) {
            try {
                int degreeCode = Integer.parseInt(degreeInput);
                switch (degreeCode) {
                    case 0 -> existing.setTeacherDegree("Cử nhân");
                    case 1 -> existing.setTeacherDegree("Thạc sĩ");
                    case 2 -> existing.setTeacherDegree("Tiến sĩ");
                    default -> System.out.println("Mã trình độ không hợp lệ, giữ nguyên giá trị cũ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nhập không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }

        String expStr = InputUtil.getString("Kinh nghiệm (" + existing.getTeacherExperience() + "): ");
        if (!expStr.isEmpty()) {
            try {
                existing.setTeacherExperience(Integer.parseInt(expStr));
            } catch (NumberFormatException e) {
                System.out.println("Kinh nghiệm không hợp lệ, giữ nguyên giá trị cũ.");
            }
        }

        String email = InputUtil.getString("Email (" + existing.getTeacherEmail() + "): ");
        if (!email.isEmpty()) existing.setTeacherEmail(email);

        String phone = InputUtil.getString("SĐT (" + existing.getTeacherPhone() + "): ");
        if (!phone.isEmpty()) existing.setTeacherPhone(phone);

        // --- Cập nhật lớp chủ nhiệm với danh sách lớp ---
        System.out.println("\nCẬP NHẬT LỚP CHỦ NHIỆM:");
        System.out.println("Lớp hiện tại: " + (existing.getTeacherHomeroom() == null || existing.getTeacherHomeroom().isEmpty() ? "Chưa có" : existing.getTeacherHomeroom()));
        
        System.out.println("\nDANH SÁCH LỚP CÓ SẴN:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           DANH SÁCH LỚP HỌC                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-8s %-20s %-15s %-20s │%n", "Mã lớp", "Tên lớp", "Năm học", "Niên khóa");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        List<Classroom> classrooms = new ArrayList<>();
        try {
            if (FileUtil.fileExists(FileManager.CLASSROOM_FILE)) {
                List<String> classroomLines = FileUtil.readLines(FileManager.CLASSROOM_FILE);
                for (String line : classroomLines) {
                    Classroom c = Classroom.fromString(line);
                    if (c != null) classrooms.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc danh sách lớp: " + e.getMessage());
        }

        if (!classrooms.isEmpty()) {
            for (int i = 0; i < classrooms.size(); i++) {
                Classroom c = classrooms.get(i);
                System.out.printf("│ %-8s %-20s %-15s %-20s │%n", 
                    c.getClassId(), c.getClassName(), c.getSchoolYear(), c.getCourse());
            }
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
            
            System.out.println("\nBạn có thể:");
            System.out.println("1. Nhập mã lớp từ danh sách trên (VD: L01A1)");
            System.out.println("2. Nhập tên lớp mới nếu chưa có trong danh sách");
            System.out.println("3. Để trống nếu không thay đổi");
        } else {
            System.out.println("│ Chưa có lớp học nào trong hệ thống!                                    │");
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
            System.out.println("Bạn có thể nhập tên lớp mới hoặc để trống.");
        }
        
        String homeroom = InputUtil.getString("\nLớp chủ nhiệm mới (Enter nếu không thay đổi): ");
        if (!homeroom.isEmpty()) existing.setTeacherHomeroom(homeroom);



        // Gọi service để cập nhật
        boolean success = teacherService.updateTeacher(existing);

        if (success) {
            System.out.println("Cập nhật giáo viên thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi cập nhật giáo viên!");
        }

        pause();
    }
}