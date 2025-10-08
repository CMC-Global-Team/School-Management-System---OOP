package Services;

import Models.Grade;

import java.util.ArrayList;
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
        List<Grade> studentGrades = new ArrayList<>();
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
        List<Grade> subjectGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if(grade.getSubjectId().equalsIgnoreCase(subjectID)) {
                subjectGrades.add(grade);
            }
        }
        return subjectGrades;
    }

    /**
     * Lấy tất cả điểm 1 môn học theo 1 học kỳ của 1 học sinh
     */
    public List<Grade> getAllGradeBySemester(int semester, String subjectID, String studentID) {
        List<Grade> grades = getAllGradeBySubjectID(subjectID, studentID);
        List<Grade> semesterGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if(grade.getSemester() == semester) {
                semesterGrades.add(grade);
            }
        }
        return semesterGrades;
    }

    /**
     * Kiểm tra xem loại điểm liệu đã tồn tại trong học kỳ chưa
     */
    public boolean isExistGradeType(int semester,String subjectID, String studentID, String type) {
        List<Grade> grades = getAllGradeBySemester(semester, subjectID, studentID);
        for (Grade grade : grades) {
            if(grade.getGradeType().equalsIgnoreCase(type) &&  grade.getSemester() == semester) {
                return true;
            }
        }
        return false;
    }
}
