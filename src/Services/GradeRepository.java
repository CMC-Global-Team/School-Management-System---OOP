package Services;

import Models.Grade;

public class GradeRepository extends BaseFileRepository<Grade> {
    public GradeRepository() {
        super(FileManager.GRADE_FILE);
        FileManager.ensureFileExists(FileManager.GRADE_FILE);
    }
    @Override
    protected Grade parseFromString(String line) {
        return Grade.fromString(line);
    }

    @Override
    protected boolean matchesKeyword(Grade grade, String keyword) {
        if (grade == null || keyword == null) {
            return false;
        }

        String lowerKeyword = keyword.toLowerCase();

        // Tìm kiếm theo mã điểm
        if (grade.getGradeId() != null &&
                grade.getGradeId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo mã môn học
        if (grade.getSubjectId() != null &&
                grade.getSubjectId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo mã học sinh
        if (grade.getStudentId() != null &&
                grade.getStudentId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo loại điểm
        if (grade.getGradeType() != null &&
                grade.getGradeType().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        return false;
    }
}
