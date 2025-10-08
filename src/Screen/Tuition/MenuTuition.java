package Screen.Tuition;

import Screen.AbstractScreen;



public class MenuTuition extends AbstractScreen {
    private final RecordTuitionScreen recordTuitionScreen;
    private final EditTuitionScreen editTuitionScreen;
    private final DeleteTuitionScreen deleteTuitionScreen;
    private final SearchForTuition searchForTuition;
    private final TuitionDiscountScreen tuitionDiscountScreen;
    private final FinancialReportScreen financialReportScreen;
    private final ExportTuitionListScreen exportTuitionListScreen;
    public MenuTuition() {
        super();
        this.recordTuitionScreen = new RecordTuitionScreen();
        this.editTuitionScreen = new EditTuitionScreen();
        this.deleteTuitionScreen = new DeleteTuitionScreen();
        this.searchForTuition = new SearchForTuition();
        this.tuitionDiscountScreen = new TuitionDiscountScreen();
        this.financialReportScreen = new FinancialReportScreen();
        this.exportTuitionListScreen = new ExportTuitionListScreen();


    }
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│        HỆ THỐNG QUẢN LÝ HỌC PHÍ          │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Ghi Nhận Thanh Toán Học Phí          │");
        System.out.println("│  2. Sửa Thông Tin Học Phí                │");
        System.out.println("│  3. Xóa Dữ Liệu Học Phí                  │");
        System.out.println("│  4. Tra Cứu Học Phí                      │");
        System.out.println("│  5. Miễn Giảm Học Phí                    │");
        System.out.println("│  6. Báo Cáo Tài Chính                    │");
        System.out.println("│  7. Xuất Danh Sách Học Phí               │");
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
                    recordTuitionScreen.display();
                    recordTuitionScreen.handleInput();
                    break;
                case 2:
                    editTuitionScreen.display();
                    editTuitionScreen.handleInput();
                    break;
                case 3:
                    deleteTuitionScreen.display();
                    deleteTuitionScreen.handleInput();
                    break;
                case 4:
                    searchForTuition.display();
                    searchForTuition.handleInput();
                    break;
                case 5:
                    tuitionDiscountScreen.display();
                    tuitionDiscountScreen.handleInput();
                    break;
                case 6:
                    financialReportScreen.display();
                    financialReportScreen.handleInput();
                    break;
                case 7:
                    exportTuitionListScreen.display();
                    exportTuitionListScreen.handleInput();
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
