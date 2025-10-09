package Screen.Grade;

import Models.Grade;
import Models.Student;
import Models.Subject;
import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
import Services.SubjectService;
import Utils.FileUtil;
import Utils.InputUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ExportGradeListScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        Xuất Danh Sách Điểm              │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nChọn loại xuất:");
        System.out.println("1. Xuất điểm của một học sinh");
        System.out.println("2. Xuất điểm của cả lớp");
        System.out.println("3. Xuất điểm theo môn học");
        System.out.println("4. Xuất tất cả điểm");
        
        int choice = InputUtil.getInt("Lựa chọn (1-4): ");
        
        switch (choice) {
            case 1:
                exportStudentGrades();
                break;
            case 2:
                exportClassGrades();
                break;
            case 3:
                exportSubjectGrades();
                break;
            case 4:
                exportAllGrades();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
        pause();
    }
    
    private void exportStudentGrades() {
        String studentId = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
        
        if (!StudentService.getInstance().isStudentIdExists(studentId)) {
            System.out.println("Không tìm thấy học sinh: " + studentId);
            return;
        }
        
        Student student = StudentService.getInstance().findById(studentId).orElse(null);
        if (student == null) {
            System.out.println("Không thể lấy thông tin học sinh!");
            return;
        }
        
        List<Grade> grades = GradeServices.getInstance().getAllGradeByStudentID(studentId);
        if (grades.isEmpty()) {
            System.out.println("Không có điểm nào cho học sinh: " + studentId);
            return;
        }
        
        String fileName = "data/export_student_" + studentId + "_" + getCurrentTimestamp() + ".txt";
        
        try {
            List<String> lines = createStudentGradeReport(student, grades);
            FileUtil.writeLines(fileName, lines);
            System.out.println("✓ Xuất thành công file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi xuất file: " + e.getMessage());
        }
    }
    
    private void exportClassGrades() {
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        int semester = InputUtil.getInt("Nhập học kỳ (0 = tất cả): ");
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
        
        if (classStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            return;
        }
        
        String fileName = "data/export_class_" + className + "_" + getCurrentTimestamp() + ".txt";
        
        try {
            List<String> lines = createClassGradeReport(classStudents, semester);
            FileUtil.writeLines(fileName, lines);
            System.out.println("✓ Xuất thành công file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi xuất file: " + e.getMessage());
        }
    }
    
    private void exportSubjectGrades() {
        String subjectId = InputUtil.getNonEmptyString("Nhập mã môn học: ");
        String className = InputUtil.getNonEmptyString("Nhập tên lớp (Enter = tất cả lớp): ");
        
        var subjectOpt = SubjectService.getInstance().findById(subjectId);
        if (!subjectOpt.isPresent()) {
            System.out.println("Không tìm thấy môn học: " + subjectId);
            return;
        }
        
        Subject subject = subjectOpt.get();
        String fileName = "data/export_subject_" + subjectId + "_" + getCurrentTimestamp() + ".txt";
        
        try {
            List<String> lines = createSubjectGradeReport(subject, className);
            FileUtil.writeLines(fileName, lines);
            System.out.println("✓ Xuất thành công file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi xuất file: " + e.getMessage());
        }
    }
    
    private void exportAllGrades() {
        List<Grade> allGrades = GradeServices.getInstance().getAllGrade();
        if (allGrades.isEmpty()) {
            System.out.println("Không có điểm nào trong hệ thống!");
            return;
        }
        
        String fileName = "data/export_all_grades_" + getCurrentTimestamp() + ".txt";
        
        try {
            List<String> lines = createAllGradesReport(allGrades);
            FileUtil.writeLines(fileName, lines);
            System.out.println("✓ Xuất thành công file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi xuất file: " + e.getMessage());
        }
    }
    
    private List<String> createStudentGradeReport(Student student, List<Grade> grades) {
        List<String> lines = new java.util.ArrayList<>();
        
        lines.add("==========================================");
        lines.add("        BÁO CÁO ĐIỂM HỌC SINH");
        lines.add("==========================================");
        lines.add("Mã học sinh: " + student.getId());
        lines.add("Tên học sinh: " + student.getName());
        lines.add("Lớp: " + student.getClassName());
        lines.add("Ngày xuất: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lines.add("");
        lines.add("DANH SÁCH ĐIỂM:");
        lines.add("Mã điểm | Mã môn | Loại điểm    | Điểm | Kỳ | Năm học    | Ngày nhập");
        lines.add("--------|--------|--------------|------|----|------------|----------");
        
        for (Grade grade : grades) {
            lines.add(String.format("%-7s | %-6s | %-12s | %-4.1f | %-2d | %-10s | %s",
                grade.getGradeId(),
                grade.getSubjectId(),
                grade.getGradeType(),
                grade.getScore(),
                grade.getSemester(),
                grade.getSchoolYear(),
                grade.getInputDate()
            ));
        }
        
        lines.add("");
        lines.add("TỔNG KẾT:");
        double averageScore = GradeServices.getInstance().DAverageScore(student.getId());
        String classification = GradeServices.getInstance().getClassification(averageScore);
        lines.add("Điểm trung bình: " + String.format("%.2f", averageScore));
        lines.add("Xếp loại học lực: " + classification);
        
        return lines;
    }
    
    private List<String> createClassGradeReport(List<Student> students, int semester) {
        List<String> lines = new java.util.ArrayList<>();
        
        lines.add("==========================================");
        lines.add("        BÁO CÁO ĐIỂM LỚP");
        lines.add("==========================================");
        lines.add("Lớp: " + students.get(0).getClassName());
        lines.add("Số học sinh: " + students.size());
        lines.add("Ngày xuất: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lines.add("");
        lines.add("DANH SÁCH ĐIỂM HỌC SINH:");
        lines.add("Mã HS    | Tên học sinh           | Điểm TB | Xếp loại");
        lines.add("---------|-------------------------|---------|----------");
        
        for (Student student : students) {
            double averageScore = GradeServices.getInstance().DAverageScore(student.getId());
            String classification = GradeServices.getInstance().getClassification(averageScore);
            
            lines.add(String.format("%-8s | %-23s | %-7.2f | %s",
                student.getId(),
                student.getName(),
                averageScore,
                classification
            ));
        }
        
        return lines;
    }
    
    private List<String> createSubjectGradeReport(Subject subject, String className) {
        List<String> lines = new java.util.ArrayList<>();
        
        lines.add("==========================================");
        lines.add("        BÁO CÁO ĐIỂM MÔN HỌC");
        lines.add("==========================================");
        lines.add("Môn học: " + subject.getSubjectName());
        lines.add("Mã môn: " + subject.getSubjectID());
        lines.add("Hệ số: " + subject.getConfficient());
        lines.add("Ngày xuất: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lines.add("");
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        if (!className.isEmpty()) {
            students = students.stream()
                    .filter(s -> s.getClassName().equalsIgnoreCase(className))
                    .collect(Collectors.toList());
        }
        
        lines.add("DANH SÁCH ĐIỂM:");
        lines.add("Mã HS    | Tên học sinh           | TX  | GK  | CK  | TB môn");
        lines.add("---------|-------------------------|-----|-----|-----|--------");
        
        for (Student student : students) {
            List<Grade> subjectGrades = GradeServices.getInstance().getAllGradeBySubjectID(subject.getSubjectID(), student.getId());
            
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
            }
            
            lines.add(String.format("%-8s | %-23s | %-3.1f | %-3.1f | %-3.1f | %-6.2f",
                student.getId(),
                student.getName(),
                txScore,
                gkScore,
                ckScore,
                subjectAverage
            ));
        }
        
        return lines;
    }
    
    private List<String> createAllGradesReport(List<Grade> grades) {
        List<String> lines = new java.util.ArrayList<>();
        
        lines.add("==========================================");
        lines.add("        BÁO CÁO TẤT CẢ ĐIỂM");
        lines.add("==========================================");
        lines.add("Tổng số điểm: " + grades.size());
        lines.add("Ngày xuất: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        lines.add("");
        lines.add("DANH SÁCH ĐIỂM:");
        lines.add("Mã điểm | Mã HS | Mã môn | Loại điểm    | Điểm | Kỳ | Năm học    | Ngày nhập");
        lines.add("--------|-------|--------|--------------|------|----|------------|----------");
        
        for (Grade grade : grades) {
            lines.add(String.format("%-7s | %-5s | %-6s | %-12s | %-4.1f | %-2d | %-10s | %s",
                grade.getGradeId(),
                grade.getStudentId(),
                grade.getSubjectId(),
                grade.getGradeType(),
                grade.getScore(),
                grade.getSemester(),
                grade.getSchoolYear(),
                grade.getInputDate()
            ));
        }
        
        return lines;
    }
    
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }
}
