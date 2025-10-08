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

    /**
     * Lấy tất cả điểm theo mã học sinh
     */
    public List<Grade> getAllGradeByStudentID(String studentID) {
        List<Grade> grades = getAllGrade();
        List<Grade> studentGrades = getAllGrade();
        for (Grade grade : grades) {
            if(grade.getStudentId().equalsIgnoreCase(studentID)) {
                studentGrades.add(grade);
            }
        }
        return studentGrades;
    }

    /**
     * Lấy tất cả điểm theo mã môn học
     */
    public List<Grade> getAllGradeBySubjectID(String subjectID, String studentID) {
        List<Grade> grades = getAllGradeByStudentID(studentID);
        List<Grade> subjectGrades = getAllGrade();
        for (Grade grade : grades) {
            if(grade.getSubjectId().equalsIgnoreCase(subjectID)) {
                subjectGrades.add(grade);
            }
        }
        return subjectGrades;
    }
}
