package Screen.Grade;

import Screen.AbstractScreen;

public class MenuGrade extends AbstractScreen {
    private final EnterGradeScreen enterGradeScreen;
    private final EditGradeScreen editGradeScreen;
    private final DeleteGradeScreen deleteGradeScreen;
    private final SearchForStudentGradesScreen searchForStudentGradesScreen;
    private final AverageGradeScreen averageGradeScreen;
    private final GradeClassificationScreen gradeClassificationScreen;
    private final ClassGradeReportScreen classGradeReportScreen;
    private final ExportGradeListScreen exportGradeListScreen;

    public MenuGrade() {
        super();
        this.enterGradeScreen = new EnterGradeScreen();
        this.editGradeScreen = new EditGradeScreen();
        this.deleteGradeScreen = new DeleteGradeScreen();
        this.searchForStudentGradesScreen = new SearchForStudentGradesScreen();
        this.averageGradeScreen = new AverageGradeScreen();
        this.gradeClassificationScreen = new GradeClassificationScreen();
        this.classGradeReportScreen = new ClassGradeReportScreen();
        this.exportGradeListScreen = new ExportGradeListScreen();
    }
    
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           HỆ THỐNG QUẢN LÝ ĐIỂM          │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Nhập Điểm Cho Học Sinh               │");
        System.out.println("│  2. Sửa Điểm Đã Nhập                     │");
        System.out.println("│  3. Xóa Điểm                             │");
        System.out.println("│  4. Tìm Kiếm Điểm Học Sinh               │");
        System.out.println("│  5. Tính Điểm Trung Bình                 │");
        System.out.println("│  6. Xếp Loại Học Lực                     │");
        System.out.println("│  7. Báo Cáo Điểm Lớp                     │");
        System.out.println("│  8. Xuất Danh Sách Điểm                  │");
        System.out.println("│  0. Quay Lại Menu Chính                  │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        boolean running = true;
        while (running) {
            clearScreen();
            display();
            int choice = inputInt("Nhập lựa chọn của bạn: ");

            switch (choice) {
                case 1:
                    enterGradeScreen.display();
                    enterGradeScreen.handleInput();
                    break;
                case 2:
                    editGradeScreen.display();
                    editGradeScreen.handleInput();
                    break;
                case 3:
                    deleteGradeScreen.display();
                    deleteGradeScreen.handleInput();
                    break;
                case 4:
                    searchForStudentGradesScreen.display();
                    searchForStudentGradesScreen.handleInput();
                    break;
                case 5:
                    averageGradeScreen.display();
                    averageGradeScreen.handleInput();
                    break;
                case 6:
                    gradeClassificationScreen.display();
                    gradeClassificationScreen.handleInput();
                    break;
                case 7:
                    classGradeReportScreen.display();
                    classGradeReportScreen.handleInput();
                    break;
                case 8:
                    exportGradeListScreen.display();
                    exportGradeListScreen.handleInput();
                    break;
                case 0:
                    System.out.println("\nĐang quay lại menu chính...");
                    running = false;
                    break;
                default:
                    System.out.println("\nLựa chọn không hợp lệ, vui lòng thử lại");
                    pause();
                    break;
            }
        }
    }
}
