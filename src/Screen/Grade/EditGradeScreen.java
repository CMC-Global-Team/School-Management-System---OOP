package Screen.Grade;

import Models.Grade;
import Screen.AbstractScreen;
import Services.GradeServices;
import Utils.InputUtil;
import java.util.Optional;

public class EditGradeScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Sửa Điểm Cho Học Sinh          │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        if (GradeServices.getInstance().getTotalGradeRecords() == 0) {
            System.out.println("\nKhông có bản ghi nào để cập nhật.");
            pause();
            return;
        }

        String gradeID = input("\nNhập Mã điểm cần cập nhật: ");

        Optional<Grade> gradeList = GradeServices.getInstance().findById(gradeID);

        if (!gradeList.isPresent()) {
            System.out.println("Lỗi: Không tìm thấy điểm");
            pause();
            return;
        }

        Grade grade = gradeList.get();

        System.out.println("\nThông tin hiện tại:");
        System.out.println(grade);
        System.out.println("\nBạn muốn cập nhật thông tin gì?");
        System.out.println("1. Mã học sinh");
        System.out.println("2. Mã môn");
        System.out.println("3. Loại điểm");
        System.out.println("4. Điểm");
        System.out.println("5. Kỳ học");
        System.out.println("6. Năm học");
        System.out.println("7. Ghi chú");
        System.out.println("8. Cập nhật Tất cả");
        System.out.println("0. Hủy");

        int choice = inputInt("\nNhập lựa chọn của bạn(0-8): ");

        switch (choice) {
            case 1:
                updateStudentID(grade);
                break;
            case 2:
                updateSubjectID(grade);
                break;
            case 3:
                updateGradeType(grade);
                break;
            case 4:
                updateScore(grade);
                break;
            case 5:
                updateSemester(grade);
                break;
            case 6:
                updateSchoolYear(grade);
                break;
            case 7:
                updateNote(grade);
                break;
            case 8:
                updateAll(grade);
                break;
            case 0:
                System.out.println("Đã hủy cập nhật.");
                pause();
                return;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                pause();
                return;
        }
        if (GradeServices.getInstance().updateGrade(grade)) {
            System.out.println("\nThông tin đã cập nhật:");
            System.out.println(grade.toString());
        }
        pause();
    }

    private void updateStudentID(Grade grade) {
        String newStudentID = input("Nhập mã học sinh mới (hiện tại: " + grade.getStudentId() + "): ");
        if (!newStudentID.isEmpty()) {
            grade.setStudentId(newStudentID);
        }
    }

    private void updateSubjectID(Grade grade) {
        String newSubjectID = input("Nhập mã môn học mới (hiện tại: " + grade.getStudentId() + "): ");
        if (!newSubjectID.isEmpty()) {
            grade.setStudentId(newSubjectID);
        }
    }

    private void updateGradeType(Grade grade) {
        System.out.println("(1.Thường xuyên, 2.Giữa kỳ, 3.Cuối kỳ");
        int newGradeType = InputUtil.getInt("Nhập mã loại điểm 1-3 (hiện tại: " + grade.getStudentId() + "): ");
        switch (newGradeType) {
            case 1:
                grade.setGradeType("thuong xuyen");
                break;
            case 2:
                grade.setGradeType("giua ky");
                break;
            case 3:
                grade.setGradeType("cuoi ky");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                break;
        }
    }

    private void updateScore(Grade grade) {
        double score = InputUtil.getInt("Điểm: ");
        grade.setScore(score);
    }

    private void updateSemester(Grade grade) {
        int Semester = inputInt("Học kỳ: ");
        if (Semester != 0) {
            grade.setSemester(Semester);
        }
    }

    private void updateSchoolYear(Grade grade) {
        GradeServices.getInstance();
        String schoolYear = GradeServices.schoolYearInput();
        if(!schoolYear.isEmpty()){
            grade.setSchoolYear(schoolYear);
        }
    }

    private void updateNote(Grade grade) {
        String note = InputUtil.getString("Ghi chú: ");
        if(!note.isEmpty()){
            grade.setNote(note);
        }
    }

    private void updateAll(Grade grade) {
        updateStudentID(grade);
        updateSubjectID(grade);
        updateGradeType(grade);
        updateScore(grade);
        updateSemester(grade);
        updateSchoolYear(grade);
        updateNote(grade);
    }
}
