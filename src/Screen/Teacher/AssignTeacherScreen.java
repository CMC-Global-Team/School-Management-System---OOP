package Screen.Teacher;

import Screen.AbstractScreen;
import Services.TeacherService;
public class AssignTeacherScreen extends AbstractScreen {

    private final TeacherService teacherService;

    public AssignTeacherScreen() {
        this.teacherService = TeacherService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│         BỔ NHIỆM GIÁO VIÊN CHỦ NHIỆM CHO LỚP HỌC                 │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");

    }

    @Override
    public void handleInput() {

        teacherService.assignTeacherToClass();
        pause();
    }
}
