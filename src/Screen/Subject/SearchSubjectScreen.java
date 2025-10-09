package Screen.Subject;


import Screen.AbstractScreen;
import Services.SubjectService;


public class SearchSubjectScreen extends AbstractScreen {
    private final SubjectService subjectService;


    public SearchSubjectScreen() {
        this.subjectService = SubjectService.getInstance();
    }


    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│             TÌM KIẾM MÔN HỌC             │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│            HƯỚNG DẪN TÌM KIẾM             │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ Bước 1: Nhập từ khóa tìm kiếm             │");
        System.out.println("│ Bước 2: Hệ thống sẽ tìm kiếm trong:       │");
        System.out.println("│         - Mã môn học                     │");
        System.out.println("│         - Tên môn học                     │");
        System.out.println("│         - Mô tả môn học                   │");
        System.out.println("│         - Tên giáo viên phụ trách         │");
        System.out.println("│                                          │");
        System.out.println("│ Mẹo tìm kiếm hiệu quả:                    │");
        System.out.println("│ - Sử dụng từ khóa ngắn gọn                │");
        System.out.println("│ - Không phân biệt hoa thường               │");
        System.out.println("│ - Có thể tìm theo một phần của từ          │");
        System.out.println("│ - Ví dụ: 'toán', 'GV001', 'bắt buộc'      │");
        System.out.println("└──────────────────────────────────────────┘");
    }


    @Override
    public void handleInput() {
        String keyword = input("Nhập từ khóa tìm kiếm: ");
        subjectService.displaySearchResults(keyword);
        pause();
    }
}
