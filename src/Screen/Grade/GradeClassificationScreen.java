package Screen.Grade;

import Models.Student;
import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
import Utils.InputUtil;
import java.util.List;

public class GradeClassificationScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Xếp loại học lực               │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nChọn cách xem xếp loại:");
        System.out.println("1. Xem xếp loại của một học sinh");
        System.out.println("2. Xem xếp loại của cả lớp");
        System.out.println("3. Thống kê xếp loại theo lớp");
        
        int choice = InputUtil.getInt("Lựa chọn (1-3): ");
        
        switch (choice) {
            case 1:
                classifySingleStudent();
                break;
            case 2:
                classifyClassStudents();
                break;
            case 3:
                statisticsByClass();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
        pause();
    }
    
    private void classifySingleStudent() {
        String studentID = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
        
        if (!StudentService.getInstance().isStudentIdExists(studentID)) {
            System.out.println("Không tìm thấy học sinh: " + studentID);
            return;
        }
        
        Student student = StudentService.getInstance().findById(studentID).orElse(null);
        if (student == null) {
            System.out.println("Không thể lấy thông tin học sinh!");
            return;
        }
        
        double averageScore = GradeServices.getInstance().DAverageScore(studentID);
        String classification = GradeServices.getInstance().getClassification(averageScore);
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           XẾP LOẠI HỌC LỰC                              │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Mã HS: %-10s │ Tên: %-30s │ Lớp: %-15s │%n", 
            student.getId(), student.getName(), student.getClassName());
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Điểm TB: %-8.2f │ Xếp loại: %-20s │%n", averageScore, classification);
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
    
    private void classifyClassStudents() {
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(java.util.stream.Collectors.toList());
        
        if (classStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            return;
        }
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    XẾP LOẠI HỌC LỰC LỚP " + className.toUpperCase() + "                    │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-10s │ %-25s │ %-8s │ %-15s │%n", 
            "Mã HS", "Tên học sinh", "Điểm TB", "Xếp loại");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        
        for (Student student : classStudents) {
            double averageScore = GradeServices.getInstance().DAverageScore(student.getId());
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
    
    private void statisticsByClass() {
        String className = InputUtil.getNonEmptyString("Nhập tên lớp: ");
        
        List<Student> students = StudentService.getInstance().getAllStudents();
        List<Student> classStudents = students.stream()
                .filter(s -> s.getClassName().equalsIgnoreCase(className))
                .collect(java.util.stream.Collectors.toList());
        
        if (classStudents.isEmpty()) {
            System.out.println("Không tìm thấy học sinh nào trong lớp: " + className);
            return;
        }
        
        int xuatSac = 0, gioi = 0, kha = 0, trungBinh = 0, yeu = 0, kem = 0;
        
        for (Student student : classStudents) {
            double averageScore = GradeServices.getInstance().DAverageScore(student.getId());
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
        
        int total = classStudents.size();
        
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    THỐNG KÊ XẾP LOẠI LỚP " + className.toUpperCase() + "                    │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s │ %-8s │ %-10s │%n", "Xếp loại", "Số lượng", "Tỷ lệ (%)");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Xuất sắc", xuatSac, (xuatSac * 100.0 / total));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Giỏi", gioi, (gioi * 100.0 / total));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Khá", kha, (kha * 100.0 / total));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Trung bình", trungBinh, (trungBinh * 100.0 / total));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Yếu", yeu, (yeu * 100.0 / total));
        System.out.printf("│ %-15s │ %-8d │ %-10.1f │%n", "Kém", kem, (kem * 100.0 / total));
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ %-15s │ %-8d │ %-10s │%n", "Tổng", total, "100.0");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }
}