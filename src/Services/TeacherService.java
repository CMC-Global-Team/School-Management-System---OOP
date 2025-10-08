package Services;

import Models.Teacher;
import java.util.List;
import java.util.Optional;

/**
 * TeacherService - Lớp Service xử lý logic nghiệp vụ cho giáo viên
 * Dựa theo mẫu StudentService, sử dụng Singleton Pattern
 */
public class TeacherService {

    private static TeacherService instance;
    private final TeacherRepository repository;

    private TeacherService() {
        this.repository = new TeacherRepository();
    }

    /**
     * Lấy instance duy nhất (Singleton)
     */
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
                              List<String> teacherSubjects, String degree, int experience,
                              String email, String phone, String homeroom) {

        if (id == null || id.trim().isEmpty()) {
            System.out.println("Lỗi: Mã giáo viên không được để trống!");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Lỗi: Tên giáo viên không được để trống!");
            return false;
        }

        if (repository.exists(id)) {
            System.out.println("Lỗi: Mã giáo viên '" + id + "' đã tồn tại!");
            return false;
        }

        Teacher teacher = new Teacher(id, name, status, teacherSubjects, degree, experience, email, phone, homeroom);

        if (repository.add(teacher)) {
            System.out.println("✓ Thêm giáo viên thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể thêm giáo viên!");
            return false;
        }
    }
    /**
     * gán giáo viên với môn
     */
    public boolean assignSubjectsToTeacher(String teacherID, List<String> subjectIDs) {
        Optional<Teacher> optTeacher = repository.findById(teacherID);
        if (optTeacher.isEmpty()) {
            System.out.println("Không tìm thấy giáo viên với ID: " + teacherID);
            return false;
        }

        Teacher teacher = optTeacher.get();
        teacher.setTeacherSubjects(subjectIDs);

        boolean updated = repository.update(teacher);
        if (updated) {
            System.out.println("✓ Gán môn học thành công cho giáo viên " + teacher.getName());
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật giáo viên!");
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

        if (!repository.exists(teacher.getId())) {
            System.out.println("Lỗi: Không tìm thấy giáo viên với mã '" + teacher.getId() + "'!");
            return false;
        }

        if (repository.update(teacher)) {
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

        if (!repository.exists(id)) {
            System.out.println("Lỗi: Không tìm thấy giáo viên với mã '" + id + "'!");
            return false;
        }

        if (repository.delete(id)) {
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
        return repository.findById(id);
    }

    /**
     * Lấy toàn bộ danh sách giáo viên
     */
    public List<Teacher> getAllTeachers() {
        return repository.findAll();
    }

    /**
     * Tìm kiếm giáo viên theo từ khóa
     */
    public List<Teacher> searchTeachers(String keyword) {
        return repository.search(keyword);
    }

    /**
     * Kiểm tra mã giáo viên đã tồn tại chưa
     */
    public boolean isTeacherIdExists(String id) {
        return repository.exists(id);
    }

    /**
     * Đếm tổng số giáo viên
     */
    public int getTotalTeachers() {
        return repository.count();
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
            String subjects = t.getTeacherSubjects() != null && !t.getTeacherSubjects().isEmpty()
                    ? String.join(", ", t.getTeacherSubjects())
                    : "-";

            System.out.printf("│ %-10s %-20s %-15s %-15s %-10d %-20s │%n",
                    truncate(t.getId(), 10),
                    truncate(t.getName(), 20),
                    truncate(subjects, 15),
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
            String subjects = String.join(", ", t.getTeacherSubjects()); // List -> String

            System.out.printf("│ %-10s %-20s %-15s %-15s %-10d %-20s │%n",
                    truncate(t.getId(), 10),
                    truncate(t.getName(), 20),
                    truncate(subjects, 15),
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
     * Xuất danh sách giáo viên ra file
     * @param filePath Đường dẫn file xuất
     * @param teachers Danh sách giáo viên cần xuất
     * @return true nếu xuất thành công, false nếu thất bại
     */
    public boolean exportTeachersToFile(String filePath, List<Teacher> teachers) {
        if (teachers == null || teachers.isEmpty()) {
            System.out.println("Không có dữ liệu để xuất!");
            return false;
        }

        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(filePath, java.nio.charset.StandardCharsets.UTF_8))) {
            // Ghi header
            bw.write("DANH SÁCH GIÁO VIÊN");
            bw.newLine();
            bw.write("Ngày xuất: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            bw.newLine();
            bw.write("Tổng số: " + teachers.size() + " giáo viên");
            bw.newLine();
            bw.newLine();
            
            // Ghi tiêu đề cột
            bw.write("Mã GV\tTên\tGiới tính\tNgày sinh\tĐịa chỉ\tSĐT\tEmail\tMôn dạy\tLớp CN");
            bw.newLine();
            bw.write("=".repeat(100));
            bw.newLine();
            
            // Ghi dữ liệu
            for (Teacher teacher : teachers) {
                String subjects = teacher.getTeacherSubjects().isEmpty() ? "Chưa có" : String.join(", ", teacher.getTeacherSubjects());
                String homeroom = (teacher.getTeacherHomeroom() == null || teacher.getTeacherHomeroom().isEmpty()) ? "Chưa có" : teacher.getTeacherHomeroom();
                String birthDate = teacher.getBirthDate() != null ? teacher.getBirthDate().toString() : "N/A";
                
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    teacher.getId(),
                    teacher.getName(),
                    teacher.getGender(),
                    birthDate,
                    teacher.getAddress(),
                    teacher.getPhoneNumber(),
                    teacher.getEmail(),
                    subjects,
                    homeroom
                ));
                bw.newLine();
            }
            
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi khi xuất file: " + e.getMessage());
            return false;
        }
    }
}
