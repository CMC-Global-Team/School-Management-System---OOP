package Services;

import Models.Student;

/**
 * StudentRepository - Repository cụ thể cho Student entity
 * Demo việc TÁI SỬ DỤNG BaseFileRepository cho entity khác
 */
public class StudentRepository extends BaseFileRepository<Student> {
    
    public StudentRepository() {
        super(FileManager.STUDENT_FILE);
        FileManager.ensureFileExists(FileManager.STUDENT_FILE);
    }
    
    @Override
    protected Student parseFromString(String line) {
        return Student.fromString(line);
    }
    
    @Override
    protected boolean matchesKeyword(Student student, String keyword) {
        if (student == null || keyword == null) {
            return false;
        }
        
        String lowerKeyword = keyword.toLowerCase();
        
        // Tìm kiếm theo ID
        if (student.getId() != null && 
            student.getId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo tên
        if (student.getName() != null && 
            student.getName().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo lớp
        if (student.getClassName() != null && 
            student.getClassName().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo giới tính
        if (student.getGender() != null && 
            student.getGender().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo địa chỉ
        if (student.getAddress() != null && 
            student.getAddress().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        return false;
    }
}

