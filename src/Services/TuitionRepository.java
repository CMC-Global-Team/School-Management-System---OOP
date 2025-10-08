package Services;

import Models.Tuition;

/**
 * TuitionRepository - Repository cụ thể cho Tuition entity
 * Demo việc TÁI SỬ DỤNG BaseFileRepository cho entity khác
 */
public class TuitionRepository extends BaseFileRepository<Tuition> {

    public TuitionRepository() {
        super(FileManager.TUITION_FILE);
        // Đảm bảo file tồn tại
        FileManager.ensureFileExists(FileManager.TUITION_FILE);
    }

    @Override
    protected Tuition parseFromString(String line) {
        return Tuition.fromString(line);
    }

    @Override
    protected boolean matchesKeyword(Tuition tuition, String keyword) {
        if (tuition == null || keyword == null) {
            return false;
        }

        String lowerKeyword = keyword.toLowerCase();

        // Tìm kiếm theo ID học phí
        if (tuition.getTuitionId() != null &&
                tuition.getTuitionId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo ID sinh viên
        if (tuition.getStudentId() != null &&
                tuition.getStudentId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo năm học
        if (tuition.getSchoolYear() != null &&
                tuition.getSchoolYear().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo trạng thái
        if (tuition.getStatus() != null &&
                tuition.getStatus().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo phương thức thanh toán
        if (tuition.getMethod() != null &&
                tuition.getMethod().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo ghi chú
        if (tuition.getNote() != null &&
                tuition.getNote().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        return false;
    }
}