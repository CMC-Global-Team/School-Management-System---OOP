package Services;

import Models.Student;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * StudentService - Service layer cho business logic của Student
 * Demo việc TÁI SỬ DỤNG pattern tương tự ClassroomService
 * Sử dụng Singleton Pattern để đảm bảo chỉ có 1 instance duy nhất
 */
public class StudentService {

    private static StudentService instance;
    private final StudentRepository repository;

    private StudentService() {
        this.repository = new StudentRepository();
    }

    /**
     * Lấy instance duy nhất của StudentService (Singleton)
     */
    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    /**
     * Thêm học sinh mới
     */
    public boolean addStudent(String id, String name, LocalDate dateOfBirth,
                              String gender, String className, String course,
                              String parentPhone, String address, String status) {

        // Validate input
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Lỗi: Mã học sinh không được để trống!");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Lỗi: Tên học sinh không được để trống!");
            return false;
        }

        // Kiểm tra ID đã tồn tại
        if (repository.exists(id)) {
            System.out.println("Lỗi: Mã học sinh '" + id + "' đã tồn tại!");
            return false;
        }

        // Tạo student mới
        Student student = new Student(id, name, dateOfBirth, gender, className,
                course, parentPhone, address, status);

        // Thêm vào repository
        if (repository.add(student)) {
            System.out.println("✓ Thêm học sinh thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể thêm học sinh!");
            return false;
        }
    }

    /**
     * Cập nhật học sinh
     */
    public boolean updateStudent(Student student) {
        if (student == null) {
            System.out.println("Lỗi: Student không được null!");
            return false;
        }

        if (!repository.exists(student.getId())) {
            System.out.println("Lỗi: Không tìm thấy học sinh với mã '" + student.getId() + "'!");
            return false;
        }

        if (repository.update(student)) {
            System.out.println("✓ Cập nhật học sinh thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật học sinh!");
            return false;
        }
    }

    /**
     * Xóa học sinh theo ID
     */
    public boolean deleteStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Lỗi: Mã học sinh không được để trống!");
            return false;
        }

        if (!repository.exists(id)) {
            System.out.println("Lỗi: Không tìm thấy học sinh với mã '" + id + "'!");
            return false;
        }

        if (repository.delete(id)) {
            System.out.println("✓ Xóa học sinh thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể xóa học sinh!");
            return false;
        }
    }

    /**
     * Tìm học sinh theo ID
     */
    public Optional<Student> findById(String id) {
        return repository.findById(id);
    }

    /**
     * Lấy tất cả học sinh
     */
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    /**
     * Tìm kiếm học sinh theo từ khóa
     */
    public List<Student> searchStudents(String keyword) {
        return repository.search(keyword);
    }

    /**
     * Kiểm tra mã học sinh đã tồn tại chưa
     */
    public boolean isStudentIdExists(String id) {
        return repository.exists(id);
    }

    /**
     * Đếm tổng số học sinh
     */
    public int getTotalStudents() {
        return repository.count();
    }

    /**
     * Hiển thị danh sách tất cả học sinh
     */
    public void displayAllStudents() {
        List<Student> students = getAllStudents();

        if (students.isEmpty()) {
            System.out.println("\nKhông có học sinh nào trong hệ thống.");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                      DANH SÁCH HỌC SINH                                 │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-15s %-10s %-10s │%n", "Mã HS", "Họ Tên", "Ngày Sinh", "Giới Tính", "Lớp");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");

        for (Student student : students) {
            System.out.printf("│ %-10s %-25s %-15s %-10s %-10s │%n",
                    truncate(student.getId(), 10),
                    truncate(student.getName(), 25),
                    student.getDateOfBirth(),
                    truncate(student.getGender(), 10),
                    truncate(student.getClassName(), 10)
            );
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tổng số học sinh: " + students.size());
    }

    /**
     * Hiển thị kết quả tìm kiếm
     */
    public void displaySearchResults(String keyword) {
        List<Student> results = searchStudents(keyword);

        if (results.isEmpty()) {
            System.out.println("\nKhông tìm thấy học sinh nào phù hợp với từ khóa: '" + keyword + "'");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    KẾT QUẢ TÌM KIẾM                                     │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-15s %-10s %-10s │%n", "Mã HS", "Họ Tên", "Ngày Sinh", "Giới Tính", "Lớp");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");

        for (Student student : results) {
            System.out.printf("│ %-10s %-25s %-15s %-10s %-10s │%n",
                    truncate(student.getId(), 10),
                    truncate(student.getName(), 25),
                    student.getDateOfBirth(),
                    truncate(student.getGender(), 10),
                    truncate(student.getClassName(), 10)
            );
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tìm thấy: " + results.size() + " học sinh");
    }

    /**
     * Helper method để cắt chuỗi cho vừa với độ rộng
     */
    private String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}