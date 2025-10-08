package Services;

import Models.Tuition;
import Utils.InputUtil;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class TuitionService {
    private static TuitionService instance;
    private final TuitionRepository repository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final StudentRepository studentRepository;

    private TuitionService() {
        this.repository = new TuitionRepository();

        this.studentRepository = new StudentRepository();
    }

    /**
     * Lấy instance duy nhất của TuitionService (Singleton)
     */
    public static TuitionService getInstance() {
        if (instance == null) {
            instance = new TuitionService();
        }
        return instance;
    }
}
