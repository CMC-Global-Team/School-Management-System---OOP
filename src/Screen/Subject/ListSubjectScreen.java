package Screen.Subject;


import Screen.AbstractScreen;
import Services.SubjectService;


public class ListSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;


    public ListSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
    }


    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           DANH SÁCH MÔN HỌC              │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
        
        // Hiển thị danh sách môn học
        subjectService.displayAllSubjects();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│            HƯỚNG DẪN XEM DANH SÁCH        │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ Màn hình này hiển thị tất cả môn học      │");
        System.out.println("│ trong hệ thống với đầy đủ thông tin:      │");
        System.out.println("│                                          │");
        System.out.println("│ Thông tin hiển thị bao gồm:               │");
        System.out.println("│ - Mã môn học                             │");
        System.out.println("│ - Tên môn học                             │");
        System.out.println("│ - Số tiết học                             │");
        System.out.println("│ - Hệ số                                  │");
        System.out.println("│ - Loại môn học (Bắt buộc/Tự chọn)        │");
        System.out.println("│ - Mô tả                                  │");
        System.out.println("│ - Giáo viên phụ trách                    │");
        System.out.println("│ - Trạng thái (Đang dạy/Ngừng)            │");
        System.out.println("│                                          │");
        System.out.println("│ Nhấn Enter để quay lại menu chính        │");
        System.out.println("└──────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        pause();
    }
}
