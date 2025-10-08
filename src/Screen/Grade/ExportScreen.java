package Screen.Grade;

import Screen.AbstractScreen;

public class ExportScreen extends AbstractScreen {
    @Override
    public void display() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│           Xuat Danh Sach Diem            │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    @Override
    public void handleInput() {
        System.out.println("\n[Thong bao] Chuc nang xuat file  dang duoc phat trien...");
        System.out.println("Cac thong tin se bao gom:");
        System.out.println("- Xuat du lieu ra man hinh");
        System.out.println("- Xuat du lieu ra file");

        pause();
    }
}