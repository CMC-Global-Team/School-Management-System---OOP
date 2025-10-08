package Screen.Grade;

import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
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
        String studentID = InputUtil.getString("Mã học sinh(Enter để quay lại): ");
        if(studentID.isEmpty()){
            return;
        }
        if(!StudentService.getInstance().isStudentIdExists(studentID)){
            System.out.println("Không tìm thấy hoc sinh: " + studentID);
            return;
        }
        double a = GradeServices.getInstance().DAverageScore(studentID);
        System.out.println("Điểm trung bình" + a);
        if(a <= 2) System.out.println("Xếp hạng: Kém");
        if(a>2 && a<=4) System.out.println("Xếp hạng: Trung bình");
        if(a>4 && a<=6) System.out.println("Xếp hạng: Khá");
        if(a>6 && a<=8) System.out.println("Xếp hạng: Giỏi");
        if(a<=10) System.out.println("Xếp hạng: Xuất sắc");
        pause();
    }
}