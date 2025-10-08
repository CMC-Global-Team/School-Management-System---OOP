package Services;

import Models.Grade;
import Utils.InputUtil;

import java.time.LocalDate;
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
     * Thêm điểm mới
     */
    public boolean addGrade(String gradeID, String studentID, String subjectID, int gradeType) {

        // Validate input
        if (StudentService.getInstance().isStudentIdExists(studentID)) {
            System.out.println("Không tìm thấy học sinh có mã: " + studentID);
            return false;
        }
        if (SubjectService.getInstance().isSubjectIdExists(subjectID)) {
            System.out.println("Không tìm thấy môn học có mã: " + subjectID);
            return false;
        }
        if (isGradeIDExists(gradeID)) {
            System.out.println("Mã điểm " + gradeID + " đã tồn tại!");
            return false;
        }
        String type;
        switch (gradeType) {
            case 1:
                type = "thuong xuyen";
                break;
            case 2:
                type = "giua ky";
                break;
            case 3:
                type = "cuoi ky";
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return false;
        }

        double score = -1;
        while(score < 0 || score > 10) {
            score = InputUtil.getDouble("Điểm số(0-10): ");
        }
        int gradeSemester = InputUtil.getInt("Học kỳ: ");
        if(isExistGradeType(gradeSemester,subjectID, studentID, type)) {
            System.out.println("Loại điểm này đã tồn tại!");
            return false;
        }
        System.out.println("Năm học: ");
        String gradeSchoolYear = schoolYearInput();
        LocalDate inputDate =  LocalDate.now();
        String gradeNote = InputUtil.getString("Ghi chú: ");

        Grade grade = new Grade(gradeID, studentID, subjectID, type, score, gradeSemester, gradeSchoolYear, inputDate, gradeNote);
        System.out.println(grade.toString());
        if(!InputUtil.getBoolean("Bạn chắc chắn muốn lưu điểm này?")){
            return false;
        }
        // Thêm vào repository
        if (repository.add(grade)) {
            System.out.println("✓ Thêm điểm thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể thêm điểm!");
            return false;
        }
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
