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
        subjectService.displayAllSubjects();
    }


    @Override
    public void handleInput() {
        pause();
    }
}
