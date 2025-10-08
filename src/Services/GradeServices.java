package Services;

import Models.Grade;
import Utils.InputUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeServices {
    private static GradeServices instance;
    private final GradeRepository repository;

    private GradeServices() {
        this.repository = new GradeRepository();
    }

    /**
     * Lấy instance duy nhất của GradeServices (Singleton)
     */
    public static GradeServices getInstance() {
        if (instance == null) {
            instance = new GradeServices();
        }
        return instance;
    }
    
    /**
     * Kiểm tra mã điểm đã tồn tại chưa
     */
    public boolean isGradeIdExists(String gradeId) {
        return repository.exists(gradeId);
    }

    /**
     * Thêm điểm mới với đầy đủ tham số
     */
    public boolean addGrade(String gradeID, String studentID, String subjectID, String gradeType, 
                           double score, int semester, String schoolYear, LocalDate inputDate, String note) {
        
        // Validate input
        if (gradeID == null || gradeID.trim().isEmpty()) {
            System.out.println("Mã điểm không được để trống!");
            return false;
        }
        
        if (!gradeID.matches("^D\\d{4}$")) {
            System.out.println("Mã điểm sai định dạng! (VD: D0001)");
            return false;
        }
        
        if (repository.exists(gradeID)) {
            System.out.println("Mã điểm '" + gradeID + "' đã tồn tại trong hệ thống!");
            return false;
        }
        
        if (!StudentService.getInstance().isStudentIdExists(studentID)) {
            System.out.println("Không tìm thấy học sinh có mã '" + studentID + "' trong hệ thống!");
            return false;
        }
        
        if (!SubjectService.getInstance().isSubjectIdExists(subjectID)) {
            System.out.println("Không tìm thấy môn học có mã '" + subjectID + "' trong hệ thống!");
            return false;
        }
        
        if (score < 0 || score > 10) {
            System.out.println("Điểm số phải trong khoảng 0.0 - 10.0!");
            return false;
        }
        
        if (semester < 1 || semester > 2) {
            System.out.println("Học kỳ phải là 1 hoặc 2!");
            return false;
        }
        
        if (schoolYear == null || schoolYear.trim().isEmpty() || !schoolYear.matches("^\\d{4}-\\d{4}$")) {
            System.out.println("Năm học sai định dạng! (VD: 2024-2025)");
            return false;
        }
        
        // Kiểm tra năm học hợp lệ
        String[] parts = schoolYear.split("-");
        int startYear = Integer.parseInt(parts[0]);
        int endYear = Integer.parseInt(parts[1]);
        if (startYear > endYear) {
            System.out.println("Năm học không hợp lệ! Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc");
            return false;
        }
        
        if (inputDate == null) {
            System.out.println("Ngày nhập không được để trống!");
            return false;
        }
        
        // Kiểm tra trùng loại điểm
        if (isExistGradeType(semester, subjectID, studentID, gradeType)) {
            System.out.println("Loại điểm '" + gradeType + "' đã tồn tại cho học sinh này trong học kỳ " + semester + "!");
            return false;
        }
        
        Grade grade = new Grade(gradeID, studentID, subjectID, gradeType, score, semester, schoolYear, inputDate, note);
        
        if (repository.add(grade)) {
            System.out.println("Thêm điểm thành công!");
            return true;
        } else {
            System.out.println("Thêm điểm thất bại!");
            return false;
        }
    }

    /**
     * Thêm điểm mới (phương thức cũ để tương thích)
     */
    public boolean addGrade(String gradeID, String studentID, String subjectID, int gradeType) {

        // Validate input
        if (!StudentService.getInstance().isStudentIdExists(studentID)) {
            System.out.println("Không tìm thấy học sinh có mã: " + studentID);
            return false;
        }
        if (!SubjectService.getInstance().isSubjectIdExists(subjectID)) {
            System.out.println("Không tìm thấy môn học có mã: " + subjectID);
            return false;
        }
        if (repository.exists(gradeID)) {
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
     * Cập nhật điểm
     */
    public boolean updateGrade(Grade Grade) {
        if (Grade == null) {
            System.out.println("Lỗi: Grade không được null!");
            return false;
        }

        if (!repository.exists(Grade.getGradeId())) {
            System.out.println("Lỗi: Không tìm thấy điểm với mã '" + Grade.getGradeId() + "'!");
            return false;
        }

        if (repository.update(Grade)) {
            System.out.println("✓ Cập nhật điểm thành công!");
            return true;
        } else {
            System.out.println("Lỗi: Không thể cập nhật điểm!");
            return false;
        }
    }

    /**
     * Xóa điểm theo ID
     */
    public boolean deleteGrade(String GradeID) {
        if (GradeID == null || GradeID.trim().isEmpty()) {
            System.out.println("Lỗi: Mã lớp không được để trống!");
            return false;
        }

        if (!repository.exists(GradeID)) {
            System.out.println("Lỗi: Không tìm thấy lớp học với mã '" + GradeID + "'!");
            return false;
        }

        if (repository.delete(GradeID)) {
            return true;
        } else {
            System.out.println("Lỗi: Không thể xóa lớp học!");
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

    /**
     * Nhập Năm học
     */
    public static String schoolYearInput(){
        int start = InputUtil.getInt("Năm bắt đầu: ");
        int end = InputUtil.getInt("Năm kết thúc: ");
        return start + " - " + end;
    }

    /**
     * Tìm điểm theo ID
     */
    public Optional<Grade> findById(String GradeID) {
        return repository.findById(GradeID);
    }

    /**
     * Đếm tổng số bản ghi
     */
    public int getTotalGradeRecords() {
        return repository.count();
    }

    public List<Grade> searchGrade(String keyword) {
        return repository.search(keyword);
    }

    /**
     * Hiển thị kết quả tìm kiếm
     */
    public void displaySearchResults(String keyword) {
        List<Grade> results = searchGrade(keyword);

        if (results.isEmpty()) {
            System.out.println("\nKhông tìm thấy lớp học nào phù hợp với từ khóa: '" + keyword + "'");
            return;
        }

        System.out.println("\n┌───────────────────────────────────────────────────────────────────┐");
        System.out.println("│                      KẾT QUẢ TÌM KIẾM                              │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s %-25s %-15s %-15s │%n", "Mã điểm", "Mã HS", "Mã môn", "Niên Khóa");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");

        for (Grade Grade : results) {
            System.out.printf("│ %-10s %-25s %-15s %-15s │%n",
                    truncate(Grade.getGradeId(), 10),
                    truncate(Grade.getStudentId(), 25),
                    truncate(Grade.getSubjectId(), 15),
                    truncate(Grade.getSchoolYear(), 20)
            );
        }

        System.out.println("└─────────────────────────────────────────────────────────────────────┘");
    }

    /**
     * Helper method để cắt chuỗi cho vừa với độ rộng
     */
    private String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    public void AverageScore(String studentID){
        List<Grade> gradesStudent = getAllGradeByStudentID(studentID);
        if (gradesStudent.isEmpty()) {
            System.out.println("Không có điểm nào cho học sinh: " + studentID);
            return;
        }
        
        double totalWeightedGrade = 0;
        double totalCoefficient = 0;
        
        // Lấy danh sách các môn học duy nhất
        List<String> uniqueSubjects = gradesStudent.stream()
                .map(Grade::getSubjectId)
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           ĐIỂM TRUNG BÌNH TỪNG MÔN                        │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s │ %-12s │ %-12s │ %-12s │ %-12s │%n", 
            "Môn học", "Thường xuyên", "Giữa kỳ", "Cuối kỳ", "TB môn");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        for (String subjectId : uniqueSubjects) {
            List<Grade> subjectGrades = getAllGradeBySubjectID(subjectId, studentID);
            
            double txScore = 0, gkScore = 0, ckScore = 0;
            boolean hasTx = false, hasGk = false, hasCk = false;
            
            for (Grade grade : subjectGrades) {
                switch (grade.getGradeType().toLowerCase()) {
                    case "thuong xuyen":
                        txScore = grade.getScore();
                        hasTx = true;
                        break;
                    case "giua ky":
                        gkScore = grade.getScore();
                        hasGk = true;
                        break;
                    case "cuoi ky":
                        ckScore = grade.getScore();
                        hasCk = true;
                        break;
                }
            }
            
            // Tính điểm trung bình môn
            double subjectAverage = 0;
            if (hasTx && hasGk && hasCk) {
                subjectAverage = (txScore * 0.2 + gkScore * 0.3 + ckScore * 0.5);
            } else if (hasTx && hasCk) {
                subjectAverage = (txScore * 0.3 + ckScore * 0.7);
            } else if (hasGk && hasCk) {
                subjectAverage = (gkScore * 0.4 + ckScore * 0.6);
            } else if (hasCk) {
                subjectAverage = ckScore;
            } else {
                System.out.printf("│ %-15s │ %-12s │ %-12s │ %-12s │ %-12s │%n", 
                    subjectId, "Chưa có", "Chưa có", "Chưa có", "Chưa đủ");
                continue;
            }
            
            System.out.printf("│ %-15s │ %-12.1f │ %-12.1f │ %-12.1f │ %-12.2f │%n", 
                subjectId, txScore, gkScore, ckScore, subjectAverage);
            
            // Lấy hệ số môn học
            var subjectOpt = SubjectService.getInstance().findById(subjectId);
            if (subjectOpt.isPresent()) {
                double coefficient = subjectOpt.get().getConfficient();
                totalWeightedGrade += subjectAverage * coefficient;
                totalCoefficient += coefficient;
            }
        }
        
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
        
        if (totalCoefficient > 0) {
            double finalAverage = totalWeightedGrade / totalCoefficient;
            System.out.printf("\nĐiểm trung bình tổng kết: %.2f%n", finalAverage);
            
            // Xếp loại học lực
            String classification = getClassification(finalAverage);
            System.out.println("Xếp loại học lực: " + classification);
        } else {
            System.out.println("Không thể tính điểm trung bình do thiếu hệ số môn học.");
        }
    }

    public double DAverageScore(String studentID){
        List<Grade> gradesStudent = getAllGradeByStudentID(studentID);
        if (gradesStudent.isEmpty()) {
            return 0.0;
        }
        
        double totalWeightedGrade = 0;
        double totalCoefficient = 0;
        
        // Lấy danh sách các môn học duy nhất
        List<String> uniqueSubjects = gradesStudent.stream()
                .map(Grade::getSubjectId)
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        
        for (String subjectId : uniqueSubjects) {
            List<Grade> subjectGrades = getAllGradeBySubjectID(subjectId, studentID);
            
            double txScore = 0, gkScore = 0, ckScore = 0;
            boolean hasTx = false, hasGk = false, hasCk = false;
            
            for (Grade grade : subjectGrades) {
                switch (grade.getGradeType().toLowerCase()) {
                    case "thuong xuyen":
                        txScore = grade.getScore();
                        hasTx = true;
                        break;
                    case "giua ky":
                        gkScore = grade.getScore();
                        hasGk = true;
                        break;
                    case "cuoi ky":
                        ckScore = grade.getScore();
                        hasCk = true;
                        break;
                }
            }
            
            // Tính điểm trung bình môn
            double subjectAverage = 0;
            if (hasTx && hasGk && hasCk) {
                subjectAverage = (txScore * 0.2 + gkScore * 0.3 + ckScore * 0.5);
            } else if (hasTx && hasCk) {
                subjectAverage = (txScore * 0.3 + ckScore * 0.7);
            } else if (hasGk && hasCk) {
                subjectAverage = (gkScore * 0.4 + ckScore * 0.6);
            } else if (hasCk) {
                subjectAverage = ckScore;
            } else {
                continue; // Bỏ qua môn chưa đủ điểm
            }
            
            // Lấy hệ số môn học
            var subjectOpt = SubjectService.getInstance().findById(subjectId);
            if (subjectOpt.isPresent()) {
                double coefficient = subjectOpt.get().getConfficient();
                totalWeightedGrade += subjectAverage * coefficient;
                totalCoefficient += coefficient;
            }
        }
        
        return totalCoefficient > 0 ? totalWeightedGrade / totalCoefficient : 0.0;
    }
    
    /**
     * Xếp loại học lực dựa trên điểm trung bình
     */
    public String getClassification(double averageScore) {
        if (averageScore >= 9.0) {
            return "Xuất sắc";
        } else if (averageScore >= 8.0) {
            return "Giỏi";
        } else if (averageScore >= 6.5) {
            return "Khá";
        } else if (averageScore >= 5.0) {
            return "Trung bình";
        } else if (averageScore >= 3.5) {
            return "Yếu";
        } else {
            return "Kém";
        }
    }

}
