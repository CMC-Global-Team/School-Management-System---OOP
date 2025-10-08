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
            System.out.println("Thêm học sinh thành công!");
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
            System.out.println("Cập nhật học sinh thành công!");
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
            System.out.println("Xóa học sinh thành công!");
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
     * Lọc học sinh theo lớp
     */
    public List<Student> filterByClass(String className) {
        return repository.findAll().stream()
                .filter(student -> student.getClassName().equalsIgnoreCase(className))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lọc học sinh theo trạng thái
     */
    public List<Student> filterByStatus(String status) {
        return repository.findAll().stream()
                .filter(student -> student.getStatus().equalsIgnoreCase(status))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lọc học sinh theo giới tính
     */
    public List<Student> filterByGender(String gender) {
        return repository.findAll().stream()
                .filter(student -> student.getGender().equalsIgnoreCase(gender))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lọc học sinh theo nhiều tiêu chí
     */
    public List<Student> filterStudents(String className, String status, String gender) {
        return repository.findAll().stream()
                .filter(student -> {
                    boolean classMatch = className == null || className.trim().isEmpty() || 
                                      student.getClassName().equalsIgnoreCase(className);
                    boolean statusMatch = status == null || status.trim().isEmpty() || 
                                        student.getStatus().equalsIgnoreCase(status);
                    boolean genderMatch = gender == null || gender.trim().isEmpty() || 
                                        student.getGender().equalsIgnoreCase(gender);
                    return classMatch && statusMatch && genderMatch;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Xuất danh sách học sinh ra file .txt
     */
    public boolean exportStudentsToFile(String filename, List<Student> students) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filename)) {
            
            // Ghi header
            writer.write("DANH SÁCH HỌC SINH\n");
            writer.write("=".repeat(80) + "\n");
            writer.write(String.format("%-10s %-25s %-15s %-10s %-10s %-15s %-15s %-20s %-10s\n",
                    "Mã HS", "Họ Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Khóa", "SĐT PH", "Địa Chỉ", "Trạng Thái"));
            writer.write("-".repeat(80) + "\n");
            
            // Ghi dữ liệu học sinh
            for (Student student : students) {
                writer.write(String.format("%-10s %-25s %-15s %-10s %-10s %-15s %-15s %-20s %-10s\n",
                        student.getId(),
                        student.getName(),
                        student.getDateOfBirth(),
                        student.getGender(),
                        student.getClassName(),
                        student.getCourse(),
                        student.getParentPhone(),
                        student.getAddress(),
                        student.getStatus()));
            }
            
            writer.write("-".repeat(80) + "\n");
            writer.write("Tổng số học sinh: " + students.size() + "\n");
            writer.write("Ngày xuất: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            
            return true;
        } catch (java.io.IOException e) {
            System.err.println("Lỗi khi xuất file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xuất tất cả học sinh ra file .txt
     */
    public boolean exportAllStudentsToFile(String filename) {
        return exportStudentsToFile(filename, getAllStudents());
    }

    /**
     * Hiển thị kết quả lọc
     */
    public void displayFilterResults(List<Student> students, String filterType) {
        if (students.isEmpty()) {
            System.out.println("\nKhông có học sinh nào phù hợp với tiêu chí lọc.");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    KẾT QUẢ LỌC " + filterType.toUpperCase() + "                                    │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-15s %-10s %-10s %-10s │%n", "Mã HS", "Họ Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Trạng Thái");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");

        for (Student student : students) {
            System.out.printf("│ %-10s %-25s %-15s %-10s %-10s %-10s │%n",
                    truncate(student.getId(), 10),
                    truncate(student.getName(), 25),
                    student.getDateOfBirth(),
                    truncate(student.getGender(), 10),
                    truncate(student.getClassName(), 10),
                    truncate(student.getStatus(), 10)
            );
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tìm thấy: " + students.size() + " học sinh");
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