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
    }


    @Override
    public void handleInput() {
        String keyword = input("Nhập từ khóa tìm kiếm: ");
        subjectService.displaySearchResults(keyword);
        pause();
    }
}
