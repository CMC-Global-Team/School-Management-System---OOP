package Screen.Grade;

import Models.Grade;
import Models.Student;
import Models.Subject;
import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
import Services.SubjectService;
import Utils.InputUtil;
import java.util.List;
import java.util.stream.Collectors;

public class ClassGradeReportScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Báo Cáo Điểm Lớp               │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nChọn loại báo cáo:");
        System.out.println("1. Báo cáo điểm theo lớp");
        System.out.println("2. Báo cáo điểm theo môn học");
        System.out.println("3. Báo cáo tổng hợp lớp");
        System.out.println("4. Báo cáo học sinh có điểm cao nhất/thấp nhất");
        
        int choice = InputUtil.getInt("Lựa chọn (1-4): ");
        
        switch (choice) {
            case 1:
                reportByClass();
                break;
            case 2:
                reportBySubject();
                break;
            case 3:
                comprehensiveClassReport();
                break;
            case 4:
                topBottomStudentsReport();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
        pause();
    }
    
    private void reportByClass() {
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        int semester = InputUtil.getInt("Nhập học kỳ (1 hoặc 2): ");
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
        
        if (classStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            return;
        }
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    BÁO CÁO ĐIỂM LỚP " + className.toUpperCase() + " - HỌC KỲ " + semester + "                    │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s │ %-25s │ %-8s │ %-15s │%n", 
            "Mã HS", "Tên học sinh", "Điểm TB", "Xếp loại");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        for (Student student : classStudents) {
            double averageScore = calculateSemesterAverage(student.getId(), semester);
            String classification = GradeServices.getInstance().getClassification(averageScore);
            
            System.out.printf("│ %-10s │ %-25s │ %-8.2f │ %-15s │%n",
                student.getId(),
                student.getName(),
                averageScore,
                classification
            );
        }
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    private void reportBySubject() {
        String subjectId = InputUtil.getNonEmptyString("Nhập mã môn học: ");
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        int semester = InputUtil.getInt("Nhập học kỳ (1 hoặc 2): ");
        
        var subjectOpt = SubjectService.getInstance().findById(subjectId);
        if (!subjectOpt.isPresent()) {
            System.out.println("Không tìm thấy môn học: " + subjectId);
            return;
        }
        
        Subject subject = subjectOpt.get();
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    BÁO CÁO ĐIỂM MÔN " + subject.getSubjectName().toUpperCase() + "                    │");
        System.out.println("│                           LỚP " + className.toUpperCase() + " - HỌC KỲ " + semester + "                           │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s │ %-25s │ %-12s │ %-12s │ %-12s │ %-8s │%n", 
            "Mã HS", "Tên học sinh", "Thường xuyên", "Giữa kỳ", "Cuối kỳ", "TB môn");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        for (Student student : classStudents) {
            List<Grade> subjectGrades = GradeServices.getInstance().getAllGradeBySubjectID(subjectId, student.getId());
            List<Grade> semesterGrades = subjectGrades.stream()
                    .filter(g -> g.getSemester() == semester)
                    .collect(Collectors.toList());
            
            double txScore = 0, gkScore = 0, ckScore = 0;
            boolean hasTx = false, hasGk = false, hasCk = false;
            
            for (Grade grade : semesterGrades) {
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
            
            double subjectAverage = 0;
            if (hasTx && hasGk && hasCk) {
                subjectAverage = (txScore * 0.2 + gkScore * 0.3 + ckScore * 0.5);
            } else if (hasTx && hasCk) {
                subjectAverage = (txScore * 0.3 + ckScore * 0.7);
            } else if (hasGk && hasCk) {
                subjectAverage = (gkScore * 0.4 + ckScore * 0.6);
            } else if (hasCk) {
                subjectAverage = ckScore;
            }
            
            System.out.printf("│ %-10s │ %-25s │ %-12.1f │ %-12.1f │ %-12.1f │ %-8.2f │%n",
                student.getId(),
                student.getName(),
                txScore,
                gkScore,
                ckScore,
                subjectAverage
            );
        }
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    private void comprehensiveClassReport() {
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        int semester = InputUtil.getInt("Nhập học kỳ (1 hoặc 2): ");
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
        
        if (classStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            return;
        }
        
        // Thống kê tổng quan
        int totalStudents = classStudents.size();
        int xuatSac = 0, gioi = 0, kha = 0, trungBinh = 0, yeu = 0, kem = 0;
        double totalAverage = 0;
        
        for (Student student : classStudents) {
            double averageScore = calculateSemesterAverage(student.getId(), semester);
            totalAverage += averageScore;
            
            String classification = GradeServices.getInstance().getClassification(averageScore);
            switch (classification) {
                case "Xuất sắc": xuatSac++; break;
                case "Giỏi": gioi++; break;
                case "Khá": kha++; break;
                case "Trung bình": trungBinh++; break;
                case "Yếu": yeu++; break;
                case "Kém": kem++; break;
            }
        }
        
        double classAverage = totalAverage / totalStudents;
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    BÁO CÁO TỔNG HỢP LỚP " + className.toUpperCase() + "                    │");
        System.out.println("│                              HỌC KỲ " + semester + "                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Tổng số học sinh: %-8d │ Điểm TB lớp: %-8.2f │%n", totalStudents, classAverage);
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s │ %-8s │ %-10s │%n", "Xếp loại", "Số lượng", "Tỷ lệ (%)");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Xuất sắc", xuatSac, (xuatSac * 100.0 / totalStudents));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Giỏi", gioi, (gioi * 100.0 / totalStudents));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Khá", kha, (kha * 100.0 / totalStudents));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Trung bình", trungBinh, (trungBinh * 100.0 / totalStudents));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Yếu", yeu, (yeu * 100.0 / totalStudents));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Kém", kem, (kem * 100.0 / totalStudents));
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    private void topBottomStudentsReport() {
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        int semester = InputUtil.getInt("Nhập học kỳ (1 hoặc 2): ");
        int topCount = InputUtil.getInt("Số học sinh top (mặc định 5): ");
        if (topCount <= 0) topCount = 5;
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
        
        if (classStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            return;
        }
        
        // Sắp xếp theo điểm trung bình
        classStudents.sort((s1, s2) -> {
            double avg1 = calculateSemesterAverage(s1.getId(), semester);
            double avg2 = calculateSemesterAverage(s2.getId(), semester);
            return Double.compare(avg2, avg1); // Giảm dần
        });
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    TOP " + topCount + " HỌC SINH XUẤT SẮC NHẤT                    │");
        System.out.println("│                           LỚP " + className.toUpperCase() + " - HỌC KỲ " + semester + "                           │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-5s │ %-10s │ %-25s │ %-8s │ %-15s │%n", 
            "Hạng", "Mã HS", "Tên học sinh", "Điểm TB", "Xếp loại");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        for (int i = 0; i < Math.min(topCount, classStudents.size()); i++) {
            Student student = classStudents.get(i);
            double averageScore = calculateSemesterAverage(student.getId(), semester);
            String classification = GradeServices.getInstance().getClassification(averageScore);
            
            System.out.printf("│ %-5d │ %-10s │ %-25s │ %-8.2f │ %-15s │%n",
                i + 1,
                student.getId(),
                student.getName(),
                averageScore,
                classification
            );
        }
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    private double calculateSemesterAverage(String studentId, int semester) {
        List<Grade> studentGrades = GradeServices.getInstance().getAllGradeByStudentID(studentId);
        List<Grade> semesterGrades = studentGrades.stream()
                .filter(g -> g.getSemester() == semester)
                .collect(Collectors.toList());
        
        if (semesterGrades.isEmpty()) {
            return 0.0;
        }
        
        double totalWeightedGrade = 0;
        double totalCoefficient = 0;
        
        List<String> uniqueSubjects = semesterGrades.stream()
                .map(Grade::getSubjectId)
                .distinct()
                .collect(Collectors.toList());
        
        for (String subjectId : uniqueSubjects) {
            List<Grade> subjectGrades = semesterGrades.stream()
                    .filter(g -> g.getSubjectId().equals(subjectId))
                    .collect(Collectors.toList());
            
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
                continue;
            }
            
            var subjectOpt = SubjectService.getInstance().findById(subjectId);
            if (subjectOpt.isPresent()) {
                double coefficient = subjectOpt.get().getConfficient();
                totalWeightedGrade += subjectAverage * coefficient;
                totalCoefficient += coefficient;
            }
        }
        
        return totalCoefficient > 0 ? totalWeightedGrade / totalCoefficient : 0.0;
    }
}
