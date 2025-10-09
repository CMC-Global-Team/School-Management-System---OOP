package Screen.ClassRoom;

import Screen.AbstractScreen;
import Services.ClassroomService;
import Utils.InputUtil;

public class SearchClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public SearchClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            Tìm Kiếm Lớp Học               │");
        System.out.println("└──────────────────────────────────────────┘");
        
        System.out.println("\nHƯỚNG DẪN CHI TIẾT:");
        System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                           CÁCH TÌM KIẾM                                 │");
        System.out.println("├─────────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ Tìm theo mã lớp: Lớp001, Lớp002...                                      │");
        System.out.println("│ Tìm theo tên lớp: Lớp A, Lớp B, Lớp C...                                │");
        System.out.println("│ Tìm theo năm học: 2024-2025, 2023-2024...                               │");
        System.out.println("│ Tìm theo niên khóa: 2020-2024, 2021-2025...                             │");
        System.out.println("│ Tìm theo từ khóa: Bất kỳ từ nào trong thông tin lớp học                  │");
        System.out.println("│                                                                         │");
        System.out.println("│ LƯU Ý:                                                                   │");
        System.out.println("│ - Tìm kiếm không phân biệt hoa thường                                    │");
        System.out.println("│ - Có thể tìm kiếm một phần của từ khóa                                  │");
        System.out.println("│ - Để trống để hiển thị tất cả lớp học                                   │");
        System.out.println("│ - Kết quả sẽ hiển thị tất cả lớp học phù hợp                            │");
        System.out.println("└─────────────────────────────────────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    TÌM KIẾM LỚP HỌC");
        System.out.println("=".repeat(50));
        
        // Kiểm tra có dữ liệu không
        if (classroomService.getTotalClasses() == 0) {
            System.out.println("Không có lớp học nào trong hệ thống để tìm kiếm!");
            pause();
            return;
        }
        
        System.out.println("\nCÁC CÁCH TÌM KIẾM:");
        System.out.println("- Mã lớp: Lớp001, Lớp002...");
        System.out.println("- Tên lớp: Lớp A, Lớp B...");
        System.out.println("- Năm học: 2024-2025...");
        System.out.println("- Niên khóa: 2020-2024...");
        System.out.println("- Từ khóa: Bất kỳ từ nào");
        System.out.println("- Để trống: Hiển thị tất cả");
        
        String keyword = InputUtil.getString("\nNhập từ khóa tìm kiếm (Enter để xem tất cả): ");
        
        // Xử lý từ khóa
        if (keyword == null) {
            keyword = "";
        }
        
        System.out.println("\n" + "=".repeat(80));
        if (keyword.trim().isEmpty()) {
            System.out.println("                           TẤT CẢ LỚP HỌC");
        } else {
            System.out.println("                    KẾT QUẢ TÌM KIẾM: '" + keyword + "'");
        }
        System.out.println("=".repeat(80));
        
        // Sử dụng service để tìm kiếm và hiển thị kết quả
        classroomService.displaySearchResults(keyword);
        
        System.out.println("\n" + "=".repeat(80));
        pause();
    }
}