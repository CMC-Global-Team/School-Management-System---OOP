package Screen.Grade;



import Screen.AbstractScreen;


public class MenuGrade extends AbstractScreen {
    private final EnterGradeScreen enterGradeScreen ;
    private final EditGradeScreen editGradeScreen;
    private final DeleteGradeScreen deleteGradeScreen;
    private final SearchForStudentGradesScreen searchForStudentGradesScreen;
    private final AverageGradeScreen averageGradeScreen;
    private final GradeClassificationScreen gradeClassificationScreen;
    private final ReportScreen reportScreen;
    private final ExportScreen exportScreen;

    public MenuGrade() {
        super();
        this.enterGradeScreen = new EnterGradeScreen();
        this.editGradeScreen = new EditGradeScreen();
        this.deleteGradeScreen = new DeleteGradeScreen();
        this.searchForStudentGradesScreen = new SearchForStudentGradesScreen();
        this.averageGradeScreen = new AverageGradeScreen();
        this.gradeClassificationScreen = new GradeClassificationScreen();
        this.reportScreen = new ReportScreen();
        this.exportScreen = new ExportScreen();

    }
    @Override
    public void display() {
    }

    @Override
    public void handleInput() {
    }
}
