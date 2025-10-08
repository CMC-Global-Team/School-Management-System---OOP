package Services;

import Models.Grade;

import java.util.List;

public class GradeServices {
    private static GradeServices instance;
    private final GradeRepository repository;

    private GradeServices() {
        this.repository = new GradeRepository();
    }

    /**
     * Lấy instance duy nhất của ClassroomService (Singleton)
     */
    public static GradeServices getInstance() {
        if (instance == null) {
            instance = new GradeServices();
        }
        return instance;
    }

    /**
     * Tìm điểm theo mã học sinh
     */
    public List<Grade> getAllGrade() {
        return repository.findAll();
    }
}
