package Screen.Subject;


import Screen.AbstractScreen;


public class MenuSubjectScreen extends AbstractScreen {
    private final AddSubjectScreen createScreen;
    private final UpdateSubjectScreen updateScreen;
    private final DeleteSubjectScreen deleteScreen;
    private final SearchSubjectScreen searchScreen;
    private final ListSubjectScreen listScreen;
    private final AssignTeacherScreen assignTeacherScreen;
    private final EditSubjectScreen editSubjectScreen;


    public MenuSubjectScreen() {
        super();
        this.createScreen = new AddSubjectScreen();
        this.updateScreen = new UpdateSubjectScreen();
        this.deleteScreen = new DeleteSubjectScreen();
        this.searchScreen = new SearchSubjectScreen();
        this.listScreen = new ListSubjectScreen();
        this.assignTeacherScreen = new AssignTeacherScreen();
        this.editSubjectScreen = new EditSubjectScreen();
    }


    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        HỆ THỐNG QUẢN LÝ MÔN HỌC          │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Thêm Môn Học Mới                     │");
        System.out.println("│  2. Cập Nhật Môn Học                     │");
        System.out.println("│  3. Xóa Môn Học                          │");
        System.out.println("│  4. Tìm Kiếm Môn Học                     │");
        System.out.println("│  5. Gán Giáo Viên Cho Môn Học            │");
        System.out.println("│  6. Chỉnh sửa Chương trình học           │");
        System.out.println("│  7. Danh Sách Tất Cả Môn Học             │");
        System.out.println("│  0. Quay Lại Menu Chính                  │");
        System.out.println("└──────────────────────────────────────────┘");
        
        // Hiển thị tutorial
        displayTutorial();
    }
    
    private void displayTutorial() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│              HƯỚNG DẪN SỬ DỤNG             │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ 1. Thêm Môn Học: Tạo môn học mới với     │");
        System.out.println("│    mã, tên, số tiết, hệ số, loại môn     │");
        System.out.println("│ 2. Cập Nhật: Sửa đổi thông tin môn học   │");
        System.out.println("│    đã có trong hệ thống                   │");
        System.out.println("│ 3. Xóa Môn Học: Loại bỏ môn học khỏi     │");
        System.out.println("│    hệ thống                               │");
        System.out.println("│ 4. Tìm Kiếm: Tìm môn học theo từ khóa    │");
        System.out.println("│ 5. Gán Giáo Viên: Phân công giáo viên    │");
        System.out.println("│    phụ trách môn học                      │");
        System.out.println("│ 6. Chỉnh Sửa: Cập nhật chi tiết chương   │");
        System.out.println("│    trình học của môn học                  │");
        System.out.println("│ 7. Danh Sách: Xem tất cả môn học         │");
        System.out.println("│ 0. Quay Lại: Trở về menu chính           │");
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
                    assignTeacherScreen.display();
                    assignTeacherScreen.handleInput();
                    System.out.println("Đang phát triển");
                    pause();
                    break;
                case 6:
                    editSubjectScreen.display();
                    editSubjectScreen.handleInput();
                    pause();
                    break;
                case 7:
                    listScreen.display();
                    listScreen.handleInput();
                    pause();
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
