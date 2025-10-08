package Screen.Grade;

import Screen.AbstractScreen;
import Utils.FileUtil;
import Utils.InputUtil;

import java.io.IOException;
import java.util.ArrayList;
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
        List<String> gradeLines = new ArrayList<>();
        try {
            if(FileUtil.fileExists("data/grades.txt")){
                gradeLines = FileUtil.readLines("data/grades.txt");
            }
        }catch (IOException e){
            System.out.println("Lỗi khi đọc file điểm số: " + e.getMessage());
        }
        if(gradeLines.isEmpty()){
            System.out.println("Danh sách điểm trống!");
        }
        String studentID = InputUtil.getString("Mã học sinh(Enter để quay lại): ");
        if(studentID.isEmpty()){
            return;
        }
        double a = AverageGradeScreen.getAvrScoreByStudentID(studentID, gradeLines);
        System.out.println("Điểm trung bình" + a);
        if(a <= 2) System.out.println("Xếp hạng: Kém");
        if(a>2 && a<=4) System.out.println("Xếp hạng: Trung bình");
        if(a>4 && a<=6) System.out.println("Xếp hạng: Khá");
        if(a>6 && a<=8) System.out.println("Xếp hạng: Giỏi");
        if(a<=10) System.out.println("Xếp hạng: Xuất sắc");

        pause();
    }
}