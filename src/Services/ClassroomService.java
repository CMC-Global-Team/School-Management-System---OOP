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
    
    
}
