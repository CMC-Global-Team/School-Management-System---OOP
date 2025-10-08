package Screen.Grade;

import Models.Grade;
import Screen.AbstractScreen;
import Services.GradeServices;
import Utils.InputUtil;
import java.util.Optional;

public class DeleteGradeScreen extends AbstractScreen {

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Xoá Điểm Học Sinh              │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        if (GradeServices.getInstance().getTotalGradeRecords() == 0) {
            System.out.println("\nKhông có bản ghi nào để cập nhật.");
            pause();
            return;
        }

        String gradeID = input("\nNhập Mã điểm cần xoá: ");

        Optional<Grade> gradeList = GradeServices.getInstance().findById(gradeID);

        if (!gradeList.isPresent()) {
            System.out.println("Lỗi: Không tìm thấy điểm");
            pause();
            return;
        }

        Grade grade = gradeList.get();
        System.out.println("\nĐã tìm thấy lớp học:");
        System.out.println(grade.toString());


        if (InputUtil.getBoolean("Bạn có chắc muốn xoá điểm này?")) {
            if(GradeServices.getInstance().deleteGrade(gradeID)){
                System.out.println("✓ Đã xoá điểm thành công!");
            }
        } else {
            System.out.println("\nĐã hủy xóa.");
        }
        pause();
    }
}
