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
    

}
