package Screen.Teacher;

import Models.Classroom;
import Models.Subject;
import Screen.AbstractScreen;
import Services.FileManager;
import Services.SubjectService;
import Services.TeacherService;
import Utils.FileUtil;
import Utils.InputUtil;
import java.util.*;

public class AddTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService = TeacherService.getInstance();
    private final SubjectService subjectService = SubjectService.getInstance();

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           THÊM GIÁO VIÊN MỚI             │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Mã giáo viên: GVxxxx (VD: GV0001) - Phải duy nhất trong hệ thống      │");
        System.out.println("│ Họ và tên: Tên đầy đủ của giáo viên                                    │");
        System.out.println("│ Giới tính: Nam hoặc Nữ                                                 │");
        System.out.println("│ Ngày sinh: dd/MM/yyyy (VD: 15/03/1985)                                 │");
        System.out.println("│ Địa chỉ: Địa chỉ liên hệ của giáo viên                                 │");
        System.out.println("│ Số điện thoại: Số điện thoại liên hệ                                   │");
        System.out.println("│ Email: Địa chỉ email liên hệ                                             │");
        System.out.println("│ Môn giảng dạy: Các môn học mà giáo viên có thể dạy                     │");
        System.out.println("│ Lớp chủ nhiệm: Lớp mà giáo viên làm chủ nhiệm (có thể để trống)        │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập thông tin giáo viên mới:");

        // --- Nhập mã giáo viên ---
        String id;
        while (true) {
            id = InputUtil.getString("Mã giáo viên: ");
            if (teacherService.isTeacherIdExists(id)) {
                System.out.println("Mã giáo viên đã tồn tại! Vui lòng nhập lại.");
            } else break;
        }

        String name = InputUtil.getString("Họ và tên: ");

        // --- Hiển thị danh sách môn học ---
        List<Subject> allSubjects = subjectService.getAllSubjects();
        if (allSubjects.isEmpty()) {
            System.out.println("\nKhông có môn học nào trong hệ thống. Vui lòng thêm môn trước!");
            InputUtil.pressEnterToContinue();
            return;
        }

        System.out.println("\nDanh sách môn học:");
        System.out.println("─────────────────────────────────────────────");
        System.out.printf("%-10s %-25s %-10s%n", "Mã môn", "Tên môn học", "Số tiết");
        System.out.println("─────────────────────────────────────────────");
        for (Subject s : allSubjects) {
            System.out.printf("%-10s %-25s %-10d%n",
                    s.getSubjectID(), s.getSubjectName(), s.getLessonCount());
        }

        // --- Nhập mã môn học ---
        List<String> teacherSubjects = new ArrayList<>();
        while (teacherSubjects.isEmpty()) {
            String subjectInput = InputUtil.getString("\nNhập mã môn (có thể nhập nhiều, cách nhau bằng dấu ','): ");
            if (subjectInput.trim().isEmpty()) {
                System.out.println("Không được để trống. Vui lòng nhập lại!");
                continue;
            }

            String[] ids = subjectInput.split(",");
            for (String sid : ids) {
                sid = sid.trim();
                try {
                    Optional<Subject> subjectOpt = subjectService.findById(sid);
                    if (subjectOpt.isPresent()) {
                        teacherSubjects.add(subjectOpt.get().getSubjectName());
                    } else {
                        System.out.println("Mã môn không tồn tại: " + sid);
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi khi kiểm tra mã môn '" + sid + "': " + e.getMessage());
                }
            }

            if (teacherSubjects.isEmpty()) {
                System.out.println("Không có mã môn hợp lệ. Vui lòng nhập lại!");
            }
        }

        // --- Trình độ ---
        int degreeCode = -1;
        String degree = "";
        while (degreeCode < 0 || degreeCode > 2) {
            System.out.println("\nChọn trình độ:");
            System.out.println("0 - Cử nhân");
            System.out.println("1 - Thạc sĩ");
            System.out.println("2 - Tiến sĩ");
            degreeCode = InputUtil.getInt("Nhập lựa chọn (0/1/2): ");
            switch (degreeCode) {
                case 0 -> degree = "Cử nhân";
                case 1 -> degree = "Thạc sĩ";
                case 2 -> degree = "Tiến sĩ";
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        // --- Kinh nghiệm, liên hệ ---
        int experience = InputUtil.getInt("Số năm kinh nghiệm: ");
        String email = InputUtil.getEmail("Email: ");
        String phone = InputUtil.getPhoneNumber("Số điện thoại: ");
        
        // --- Lớp chủ nhiệm với danh sách lớp ---
        System.out.println("\nLỚP CHỦ NHIỆM:");
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
            System.out.println("1. Nhập mã lớp từ danh sách trên (VD: L01A1) - sẽ tự động chuyển thành tên lớp");
            System.out.println("2. Nhập tên lớp mới nếu chưa có trong danh sách");
            System.out.println("3. Để trống nếu chưa có lớp chủ nhiệm");
            System.out.println("\nLƯU Ý: Hệ thống sẽ tự động chuyển mã lớp thành tên lớp để đảm bảo tính nhất quán!");
        } else {
            System.out.println("│ Chưa có lớp học nào trong hệ thống!                                    │");
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
            System.out.println("Bạn có thể nhập tên lớp mới hoặc để trống.");
        }
        
        String homeroomInput = InputUtil.getString("\nLớp chủ nhiệm (Enter nếu chưa có): ");
        
        // Chuyển đổi mã lớp thành tên lớp để đảm bảo tính nhất quán
        String homeroom = convertToClassName(homeroomInput, classrooms);

        // --- Trạng thái ---
        int statusCode = -1;
        String status = "";
        while (statusCode < 0 || statusCode > 2) {
            System.out.println("\nChọn trạng thái giáo viên:");
            System.out.println("0 - Đang dạy");
            System.out.println("1 - Nghỉ hưu");
            System.out.println("2 - Công tác");
            statusCode = InputUtil.getInt("Nhập lựa chọn (0/1/2): ");
            switch (statusCode) {
                case 0 -> status = "Đang dạy";
                case 1 -> status = "Nghỉ hưu";
                case 2 -> status = "Công tác";
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        // --- Thêm vào danh sách ---
        boolean added = teacherService.addTeacher(
                id, name, status, teacherSubjects, degree, experience, email, phone, homeroom
        );

        if (added) {
            System.out.println("\nĐã thêm giáo viên thành công!");
        } else {
            System.out.println("\nThêm giáo viên thất bại!");
        }

        InputUtil.pressEnterToContinue();
    }
    
    /**
     * Chuyển đổi mã lớp thành tên lớp để đảm bảo tính nhất quán dữ liệu
     * @param input Mã lớp hoặc tên lớp người dùng nhập
     * @param classrooms Danh sách lớp có sẵn
     * @return Tên lớp chuẩn hóa
     */
    private String convertToClassName(String input, List<Classroom> classrooms) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        
        input = input.trim();
        
        // Tìm trong danh sách lớp xem có trùng mã lớp không
        for (Classroom classroom : classrooms) {
            if (classroom.getClassId().equalsIgnoreCase(input)) {
                return classroom.getClassName(); // Trả về tên lớp thay vì mã lớp
            }
        }
        
        // Nếu không tìm thấy mã lớp, coi như là tên lớp và trả về nguyên văn
        return input;
    }
}
