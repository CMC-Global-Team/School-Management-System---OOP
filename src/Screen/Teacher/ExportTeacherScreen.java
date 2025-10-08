package Screen.Teacher;

import Models.Teacher;
import Screen.AbstractScreen;
import Services.TeacherService;
import Utils.InputUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ExportTeacherScreen - Màn hình xuất danh sách giáo viên
 */
public class ExportTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService = TeacherService.getInstance();
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│         XUẤT DANH SÁCH GIÁO VIÊN        │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CÁC TÙY CHỌN XUẤT                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Xuất tất cả giáo viên - Xuất toàn bộ danh sách giáo viên             │");
        System.out.println("│ 2. Xuất theo môn dạy - Xuất giáo viên dạy môn học cụ thể               │");
        System.out.println("│ 3. Xuất theo lớp chủ nhiệm - Xuất giáo viên làm chủ nhiệm lớp cụ thể     │");
        System.out.println("│ 4. Xuất theo trạng thái - Xuất giáo viên theo trạng thái làm việc       │");
        System.out.println("│                                                                         │");
        System.out.println("│ ĐỊNH DẠNG FILE: .txt với mã hóa UTF-8                                  │");
        System.out.println("│ TÊN FILE: Tự động tạo với timestamp để tránh trùng lặp                  │");
        System.out.println("│ VỊ TRÍ: Thư mục data/ của hệ thống                                      │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        
        if (teachers.isEmpty()) {
            System.out.println("Không có giáo viên nào trong hệ thống để xuất!");
            pause();
            return;
        }

        System.out.println("\nChọn loại xuất danh sách:");
        System.out.println("1. Xuất tất cả giáo viên");
        System.out.println("2. Xuất theo môn dạy");
        System.out.println("3. Xuất theo lớp chủ nhiệm");
        System.out.println("4. Xuất theo trạng thái");
        System.out.println("0. Quay lại");

        int choice = InputUtil.getInt("Nhập lựa chọn: ");

        switch (choice) {
            case 1:
                exportAllTeachers(teachers);
                break;
            case 2:
                exportBySubject(teachers);
                break;
            case 3:
                exportByHomeroom(teachers);
                break;
            case 4:
                exportByStatus(teachers);
                break;
            case 0:
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                pause();
        }
    }

    private void exportAllTeachers(List<Teacher> teachers) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String fileName = "data/export_all_teachers_" + timestamp + ".txt";
        
        if (teacherService.exportTeachersToFile(fileName, teachers)) {
            System.out.println("\nXuất danh sách tất cả giáo viên thành công!");
            System.out.println("File: " + fileName);
            System.out.println("Số lượng: " + teachers.size() + " giáo viên");
        } else {
            System.out.println("\nLỗi khi xuất file!");
        }
        pause();
    }

    private void exportBySubject(List<Teacher> teachers) {
        String subject = InputUtil.getNonEmptyString("Nhập tên môn học: ");
        
        List<Teacher> filteredTeachers = teachers.stream()
                .filter(t -> t.getTeacherSubjects().stream()
                        .anyMatch(s -> s.toLowerCase().contains(subject.toLowerCase())))
                .toList();

        if (filteredTeachers.isEmpty()) {
            System.out.println("Không tìm thấy giáo viên nào dạy môn: " + subject);
            pause();
            return;
        }

        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String fileName = "data/export_teachers_subject_" + subject.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".txt";
        
        if (teacherService.exportTeachersToFile(fileName, filteredTeachers)) {
            System.out.println("\nXuất danh sách giáo viên dạy môn '" + subject + "' thành công!");
            System.out.println("File: " + fileName);
            System.out.println("Số lượng: " + filteredTeachers.size() + " giáo viên");
        } else {
            System.out.println("\nLỗi khi xuất file!");
        }
        pause();
    }

    private void exportByHomeroom(List<Teacher> teachers) {
        String homeroom = InputUtil.getNonEmptyString("Nhập tên lớp chủ nhiệm: ");
        
        List<Teacher> filteredTeachers = teachers.stream()
                .filter(t -> t.getTeacherHomeroom() != null && 
                           t.getTeacherHomeroom().toLowerCase().contains(homeroom.toLowerCase()))
                .toList();

        if (filteredTeachers.isEmpty()) {
            System.out.println("Không tìm thấy giáo viên nào làm chủ nhiệm lớp: " + homeroom);
            pause();
            return;
        }

        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String fileName = "data/export_teachers_homeroom_" + homeroom.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".txt";
        
        if (teacherService.exportTeachersToFile(fileName, filteredTeachers)) {
            System.out.println("\nXuất danh sách giáo viên chủ nhiệm lớp '" + homeroom + "' thành công!");
            System.out.println("File: " + fileName);
            System.out.println("Số lượng: " + filteredTeachers.size() + " giáo viên");
        } else {
            System.out.println("\nLỗi khi xuất file!");
        }
        pause();
    }

    private void exportByStatus(List<Teacher> teachers) {
        System.out.println("Chọn trạng thái:");
        System.out.println("1. Đang làm việc");
        System.out.println("2. Nghỉ việc");
        
        int statusChoice = InputUtil.getInt("Nhập lựa chọn: ");
        String status;
        
        switch (statusChoice) {
            case 1:
                status = "Đang làm việc";
                break;
            case 2:
                status = "Nghỉ việc";
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                pause();
                return;
        }
        
        List<Teacher> filteredTeachers = teachers.stream()
                .filter(t -> status.equals(t.getStatus()))
                .toList();

        if (filteredTeachers.isEmpty()) {
            System.out.println("Không tìm thấy giáo viên nào có trạng thái: " + status);
            pause();
            return;
        }

        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String fileName = "data/export_teachers_status_" + status.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".txt";
        
        if (teacherService.exportTeachersToFile(fileName, filteredTeachers)) {
            System.out.println("\nXuất danh sách giáo viên trạng thái '" + status + "' thành công!");
            System.out.println("File: " + fileName);
            System.out.println("Số lượng: " + filteredTeachers.size() + " giáo viên");
        } else {
            System.out.println("\nLỗi khi xuất file!");
        }
        pause();
    }
}
