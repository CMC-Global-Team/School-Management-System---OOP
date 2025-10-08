package Screen.Grade;

import Screen.AbstractScreen;
import Models.Grade;
import Utils.FileUtil;
import Utils.InputUtil;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchForStudentGradesScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Tìm Kiếm Điểm Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<String> gradeLines = new ArrayList<>();
        try{
            if(FileUtil.fileExists("data/grades.txt")){
                gradeLines = FileUtil.readLines("src/Data/grades.txt");
            }
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi đọc file điểm số: " + e.getMessage());
        }

        System.out.println("Bạn muốn tìm bằng:");
        System.out.println("1. Mã học sinh");
        System.out.println("2. Mã môn");
        System.out.println("3. Năm học và học kỳ");
        System.out.println("0. Quay lại");
        int choice = InputUtil.getInt("Lựa chọn của bạn(0-3): ");

        switch (choice){
            case 1:
                String studentID = InputUtil.getString("Nhập mã học sinh(bỏ trống và nhấn enter để quay lại): ");
                displayResults(findGradesByStudentID(gradeLines, studentID));
                pause();
                break;
            case 2:
                String subjectID = InputUtil.getString("Nhập mã môn học(bỏ trống và nhấn enter để quay lại): ");
                displayResults(findGradesBySubjectID(gradeLines, subjectID));
                pause();
                break;
            case 3:
                System.out.println("Năm học:");
                String schoolYear = EnterGradeScreen.schoolYearInput();
                int semester = InputUtil.getInt("Nhập học kỳ: " );
                displayResults(findGradesBySemester(gradeLines, semester, schoolYear));
                pause();
                break;
            case 0:
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                pause();
                break;
        }
    }
    public static List<String> findGradesByStudentID(List<String> gradeLines, String studentID){
        List<String> results = new ArrayList<>();
        for(String line : gradeLines){
            Grade grade = Grade.fromString(line);
            if(grade != null && grade.getStudentId().equalsIgnoreCase(studentID)){
                results.add(line);
            }
        }
        return results;
    }

    public static List<String> findGradesBySubjectID(List<String> gradeLines, String subjectID){
        List<String> results = new ArrayList<>();
        for(String line : gradeLines){
            Grade grade = Grade.fromString(line);
            if(grade != null && grade.getSubjectId().equals(subjectID)){
                results.add(line);
            }
        }
        return results;
    }

    public static List<String> findGradesBySemester(List<String> gradeLines, int semester, String schoolYear){
        List<String> results = new ArrayList<>();
        for(String line : gradeLines){
            Grade grade = Grade.fromString(line);
            if(grade != null && grade.getSemester() == semester && grade.getSchoolYear().equals(schoolYear)){
                results.add(line);
            }
        }
        return results;
    }

    public static void displayResults(List<String> results){
        if(!results.isEmpty()) {
            for (String line : results) {
                Grade g = Grade.fromString(line);
                if(g != null) {
                    System.out.printf("%10s %10s %10s %10s %10s %10s %5s %20s%n",
                            "Mã điểm", "Mã học sinh", "Mã môn", "Loại điểm", "Diểm", "Năm học", "Kỳ học", "Ghi chú");

                    // Print separator
                    System.out.println("-".repeat(10 + 1 + 10 + 1 + 10 + 1 + 10 + 1 + 10 + 1 + 10 + 1 + 5 + 1 + 20));
                    System.out.printf("%10s %10s %10s %10s %10.2f %10s %5d %20s%n",
                            g.getGradeId(), g.getStudentId(), g.getSubjectId(), g.getGradeType(),
                            g.getScore(), g.getSchoolYear(), g.getSemester(), g.getNote());
                }
            }
            results.clear();
        }else {
            System.out.println("Không tìm thấy thông tin!");
        }
    }
}
