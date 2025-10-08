package Screen.Grade;

import Models.Grade;
import Models.Student;
import Models.Subject;
import Screen.AbstractScreen;
import Services.GradeServices;
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
        String studentID = InputUtil.getNonEmptyString("Mã học sinh: ");
        GradeServices.getInstance().AverageScore(studentID);
    }
}
