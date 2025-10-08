package Screen.Grade;

import Screen.AbstractScreen;
import Services.GradeServices;
import Utils.InputUtil;

public class EnterGradeScreen extends  AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Nhập Điểm Cho Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        String gradeID = InputUtil.getNonEmptyString("Nhập mã điểm: ");
        String studentID = InputUtil.getNonEmptyString("Nhập mã học sinh: ");
        String subjectID = InputUtil.getNonEmptyString("Nhập mã môn học: ");
        System.out.println("Loại điểm: 1.Thường xuyên 2.Giữa kỳ 3.Cuối kỳ");
        int gradeType = InputUtil.getInt("Chọn loại điểm (1-3): ");
        GradeServices.getInstance().addGrade(gradeID, studentID, subjectID, gradeType );


    }
}