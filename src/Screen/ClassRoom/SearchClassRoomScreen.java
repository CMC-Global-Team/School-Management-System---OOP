package Screen.ClassRoom;

import Screen.AbstractScreen;
import Services.ClassroomService;

public class SearchClassRoomScreen extends AbstractScreen {
    private final ClassroomService classroomService;

    public SearchClassRoomScreen() {
        this.classroomService = ClassroomService.getInstance();
    }

    @Override
    public void display() {
        clearScreen();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│            TÌM KIẾM LỚP HỌC              │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập từ khóa tìm kiếm:");
        System.out.println("(Có thể tìm theo: Mã lớp, Tên lớp, Năm học, Tên GVCN)");
        
        String keyword = input("\nTừ khóa: ");
        
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Từ khóa không được để trống!");
            pause();
            return;
        }
        
        // Sử dụng service để tìm kiếm và hiển thị kết quả
        classroomService.displaySearchResults(keyword);
        pause();
    }
}