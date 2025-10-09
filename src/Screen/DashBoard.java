package Screen;

import Screen.ClassRoom.ClassRoomMenu;
import Screen.Grade.MenuGrade;
import Screen.Student.StudentMenu;
import Screen.Subject.MenuSubjectScreen;
import Screen.Teacher.MenuTeacherScreen;
import Screen.Tuition.MenuTuition;

public class DashBoard extends AbstractScreen {
    private final ClassRoomMenu classRoomMenu;
    private final MenuTeacherScreen teacherMenu;
    private final StudentMenu studentMenu;
    private final MenuGrade menuGrade;
    private final MenuTuition  menuTuition;
    private final MenuSubjectScreen menuSubject;

    public DashBoard() {
        super();
        this.classRoomMenu = new ClassRoomMenu();
        this.teacherMenu = new MenuTeacherScreen();
        this.studentMenu = new StudentMenu();
        this.menuGrade = new MenuGrade();
        this.menuTuition = new MenuTuition();
        this.menuSubject = new MenuSubjectScreen();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        HỆ THỐNG QUẢN LÝ TRƯỜNG HỌC        │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Quản Lý Lớp Học                       │");
        System.out.println("│  2. Quản Lý Môn Học                       │");
        System.out.println("│  3. Quản Lý Điểm                          │");
        System.out.println("│  4. Quản Lý Học Sinh                      │");
        System.out.println("│  5. Quản Lý Giáo Viên                     │");
        System.out.println("│  6. Quản Lý Học Phí                       │");
        System.out.println("│  0. Thoát Chương Trình                    │");
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
                    classRoomMenu.handleInput();
                    break;
                case 2:
                    menuSubject.handleInput();
                    break;
                case 3:
                    menuGrade.handleInput();
                    break;
                case 4:
                    studentMenu.handleInput();
                    break;
                case 5:
                    teacherMenu.handleInput();
                    break;
                case 6:
                    menuTuition.handleInput();
                    break;
                case 0:
                    System.out.println("\nCảm ơn bạn đã sử dụng hệ thống. Tạm biệt!");
                    running = false;
                    break;
                default:
                    System.out.println("\nLựa chọn không hợp lệ. Vui lòng chọn từ 0-6.");
                    pause();
            }
        }
    }
}