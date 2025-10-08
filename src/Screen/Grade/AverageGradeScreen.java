package Screen.Grade;

import Models.Grade;
import Models.Student;
import Models.Subject;
import Screen.AbstractScreen;
import Services.GradeServices;
import Services.StudentService;
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
        if (GradeServices.getInstance().getTotalGradeRecords() == 0) {
            System.out.println("\nKhông có bản ghi nào để cập nhật.");
            pause();
            return;
        }
        String studentID = InputUtil.getNonEmptyString("Mã học sinh: ");
        if(StudentService.getInstance().isStudentIdExists(studentID)){
            GradeServices.getInstance().AverageScore(studentID);
        }else {
            System.out.println("Không tìm thấy mã học sinh: " + studentID);
        }
        pause();
    }
}
