package Services;


import Models.Subject;


public class SubjectRepository extends BaseFileRepository<Subject> {
    public SubjectRepository() {
        super(FileManager.SUBJECT_FILE);
        // Đảm bảo file tồn tại
        FileManager.ensureFileExists(FileManager.SUBJECT_FILE);
    }


    @Override
    protected Subject parseFromString(String line) {
        return Subject.fromString(line);
    }


    @Override
    protected boolean matchesKeyword(Subject subject, String keyword) {
        if (subject == null || keyword == null) {
            return false;
        }


        String lowerKeyword = keyword.toLowerCase();


        // Tìm kiếm theo ID
        if (subject.getSubjectID() != null &&
                subject.getSubjectID().toLowerCase().contains(lowerKeyword)) {
            return true;
        }


        // Tìm kiếm theo tên môn học
        if (subject.getSubjectName() != null &&
                subject.getSubjectName().toLowerCase().contains(lowerKeyword)) {
            return true;
        }


        // Tìm kiếm theo loại môn
        if (subject.getSubjectType() != null &&
                subject.getSubjectType().toLowerCase().contains(lowerKeyword)) {
            return true;
        }



        if (subject.getTeachersInCharge() != null) {
            for (String teacherID : subject.getTeachersInCharge()) {
                if (teacherID.toLowerCase().contains(lowerKeyword)) {
                    return true;
                }
            }
        }

        return false;
    }}
