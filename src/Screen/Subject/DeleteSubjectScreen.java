package Screen.Subject;


import Screen.AbstractScreen;
import Services.SubjectService;


public class DeleteSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;


    public DeleteSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
    }


    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│             XÓA MÔN HỌC                  │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│              HƯỚNG DẪN XÓA MÔN HỌC        │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ Bước 1: Nhập mã môn học cần xóa          │");
        System.out.println("│ Bước 2: Hệ thống sẽ kiểm tra tồn tại      │");
        System.out.println("│ Bước 3: Xác nhận và thực hiện xóa         │");
        System.out.println("│                                          │");
        System.out.println("│ Lưu ý quan trọng:                         │");
        System.out.println("│ - Việc xóa môn học là không thể hoàn tác  │");
        System.out.println("│ - Cần đảm bảo mã môn học chính xác       │");
        System.out.println("│ - Kiểm tra kỹ trước khi xóa               │");
        System.out.println("│ - Môn học đã được gán giáo viên vẫn có thể│");
        System.out.println("│   xóa được                                │");
        System.out.println("└──────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        String id = input("Nhập mã môn học cần xóa: ");
        if (subjectService.deleteSubject(id)) {
            System.out.println("Môn học đã được xóa thành công!");
        }
        pause();
    }
}


