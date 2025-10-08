package Screen.Teacher;

import Screen.AbstractScreen;
import Services.TeacherService;

public class ExportTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService;

    public ExportTeacherScreen() {
        this.teacherService = TeacherService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│   DANH SÁCH LỚP VÀ MÔN GIẢNG DẠY CỦA GIÁO VIÊN               │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");

    }

    @Override
    public void handleInput() {

        teacherService.showTeachingClassesByTeacher();

        pause();
    }
}
