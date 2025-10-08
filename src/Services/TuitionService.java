package Services;

import Models.Tuition;
import java.util.List;
import java.util.Optional;

public class TuitionService {
    private static TuitionService instance;
    private final TuitionRepository repository;
    private final StudentRepository studentRepository;

    private TuitionService() {
        this.repository = new TuitionRepository();
        this.studentRepository = new StudentRepository();
    }

    public static TuitionService getInstance() {
        if (instance == null) {
            instance = new TuitionService();
        }
        return instance;
    }

    public List<Tuition> getAllTuitions() {
        return repository.findAll();
    }

    public Optional<Tuition> findById(String tuitionId) {
        return repository.findById(tuitionId);
    }

    public boolean isTuitionIdExists(String tuitionId) {
        return repository.exists(tuitionId);
    }

    public int getTotalTuitions() {
        return repository.count();
    }
}
