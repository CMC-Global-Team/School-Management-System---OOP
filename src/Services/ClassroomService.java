package Services;

import Models.Classroom;
import java.util.List;
import java.util.Optional;

/**
 * ClassroomService - Service layer cho business logic của Classroom
 * Sử dụng Repository pattern để tách biệt business logic và data access
 * Sử dụng Singleton Pattern để đảm bảo chỉ có 1 instance duy nhất
 */
public class ClassroomService {
    
    private static ClassroomService instance;
    private final ClassroomRepository repository;
    
    private ClassroomService() {
        this.repository = new ClassroomRepository();
    }
    
    /**
     * Lấy instance duy nhất của ClassroomService (Singleton)
     */
    public static ClassroomService getInstance() {
        if (instance == null) {
            instance = new ClassroomService();
        }
        return instance;
    }
    
    /**
     * Thêm lớp học mới
     */
    public boolean addClass(String classId, String className, String schoolYear, String course) {
        
        // Validate input
        if (classId == null || classId.trim().isEmpty()) {
            System.out.println("Lỗi: Mã lớp không được để trống!");
            return false;
        }
        
        if (className == null || className.trim().isEmpty()) {
            System.out.println("Lỗi: Tên lớp không được để trống!");
            return false;
        }
        
        // Kiểm tra ID đã tồn tại
        if (repository.exists(classId)) {
            System.out.println("Lỗi: Mã lớp '" + classId + "' đã tồn tại!");
            return false;
        }
        
        // Tạo classroom mới
        Classroom classroom = new Classroom(classId, className, schoolYear, course);
        
        // Thêm vào repository
        if (repository.add(classroom)) {
            System.out.println("✓ Thêm lớp học thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể thêm lớp học!");
            return false;
        }
    }
    
    /**
     * Cập nhật lớp học
     */
    public boolean updateClass(Classroom classroom) {
        if (classroom == null) {
            System.out.println("Lỗi: Classroom không được null!");
            return false;
        }
        
        if (!repository.exists(classroom.getClassId())) {
            System.out.println("Lỗi: Không tìm thấy lớp học với mã '" + classroom.getClassId() + "'!");
            return false;
        }
        
        if (repository.update(classroom)) {
            System.out.println("✓ Cập nhật lớp học thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật lớp học!");
            return false;
        }
    }
    
    /**
     * Xóa lớp học theo ID
     */
    public boolean deleteClass(String classId) {
        if (classId == null || classId.trim().isEmpty()) {
            System.out.println("Lỗi: Mã lớp không được để trống!");
            return false;
        }
        
        if (!repository.exists(classId)) {
            System.out.println("Lỗi: Không tìm thấy lớp học với mã '" + classId + "'!");
            return false;
        }
        
        if (repository.delete(classId)) {
            System.out.println("✓ Xóa lớp học thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể xóa lớp học!");
            return false;
        }
    }
    
    /**
     * Tìm lớp học theo ID
     */
    public Optional<Classroom> findById(String classId) {
        return repository.findById(classId);
    }
    
    /**
     * Lấy tất cả lớp học
     */
    public List<Classroom> getAllClasses() {
        return repository.findAll();
    }
    
    /**
     * Tìm kiếm lớp học theo từ khóa
     */
    public List<Classroom> searchClasses(String keyword) {
        return repository.search(keyword);
    }
    
    /**
     * Kiểm tra mã lớp đã tồn tại chưa
     */
    public boolean isClassIdExists(String classId) {
        return repository.exists(classId);
    }
    
    /**
     * Đếm tổng số lớp học
     */
    public int getTotalClasses() {
        return repository.count();
    }
    
    /**
     * Hiển thị danh sách tất cả lớp học
     */
    public void displayAllClasses() {
        List<Classroom> classes = getAllClasses();
        
        if (classes.isEmpty()) {
            System.out.println("\nKhông có lớp học nào trong hệ thống.");
            return;
        }
        
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│                  DANH SÁCH LỚP HỌC                            │");
        System.out.println("├───────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-15s %-10s │%n", "Mã Lớp", "Tên Lớp", "Năm Học", "Niên Khóa");
        System.out.println("├───────────────────────────────────────────────────────────────┤");
        
        for (Classroom classroom : classes) {
            System.out.printf("│ %-10s %-25s %-15s %-10s │%n",
                truncate(classroom.getClassId(), 10),
                truncate(classroom.getClassName(), 25),
                truncate(classroom.getSchoolYear(), 15),
                truncate(classroom.getCourse(), 10)
            );
        }
        
        System.out.println("└─────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tổng số lớp: " + classes.size());
    }
    
    /**
     * Hiển thị kết quả tìm kiếm
     */
    public void displaySearchResults(String keyword) {
        List<Classroom> results = searchClasses(keyword);
        
        if (results.isEmpty()) {
            System.out.println("\nKhông tìm thấy lớp học nào phù hợp với từ khóa: '" + keyword + "'");
            return;
        }
        
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│                  KẾT QUẢ TÌM KIẾM                             │");
        System.out.println("├───────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-15s %-10s │%n", "Mã Lớp", "Tên Lớp", "Năm Học", "Niên Khóa");
        System.out.println("├───────────────────────────────────────────────────────────────┤");
        
        for (Classroom classroom : results) {
            System.out.printf("│ %-10s %-25s %-15s %-10s │%n",
                truncate(classroom.getClassId(), 10),
                truncate(classroom.getClassName(), 25),
                truncate(classroom.getSchoolYear(), 15),
                truncate(classroom.getCourse(), 10)
            );
        }
        
        System.out.println("└─────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tìm thấy: " + results.size() + " lớp học");
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
