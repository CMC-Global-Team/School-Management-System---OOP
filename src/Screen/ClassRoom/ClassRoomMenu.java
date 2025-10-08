package Screen.ClassRoom;

import Screen.AbstractScreen;

public class ClassRoomMenu extends AbstractScreen {
    private final AddClassRoomScreen addScreen;
    private final SearchClassRoomScreen searchScreen;
    private final DeleteClassRoomScreen deleteScreen;
    private final UpdateClassRoomScreen updateScreen;
    private final ListClassRoomScreen listScreen;

    public ClassRoomMenu() {
        super();
        this.addScreen = new AddClassRoomScreen();
        this.searchScreen = new SearchClassRoomScreen();
        this.deleteScreen = new DeleteClassRoomScreen();
        this.updateScreen = new UpdateClassRoomScreen();
        this.listScreen = new ListClassRoomScreen();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        HỆ THỐNG QUẢN LÝ LỚP HỌC          │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Thêm Lớp Học Mới                     │");
        System.out.println("│  2. Tìm Kiếm Lớp Học                     │");
        System.out.println("│  3. Cập Nhật Lớp Học                     │");
        System.out.println("│  4. Xóa Lớp Học                          │");
        System.out.println("│  5. Danh Sách Tất Cả Lớp Học             │");
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
                    addScreen.display();
                    addScreen.handleInput();
                    break;
                case 2:
                    searchScreen.display();
                    searchScreen.handleInput();
                    break;
                case 3:
                    updateScreen.display();
                    updateScreen.handleInput();
                    break;
                case 4:
                    deleteScreen.display();
                    deleteScreen.handleInput();
                    break;
                case 5:
                    listScreen.display();
                    listScreen.handleInput();
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