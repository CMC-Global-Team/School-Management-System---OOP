package Screen.Student;

import Screen.AbstractScreen;

public class StudentMenu extends AbstractScreen {
    private final AddStudentScreen addScreen;
    private final UpdateStudentScreen updateScreen;
    private final DeleteStudentScreen deleteScreen;
    private final SearchStudentScreen searchScreen;
    private final ListStudentScreen listScreen;
    private final FilterStudentScreen filterScreen;
    private final ExportStudentScreen exportScreen;
    private final TransferStudentScreen transferScreen;

    public StudentMenu() {
        super();
        this.addScreen = new AddStudentScreen();
        this.updateScreen = new UpdateStudentScreen();
        this.deleteScreen = new DeleteStudentScreen();
        this.searchScreen = new SearchStudentScreen();
        this.listScreen = new ListStudentScreen();
        this.filterScreen = new FilterStudentScreen();
        this.exportScreen = new ExportStudentScreen();
        this.transferScreen = new TransferStudentScreen();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        HỆ THỐNG QUẢN LÝ HỌC SINH         │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Thêm Học Sinh Mới                    │");
        System.out.println("│  2. Tìm Kiếm Học Sinh                    │");
        System.out.println("│  3. Cập Nhật Thông Tin Học Sinh          │");
        System.out.println("│  4. Xóa Học Sinh                         │");
        System.out.println("│  5. Danh Sách Tất Cả Học Sinh            │");
        System.out.println("│  6. Lọc Học Sinh                         │");
        System.out.println("│  7. Xuất Danh Sách Học Sinh              │");
        System.out.println("│  8. Chuyển Lớp Học Sinh                  │");
        System.out.println("│  0. Quay Lại Menu Chính                  │");
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
                case 6:
                    filterScreen.display();
                    filterScreen.handleInput();
                    break;
                case 7:
                    exportScreen.display();
                    exportScreen.handleInput();
                    break;
                case 8:
                    transferScreen.display();
                    transferScreen.handleInput();
                    break;
                case 0:
                    System.out.println("\nĐang quay lại menu chính...");
                    running = false;
                    break;
                default:
                    System.out.println("\nLựa chọn không hợp lệ. Vui lòng thử lại.");
                    pause();
            }
        }
    }
}