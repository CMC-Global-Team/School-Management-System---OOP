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
        System.out.println("===========================================");
        System.out.println("     HE THONG QUAN LY TRUONG HOC");
        System.out.println("===========================================");
        System.out.println("  1. Quan Ly Lop Hoc");
        System.out.println("  2. Quan Ly Mon Hoc");
        System.out.println("  3. Quan Ly Diem");
        System.out.println("  4. Quan Ly Hoc Sinh");
        System.out.println("  5. Quan Ly Giao Vien");
        System.out.println("  6. Quan Ly Hoc Phi");
        System.out.println("  0. Thoat Chuong Trinh");
        System.out.println("===========================================");
    }

    @Override
    public void handleInput() {
        boolean running = true;
        while (running) {
            clearScreen();
            display();
            int choice = inputInt("Nhap lua chon cua ban: ");

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
                    System.out.println("\nCam on ban da su dung he thong. Tam biet!");
                    running = false;
                    break;
                default:
                    System.out.println("\nLua chon khong hop le. Vui long chon tu 0-6.");
                    pause();
            }
        }
    }
}