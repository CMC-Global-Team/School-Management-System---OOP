package Screen.Grade;

import Screen.AbstractScreen;
import Services.GradeServices;

public class SearchForStudentGradesScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Tìm Kiếm Điểm Học Sinh         │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\nNhập từ khóa tìm kiếm:");
        System.out.println("(Có thể tìm theo: , Tên lớp, Năm học, Tên GVCN)");

        String keyword = input("\nTừ khóa: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Từ khóa không được để trống!");
            pause();
            return;
        }

        // Sử dụng service để tìm kiếm và hiển thị kết quả
        GradeServices.getInstance().displaySearchResults(keyword);
        pause();
    }
}
