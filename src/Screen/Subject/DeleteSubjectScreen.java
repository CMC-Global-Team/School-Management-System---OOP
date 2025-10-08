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


