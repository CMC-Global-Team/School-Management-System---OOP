package Screen.Teacher;

import Screen.AbstractScreen;

public class MenuTeacherScreen extends AbstractScreen {
    private final AddTeacherScreen createScreen;
    private final UpdateTeacherScreen updateScreen;
    private final DeleteTeacherScreen deleteScreen;
    private final SearchTeacherScreen searchScreen;
    private final ListTeacherScreen listScreen;
    private final AssignTeacherScreen assignTeacherScreen;
    private final AssignHomeroomScreen assignHomeroomScreen;
    private final ExportTeacherScreen exportTeacherScreen;

    public MenuTeacherScreen() {
        super();
        this.createScreen = new AddTeacherScreen();
        this.updateScreen = new UpdateTeacherScreen();
        this.deleteScreen = new DeleteTeacherScreen();
        this.searchScreen = new SearchTeacherScreen();
        this.listScreen = new ListTeacherScreen();
        this.assignTeacherScreen = new AssignTeacherScreen();
        this.assignHomeroomScreen = new AssignHomeroomScreen();
        this.exportTeacherScreen = new ExportTeacherScreen();


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
        System.out.println("│  6. Phân Công Giảng Dạy                  │");
        System.out.println("│  7. Gán Lớp Chủ Nhiệm                    │");
        System.out.println("│  8. Xuất Danh Sách Giáo Viên             │");
        System.out.println("│  0. Quay Lại Menu Chính                  │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN SỬ DỤNG:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CÁC CHỨC NĂNG CHÍNH                         │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Thêm giáo viên mới - Nhập thông tin giáo viên vào hệ thống          │");
        System.out.println("│ 2. Cập nhật thông tin - Chỉnh sửa thông tin giáo viên hiện có         │");
        System.out.println("│ 3. Xóa giáo viên - Loại bỏ giáo viên khỏi hệ thống                    │");
        System.out.println("│ 4. Tìm kiếm giáo viên - Tìm kiếm theo nhiều tiêu chí khác nhau         │");
        System.out.println("│ 5. Danh sách giáo viên - Xem tất cả giáo viên trong hệ thống           │");
        System.out.println("│ 6. Phân công giảng dạy - Gán môn học cho giáo viên                    │");
        System.out.println("│ 7. Gán lớp chủ nhiệm - Chỉ định giáo viên làm chủ nhiệm lớp           │");
        System.out.println("│ 8. Xuất danh sách - Xuất danh sách giáo viên ra file                   │");
        System.out.println("│ 0. Quay lại menu chính                                                  │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
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
                case 6:
                    assignTeacherScreen.display();
                    assignTeacherScreen.handleInput();
                    pause();
                    break;
                case 7:
                    assignHomeroomScreen.display();
                    assignHomeroomScreen.handleInput();
                    pause();
                    break;
                case 8:
                    exportTeacherScreen.display();
                    exportTeacherScreen.handleInput();
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