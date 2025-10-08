package Screen.Grade;

import Screen.AbstractScreen;
import Services.GradeServices;

public class SearchForStudentGradesScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Tìm Kiếm Điểm Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập từ khóa tìm kiếm:");
        System.out.println("(Có thể tìm theo: , Tên lớp, Năm học, Tên GVCN)");

        String keyword = input("\nTừ khóa: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Từ khóa không được để trống!");
            pause();
            return;
        }

        // Sử dụng service để tìm kiếm và hiển thị kết quả
        GradeServices.getInstance().displaySearchResults(keyword);
        pause();
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
    
}
