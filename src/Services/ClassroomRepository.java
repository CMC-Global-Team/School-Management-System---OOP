package Services;

import Models.Classroom;

/**
 * ClassroomRepository - Repository cụ thể cho Classroom entity
 * Kế thừa BaseFileRepository và implement các phương thức đặc thù
 */
public class ClassroomRepository extends BaseFileRepository<Classroom> {
    
    public ClassroomRepository() {
        super(FileManager.CLASSROOM_FILE);
        FileManager.ensureFileExists(FileManager.CLASSROOM_FILE);
    }
    
    @Override
    protected Classroom parseFromString(String line) {
        return Classroom.fromString(line);
    }
    
    @Override
    protected boolean matchesKeyword(Classroom classroom, String keyword) {
        if (classroom == null || keyword == null) {
            return false;
        }
        
        String lowerKeyword = keyword.toLowerCase();
        
        // Tìm kiếm theo ID
        if (classroom.getClassId() != null && 
            classroom.getClassId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo tên lớp
        if (classroom.getClassName() != null && 
            classroom.getClassName().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo năm học
        if (classroom.getSchoolYear() != null && 
            classroom.getSchoolYear().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        // Tìm kiếm theo niên khóa
        if (classroom.getCourse() != null && 
            classroom.getCourse().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        
        return false;
    }
}
