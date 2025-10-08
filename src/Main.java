import Screen.DashBoard;
import Services.FileManager;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo thư mục data
        FileManager.initializeDataDirectory();

        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   CHÀO MỪNG ĐẾN VỚI HỆ THỐNG QUẢN LÝ TRƯỜNG   ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println();

        // Khởi chạy Dashboard
        DashBoard dashboard = new DashBoard();
        dashboard.handleInput();

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   CẢM ƠN BẠN ĐÃ SỬ DỤNG HỆ THỐNG!            ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }
}