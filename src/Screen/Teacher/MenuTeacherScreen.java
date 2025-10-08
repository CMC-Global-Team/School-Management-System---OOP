package Screen.Teacher;

import Screen.AbstractScreen;

public class MenuTeacherScreen extends AbstractScreen {
    private final AddTeacherScreen createScreen;
    private final UpdateTeacherScreen updateScreen;
    private final DeleteTeacherScreen deleteScreen;
    private final SearchTeacherScreen searchScreen;
    private final ListTeacherScreen listScreen;

    public MenuTeacherScreen() {
        super();
        this.createScreen = new AddTeacherScreen();
        this.updateScreen = new UpdateTeacherScreen();
        this.deleteScreen = new DeleteTeacherScreen();
        this.searchScreen = new SearchTeacherScreen();
        this.listScreen = new ListTeacherScreen();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│       HỆ THỐNG QUẢN LÝ GIÁO VIÊN         │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Thêm Giáo Viên Mới                   │");
        System.out.println("│  2. Cập Nhật Giáo Viên                   │");
        System.out.println("│  3. Xóa Giáo Viên                        │");
        System.out.println("│  4. Tìm Kiếm Giáo Viên                   │");
        System.out.println("│  5. Danh Sách Tất Cả Giáo Viên           │");
        System.out.println("│  0. Quay Lại Menu Chính                  │");
        System.out.println("└──────────────────────────────────────────┘");
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
                    createScreen.display();
                    createScreen.handleInput();
                    break;
                case 2:
                    updateScreen.display();
                    updateScreen.handleInput();
                    break;
                case 3:
                    deleteScreen.display();
                    deleteScreen.handleInput();
                    break;
                case 4:
                    searchScreen.display();
                    searchScreen.handleInput();
                    break;
                case 5:
                    listScreen.display();
                    listScreen.handleInput();
                    pause();
                    break;
                case 0:
                    System.out.println("\nDang quay lai menu chinh...");
                    running = false;
                    break;
                default:
                    System.out.println("\nLua chon khong hop le. Vui long thu lai.");
                    pause();
            }
        }
    }
}

