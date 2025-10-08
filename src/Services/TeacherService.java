package Services;
import Models.Classroom;

import Models.Teacher;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import Models.TeachingAssignment;

import Utils.FileUtil;
import Utils.InputUtil;
/**
 * TeacherService - Lớp Service xử lý logic nghiệp vụ cho giáo viên
 * Dựa theo mẫu StudentService, sử dụng Singleton Pattern
 */
public class TeacherService {
    private static TeacherService instance;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classRoomRepository;
    private final Scanner scanner;

    private TeacherService() {
        this.teacherRepository = new TeacherRepository();
        this.classRoomRepository = new ClassroomRepository();
        this.scanner = new Scanner(System.in);
    }

    public static TeacherService getInstance() {
        if (instance == null) {
            instance = new TeacherService();
        }
        return instance;
    }


    /**
     * Thêm giáo viên mới
     */
    public boolean addTeacher(String id, String name, String status,
                              String subject, String degree, int experience,
                              String email, String phone, String homeroom) {

        if (id == null || id.trim().isEmpty()) {
            System.out.println("Lỗi: Mã giáo viên không được để trống!");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Lỗi: Tên giáo viên không được để trống!");
            return false;
        }

        if (teacherRepository.exists(id)) {
            System.out.println("Lỗi: Mã giáo viên '" + id + "' đã tồn tại!");
            return false;
        }

        Teacher teacher = new Teacher(id, name, status, subject, degree, experience, email, phone, homeroom);

        if (teacherRepository.add(teacher)) {
            System.out.println("✓ Thêm giáo viên thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể thêm giáo viên!");
            return false;
        }
    }

    /**
     * Cập nhật thông tin giáo viên
     */
    public boolean updateTeacher(Teacher teacher) {
        if (teacher == null) {
            System.out.println("Lỗi: Teacher không được null!");
            return false;
        }

        if (!teacherRepository.exists(teacher.getId())) {
            System.out.println("Lỗi: Không tìm thấy giáo viên với mã '" + teacher.getId() + "'!");
            return false;
        }

        if (teacherRepository.update(teacher)) {
            System.out.println("✓ Cập nhật giáo viên thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật giáo viên!");
            return false;
        }
    }

    /**
     * Xóa giáo viên theo ID
     */
    public boolean deleteTeacher(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Lỗi: Mã giáo viên không được để trống!");
            return false;
        }

        if (!teacherRepository.exists(id)) {
            System.out.println("Lỗi: Không tìm thấy giáo viên với mã '" + id + "'!");
            return false;
        }

        if (teacherRepository.delete(id)) {
            System.out.println("✓ Xóa giáo viên thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể xóa giáo viên!");
            return false;
        }
    }

    /**
     * Tìm giáo viên theo ID
     */
    public Optional<Teacher> findById(String id) {
        return teacherRepository.findById(id);
    }

    /**
     * Lấy toàn bộ danh sách giáo viên
     */
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    /**
     * Tìm kiếm giáo viên theo từ khóa
     */
    public List<Teacher> searchTeachers(String keyword) {
        return teacherRepository.search(keyword);
    }

    /**
     * Kiểm tra mã giáo viên đã tồn tại chưa
     */
    public boolean isTeacherIdExists(String id) {
        return teacherRepository.exists(id);
    }

    /**
     * Đếm tổng số giáo viên
     */
    public int getTotalTeachers() {
        return teacherRepository.count();
    }

    /**
     * Hiển thị danh sách tất cả giáo viên
     */
    public void displayAllTeachers() {
        List<Teacher> teachers = getAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("\nKhông có giáo viên nào trong hệ thống.");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                              DANH SÁCH GIÁO VIÊN                                   │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-20s %-15s %-15s %-10s %-20s │%n",
                "Mã GV", "Họ Tên", "Môn Dạy", "Trình Độ", "Kinh Nghiệm", "Chủ Nhiệm");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────┤");

        for (Teacher t : teachers) {
            System.out.printf("│ %-10s %-20s %-15s %-15s %-10d %-20s │%n",
                    truncate(t.getId(), 10),
                    truncate(t.getName(), 20),
                    truncate(t.getTeacherSubject(), 15),
                    truncate(t.getTeacherDegree(), 15),
                    t.getTeacherExperience(),
                    truncate(t.getTeacherHomeroom(), 20)
            );
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tổng số giáo viên: " + teachers.size());
    }

    /**
     * Hiển thị kết quả tìm kiếm giáo viên
     */
    public void displaySearchResults(String keyword) {
        List<Teacher> results = searchTeachers(keyword);

        if (results.isEmpty()) {
            System.out.println("\nKhông tìm thấy giáo viên nào phù hợp với từ khóa: '" + keyword + "'");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                            KẾT QUẢ TÌM KIẾM GIÁO VIÊN                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-20s %-15s %-15s %-10s %-20s │%n",
                "Mã GV", "Họ Tên", "Môn Dạy", "Trình Độ", "Kinh Nghiệm", "Chủ Nhiệm");
        System.out.println("├─────────────────────────────────────────────────────────────────────────────────────┤");

        for (Teacher t : results) {
            System.out.printf("│ %-10s %-20s %-15s %-15s %-10d %-20s │%n",
                    truncate(t.getId(), 10),
                    truncate(t.getName(), 20),
                    truncate(t.getTeacherSubject(), 15),
                    truncate(t.getTeacherDegree(), 15),
                    t.getTeacherExperience(),
                    truncate(t.getTeacherHomeroom(), 20)
            );
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tìm thấy: " + results.size() + " giáo viên");
    }

    /**
     * Helper method để cắt chuỗi cho vừa với độ rộng
     */
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    /**
     * PHân công giảng daạy cho giáo viên
     */

    public void assignTeacherToClass() {
        List<String> teacherLines = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        try {
            if (FileUtil.fileExists("Data/teachers.txt")) {
                teacherLines = FileUtil.readLines("Data/teachers.txt");
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

        String newHomeroom = InputUtil.getNonEmptyString("Nhập lớp chủ nhiệm mới: ");

        // Ghi đè lớp chủ nhiệm
        selectedTeacher.setTeacherHomeroom(newHomeroom);

        // Ghi lại file
        List<String> updatedLines = new ArrayList<>();
        for (Teacher t : teachers) {
            updatedLines.add(t.toString());
        }

        try {
            FileUtil.writeLines("Data/teachers.txt", updatedLines);
            System.out.println("\nĐã cập nhật lớp chủ nhiệm cho giáo viên!");
        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }

    public void assignTeaching() {
        // 1️⃣ Đọc file danh sách giáo viên
        List<String> teacherLines;
        try {
            teacherLines = FileUtil.readLines("Data/teachers.txt");
        } catch (Exception e) {
            System.out.println("Lỗi đọc file giáo viên: " + e.getMessage());
            return;
        }

        if (teacherLines.isEmpty()) {
            System.out.println("⚠ Chưa có giáo viên nào trong hệ thống!");
            return;
        }

        // 2️⃣ Hiển thị danh sách giáo viên
        System.out.println("\n=== DANH SÁCH GIÁO VIÊN ===");
        for (String line : teacherLines) {
            Teacher t = Teacher.fromString(line);
            if (t != null)
                System.out.printf("ID: %-8s | Tên: %-20s | Môn: %-15s | Lớp CN: %s%n",
                        t.getId(), t.getName(), t.getTeacherSubject(), t.getTeacherHomeroom());
        }

        // 3️⃣ Chọn giáo viên cần phân công
        String teacherId = InputUtil.getNonEmptyString("\nNhập mã giáo viên cần phân công: ");
        Teacher selectedTeacher = null;
        for (String line : teacherLines) {
            Teacher t = Teacher.fromString(line);
            if (t != null && t.getId().equalsIgnoreCase(teacherId)) {
                selectedTeacher = t;
                break;
            }
        }

        if (selectedTeacher == null) {
            System.out.println("Không tìm thấy giáo viên có mã: " + teacherId);
            return;
        }

        // 4️⃣ Nhập thông tin phân công
        String subject = InputUtil.getNonEmptyString("Nhập môn giảng dạy: ");
        String className = InputUtil.getNonEmptyString("Nhập lớp giảng dạy: ");

        TeachingAssignment assignment = new TeachingAssignment(
                selectedTeacher.getId(),
                selectedTeacher.getName(),
                subject,
                className
        );

        // 5️⃣ Đọc file phân công cũ + thêm mới
        List<String> assignmentLines = new ArrayList<>();
        try {
            if (FileUtil.fileExists("Data/teacher_assignments.txt")) {
                assignmentLines = FileUtil.readLines("Data/teacher_assignments.txt");
            }
        } catch (Exception e) {
            System.out.println("⚠ Lỗi đọc file phân công: " + e.getMessage());
        }

        assignmentLines.add(assignment.toString());

        // 6️⃣ Ghi lại file
        try {
            FileUtil.writeLines("Data/teacher_assignments.txt", assignmentLines);
            System.out.println("\nĐã phân công giảng dạy thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }

    /**
     * Hiển thị danh sách lớp và môn mà giáo viên đang giảng dạy
     */
    public void showTeachingClassesByTeacher() {
        try {
            // Đọc file phân công
            List<String> lines = FileUtil.readLines("Data/teacher_assignments.txt");
            if (lines.isEmpty()) {
                System.out.println("Hiện chưa có dữ liệu phân công giảng dạy nào!");
                InputUtil.pressEnterToContinue();
                return;
            }

            // Nhập mã giáo viên cần xem
            String teacherId = InputUtil.getNonEmptyString("Nhập mã giáo viên cần xem: ");

            // Lọc danh sách lớp theo mã giáo viên
            List<TeachingAssignment> assignments = new ArrayList<>();
            for (String line : lines) {
                TeachingAssignment ta = TeachingAssignment.fromString(line);
                if (ta != null && ta.getTeacherId().equalsIgnoreCase(teacherId)) {
                    assignments.add(ta);
                }
            }

            if (assignments.isEmpty()) {
                System.out.println("⚠ Giáo viên này hiện chưa được phân công giảng dạy lớp nào!");
                InputUtil.pressEnterToContinue();
                return;
            }

            // Lấy tên giáo viên và in danh sách lớp - môn
            String teacherName = assignments.get(0).getTeacherName();
            System.out.println("\n=== DANH SÁCH LỚP & MÔN GIẢNG DẠY CỦA GIÁO VIÊN ===");
            System.out.println("Giáo viên: " + teacherName + " (" + teacherId + ")");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-20s %-20s%n", "Lớp Học", "Môn Giảng Dạy");
            System.out.println("-----------------------------------------------------------");

            for (TeachingAssignment ta : assignments) {
                System.out.printf("%-20s %-20s%n", ta.getClassName(), ta.getSubject());
            }

            System.out.println("-----------------------------------------------------------");
            System.out.printf("Tổng số lớp đang giảng dạy: %d%n", assignments.size());

        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }



}
