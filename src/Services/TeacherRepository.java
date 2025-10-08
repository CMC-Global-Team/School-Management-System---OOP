package Services;

import Models.Teacher;

public class TeacherRepository extends BaseFileRepository<Teacher> {
    public TeacherRepository() {
        super(FileManager.TEACHER_FILE);
        FileManager.ensureFileExists(FileManager.TEACHER_FILE);
    }

    @Override
    protected Teacher parseFromString(String line) {return Teacher.fromString(line);}

    @Override
    protected boolean matchesKeyword(Teacher teacher, String keyword) {
        if (teacher == null || keyword == null) {
            return false;
        }

        String lowerKeyword = keyword.toLowerCase();

        // Tìm kiếm theo ID
        if (teacher.getId() != null &&
                teacher.getId().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo tên
        if (teacher.getName() != null &&
                teacher.getName().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm môn giảng dạy
        if (teacher.getTeacherSubject() != null &&
                teacher.getTeacherSubject().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        // Tìm kiếm theo lớp chủ nhiệm
        if (teacher.getTeacherHomeroom() != null &&
                teacher.getTeacherHomeroom().toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        return false;
    }
}