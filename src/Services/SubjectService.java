package Services;

import Models.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectService {
    private static SubjectService instance;
    private final SubjectRepository repository;

    private SubjectService() {
        this.repository = new SubjectRepository();
    }

    /**
     * Lấy instance duy nhất của SubjectService (Singleton)
     */
    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }

    /**
     * Thêm môn học mới
     */
    public boolean addSubject(String subjectID, String subjectName, int lessonCount, double confficient,
                              String subjectType, String description, List<String> teacherInCharge, String status) {

        if (subjectID == null || subjectID.trim().isEmpty()) {
            System.out.println("Lỗi: Mã môn học không được để trống!");
            return false;
        }

        if (subjectName == null || subjectName.trim().isEmpty()) {
            System.out.println("Lỗi: Tên môn học không được để trống!");
            return false;
        }

        // Kiểm tra trùng mã môn học
        if (repository.exists(subjectID)) {
            System.out.println("Lỗi: Mã môn học '" + subjectID + "' đã tồn tại!");
            return false;
        }

        // Kiểm tra trùng tên môn học (không phân biệt hoa/thường)
        if (isSubjectNameExists(subjectName)) {
            System.out.println("Lỗi: Tên môn học '" + subjectName + "' đã tồn tại!");
            return false;
        }

        // Nếu hợp lệ thì thêm
        Subject subject = new Subject(subjectID, subjectName, lessonCount, confficient, subjectType, description, teacherInCharge, status);
        if (repository.add(subject)) {
            System.out.println("✓ Thêm môn học thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể thêm môn học!");
            return false;
        }
    }

    /**
     * Kiểm tra trùng tên môn học (không phân biệt hoa/thường)
     */
    public boolean isSubjectNameExists(String subjectName) {
        if (subjectName == null || subjectName.trim().isEmpty()) return false;
        String normalized = subjectName.trim().toLowerCase();

        List<Subject> subjects = repository.findAll();
        return subjects.stream()
                .anyMatch(s -> s.getSubjectName() != null &&
                        s.getSubjectName().trim().toLowerCase().equals(normalized));
    }
    public boolean assignTeachersToSubject(String subjectID, List<String> newTeacherIDs) {
        if (subjectID == null || subjectID.trim().isEmpty()) {
            System.out.println("Lỗi: Mã môn học không được để trống!");
            return false;
        }

        Optional<Subject> optSubject = repository.findById(subjectID);
        if (optSubject.isEmpty()) {
            System.out.println("Lỗi: Không tìm thấy môn học với mã '" + subjectID + "'!");
            return false;
        }

        Subject subject = optSubject.get();

        // Lấy danh sách cũ
        List<String> currentTeachers = subject.getTeachersInCharge();
        if (currentTeachers == null) currentTeachers = new ArrayList<>();

        // Gộp danh sách mới, tránh trùng lặp
        for (String id : newTeacherIDs) {
            if (!currentTeachers.contains(id)) {
                currentTeachers.add(id);
            }
        }

        // Cập nhật danh sách
        subject.setTeachersInCharge(currentTeachers);

        // Lưu vào file
        boolean updated = repository.update(subject);
        if (updated) {
            System.out.println("✓ Gán giáo viên thành công cho môn học " + subject.getSubjectName());
            System.out.println("Danh sách giáo viên hiện tại: " + String.join(", ", subject.getTeachersInCharge()));
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật môn học!");
            return false;
        }
    }



    public boolean updateSubject(Subject subject) {
        if (subject == null) {
            System.out.println("Lỗi: Subject không được null!");
            return false;
        }

        if (!repository.exists(subject.getSubjectID())) {
            System.out.println("Lỗi: Không tìm thấy môn học với mã '" + subject.getSubjectID() + "'!");
            return false;
        }

        if (repository.update(subject)) {
            System.out.println("✓ Cập nhật môn học thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật môn học!");
            return false;
        }
    }

    public boolean deleteSubject(String subjectID) {
        if (subjectID == null || subjectID.trim().isEmpty()) {
            System.out.println("Lỗi: Mã môn học không được để trống!");
            return false;
        }

        if (!repository.exists(subjectID)) {
            System.out.println("Lỗi: Không tìm thấy môn học với mã '" + subjectID + "'!");
            return false;
        }

        if (repository.delete(subjectID)) {
            System.out.println("✓ Xóa môn học thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể xóa môn học!");
            return false;
        }
    }

    public Optional<Subject> findById(String subjectID) {
        return repository.findById(subjectID);
    }

    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    public List<Subject> searchSubjects(String keyword) {
        return repository.search(keyword);
    }

    public boolean isSubjectIdExists(String subjectID) {
        return repository.exists(subjectID);
    }

    public int getTotalSubjects() {
        return repository.count();
    }

    public void displayAllSubjects() {
        List<Subject> subjects = getAllSubjects();

        if (subjects.isEmpty()) {
            System.out.println("\nKhông có môn học nào trong hệ thống.");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                         DANH SÁCH MÔN HỌC                                                     │");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-10s %-10s %-15s %-20s %-30s %-10s │%n",
                "Mã MH", "Tên MH", "Số tiết", "Hệ số", "Loại môn", "Mô tả", "Giáo viên", "Trạng thái");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");

        for (Subject s : subjects) {
            String teachers = String.join(", ", s.getTeachersInCharge()); // nối danh sách giáo viên
            System.out.printf("│ %-10s %-25s %-10d %-10.2f %-15s %-20s %-30s %-10s │%n",
                    truncate(s.getSubjectID(), 10),
                    truncate(s.getSubjectName(), 25),
                    s.getLessonCount(),
                    s.getConfficient(),
                    truncate(s.getSubjectType(), 15),
                    truncate(s.getDescription(), 20),
                    truncate(teachers, 30),
                    truncate(s.getStatus(), 10));
        }

        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tổng số môn học: " + subjects.size());
    }

    public void displaySearchResults(String keyword) {
        List<Subject> results = searchSubjects(keyword);

        if (results.isEmpty()) {
            System.out.println("\nKhông tìm thấy môn học nào phù hợp với từ khóa: '" + keyword + "'");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────────────────────────────────     ────────┐");
        System.out.println("│                                     KẾT QUẢ TÌM KIẾM MÔN HỌC                                                       │");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────     ────────┤");
        System.out.printf("│ %-10s %-25s %-10s %-10s %-15s %-20s %-20s %-10s │%n",
                "Mã MH", "Tên MH", "Số tiết", "Hệ số", "Loại môn", "Mô tả", "Giáo viên", "Trạng thái");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────     ────────────┤");

        for (Subject s : results) {
            String teachers = String.join(", ", s.getTeachersInCharge());
            System.out.printf("│ %-10s %-25s %-10d %-10.2f %-15s %-20s %-20s %-10s │%n",
                    truncate(s.getSubjectID(), 10),
                    truncate(s.getSubjectName(), 25),
                    s.getLessonCount(),
                    s.getConfficient(),
                    truncate(s.getSubjectType(), 15),
                    truncate(s.getDescription(), 20),
                    truncate(teachers, 20),
                    truncate(s.getStatus(), 10)
            );
        }

        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("Tìm thấy: " + results.size() + " môn học");
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "..."; // cắt và thêm "..."
    }

}
