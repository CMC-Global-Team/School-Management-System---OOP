package Screen.Teacher;

import Models.Classroom;
import Models.Teacher;
import Screen.AbstractScreen;
import Services.FileManager;
import Utils.FileUtil;
import Utils.InputUtil;
import java.util.ArrayList;
import java.util.List;

public class AssignHomeroomScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│      GÁN (CẬP NHẬT) LỚP CHỦ NHIỆM         │");
        System.out.println("└────────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           THÔNG TIN CẦN NHẬP                            │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Chọn giáo viên từ danh sách hiển thị                                │");
        System.out.println("│ 2. Xem thông tin lớp chủ nhiệm hiện tại                                │");
        System.out.println("│ 3. Nhập tên lớp mới hoặc chọn từ danh sách lớp có sẵn                  │");
        System.out.println("│ 4. Hệ thống sẽ cập nhật thông tin lớp chủ nhiệm                        │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý: Một giáo viên chỉ có thể làm chủ nhiệm một lớp                   │");
        System.out.println("│         Nếu lớp đã có giáo viên chủ nhiệm khác, sẽ được thay thế        │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<Teacher> teachers = new ArrayList<>();

        try {
            if (FileUtil.fileExists(FileManager.TEACHER_FILE)) {
                List<String> teacherLines = FileUtil.readLines(FileManager.TEACHER_FILE);
                for (String line : teacherLines) {
                    Teacher t = Teacher.fromString(line);
                    if (t != null) teachers.add(t);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file giáo viên: " + e.getMessage());
            return;
        }

        if (teachers.isEmpty()) {
            System.out.println("Chưa có giáo viên nào trong hệ thống!");
            return;
        }

        System.out.println("\nDanh sách giáo viên:");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            System.out.printf("%d. %-10s | %-25s | CN: %s\n",
                    i + 1,
                    t.getId(),
                    t.getName(),
                    (t.getTeacherHomeroom() == null || t.getTeacherHomeroom().isEmpty()) ? "Chưa có" : t.getTeacherHomeroom());
        }

        int choice = InputUtil.getInt("Chọn số thứ tự giáo viên để gán lớp: ");
        if (choice < 1 || choice > teachers.size()) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }

        Teacher selectedTeacher = teachers.get(choice - 1);
        System.out.println("\nGiáo viên được chọn: " + selectedTeacher.getName());
        System.out.println("Lớp hiện tại: " +
                (selectedTeacher.getTeacherHomeroom() == null || selectedTeacher.getTeacherHomeroom().isEmpty()
                        ? "Chưa có" : selectedTeacher.getTeacherHomeroom()));

        // Hiển thị danh sách lớp có sẵn
        System.out.println("\nDANH SÁCH LỚP CÓ SẴN:");
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
            System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                           DANH SÁCH LỚP HỌC                            │");
            System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
            System.out.printf("│ %-8s %-20s %-15s %-20s │%n", "Mã lớp", "Tên lớp", "Năm học", "Niên khóa");
            System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
            
            for (int i = 0; i < classrooms.size(); i++) {
                Classroom c = classrooms.get(i);
                System.out.printf("│ %-8s %-20s %-15s %-20s │%n", 
                    c.getClassId(), c.getClassName(), c.getSchoolYear(), c.getCourse());
            }
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
            
            System.out.println("\nBạn có thể:");
            System.out.println("1. Nhập mã lớp từ danh sách trên (VD: L01A1) - sẽ tự động chuyển thành tên lớp");
            System.out.println("2. Nhập tên lớp mới nếu chưa có trong danh sách");
            System.out.println("\nLƯU Ý: Hệ thống sẽ tự động chuyển mã lớp thành tên lớp để đảm bảo tính nhất quán!");
        } else {
            System.out.println("Chưa có lớp học nào trong hệ thống!");
            System.out.println("Bạn có thể nhập tên lớp mới.");
        }

        String newHomeroomInput = InputUtil.getNonEmptyString("\nNhập lớp chủ nhiệm mới: ");

        // Chuyển đổi mã lớp thành tên lớp để đảm bảo tính nhất quán
        String newHomeroom = convertToClassName(newHomeroomInput, classrooms);

        // Ghi đè lớp chủ nhiệm
        selectedTeacher.setTeacherHomeroom(newHomeroom);

        // Ghi lại file
        List<String> updatedLines = new ArrayList<>();
        for (Teacher t : teachers) {
            updatedLines.add(t.toString());
        }

        try {
            FileUtil.writeLines(FileManager.TEACHER_FILE, updatedLines);
            System.out.println("\nĐã cập nhật lớp chủ nhiệm cho giáo viên!");
        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
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
