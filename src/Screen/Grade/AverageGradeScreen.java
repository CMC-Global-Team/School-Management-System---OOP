package Screen.Grade;

import Models.Grade;
import Models.Subject;
import Screen.AbstractScreen;
import Utils.FileUtil;
import Utils.InputUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AverageGradeScreen extends AbstractScreen{
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Tính Điểm Trung Bình           │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        List<String> gradeLines = new ArrayList<>();
        List<String> studentResults;
        List<String> subjectResults;
        double averageGrade = 0;
        double coefficient = 0;
        try{
            if (FileUtil.fileExists("data/grades.txt")) {
                gradeLines = FileUtil.readLines("data/grades.txt");
            }
        }catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi đọc file điểm số: " + e.getMessage());
        }
        String studentID = InputUtil.getString("Mã học sinh(Enter để quay lại): ");
        if(studentID.isEmpty()){
            return;
        }
        studentResults = SearchForStudentGradesScreen.findGradesByStudentID(gradeLines, studentID);
        while(!studentResults.isEmpty()){
            String firstLine = studentResults.get(0);
            Grade g1 = Grade.fromString(firstLine);
            double r;
            if(g1 != null){
                subjectResults = SearchForStudentGradesScreen.findGradesBySubjectID(studentResults, g1.getSubjectId());
                if(!subjectResults.isEmpty()) {
                    r = getAvrSubjectScoreBySubjectLines(subjectResults);
                    System.out.printf("Điểm trung bình môn " + g1.getSubjectId() + ": %.2f \n",r);
                    averageGrade += r * getSubjectCoefficientsBySubjectID(g1.getSubjectId());
                    coefficient += getSubjectCoefficientsBySubjectID(g1.getSubjectId());
                }
                studentResults = DeleteGradeScreen.ignoreSubjectID(g1.getSubjectId(), studentResults);
            }
        }
        double finalAverageGrade = averageGrade / coefficient;
        System.out.printf("Điểm trung bình: %.2f", finalAverageGrade);
        pause();
    }
    public static Double getSubjectCoefficientsBySubjectID(String subjectIDs){
        try {
            List<String> lines = FileUtil.readLines("src/Data/subjects.txt");
            for (String line : lines) {
                Subject s = Subject.fromString(line);
                if (s != null && (s.getSubjectID().equalsIgnoreCase(subjectIDs))) {
                    return s.getConfficient();
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tìm kiếm hệ số của môn học: " + e.getMessage());
        }
        return 0.0;
    }
    
}